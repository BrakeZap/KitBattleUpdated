package xyz.brakezap.kitBattleUpdated.events;

import me.wazup.kitbattle.KitbattleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.brakezap.kitBattleUpdated.KitBattleUpdated;
import xyz.brakezap.kitBattleUpdated.potions.CustomPotionEffect;

import java.util.List;
import java.util.UUID;


public class CheckSplashPotion implements Listener {

    @EventHandler
    public void onSplashPotion(PotionSplashEvent e) {
        if (!(e.getPotion().getShooter() instanceof Player p)) return;
        if (!KitbattleAPI.getPlayerData(p).hasCooldown(p, "Biotic-Grenade")) return;
        Location loc;

        ItemStack item = e.getEntity().getItem(); // Retrieve the item from before
        // Get the data from the PDC
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        int duration;
        int amplifier;
        String value = container.get(KitBattleUpdated.dataKey, PersistentDataType.STRING);
        assert value != null;
        List<String> l = List.of(value.split(" "));
        duration = Integer.parseInt(l.get(0));
        amplifier = Integer.parseInt(l.get(1));

        if (e.getHitBlock() == null){
            loc = e.getHitEntity().getLocation();
        }else {
            loc = e.getHitBlock().getLocation().add(new Vector(0, 1, 0));
        }

        AreaEffectCloud cloud = (AreaEffectCloud) loc.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
        cloud.setDuration(20*duration);
        CustomPotionEffect effect = new CustomPotionEffect(PotionEffectType.UNLUCK, duration*20, amplifier, false, true, true, p);
        PersistentDataContainer cloudContainer = cloud.getPersistentDataContainer();
        cloudContainer.set(KitBattleUpdated.playerKey, PersistentDataType.STRING, p.getUniqueId().toString());
        int[] ints = {duration, amplifier};
        cloudContainer.set(KitBattleUpdated.valuesKey, PersistentDataType.INTEGER_ARRAY, ints);


        cloud.addCustomEffect(effect, true);
        cloud.setWaitTime(20*1);
    }

    @EventHandler
    public void onAreaApply(AreaEffectCloudApplyEvent e){
        if (e.getAffectedEntities().stream().filter((entity) -> entity instanceof Player).map(entity -> (Player) entity).noneMatch((p) -> KitbattleAPI.getPlayerData(p).getMap() != null)) return;
        List<Player> affected = e.getAffectedEntities().stream().filter((entity) -> entity instanceof Player).map(entity -> (Player) entity).toList();
        AreaEffectCloud cloud = e.getEntity();
        PersistentDataContainer container = cloud.getPersistentDataContainer();
        int[] values = container.get(KitBattleUpdated.valuesKey, PersistentDataType.INTEGER_ARRAY);
        assert values != null;
        int duration = values[0];
        int amplifier = values[1];
        Player p = Bukkit.getPlayer(UUID.fromString(container.get(KitBattleUpdated.playerKey, PersistentDataType.STRING)));
        CustomPotionEffect effect = new CustomPotionEffect(PotionEffectType.UNLUCK, duration*20, amplifier, false, true, true, p);
        for (Player pl : affected) {
            KitBattleUpdated.effectMap.put(pl.getUniqueId(), effect);
        }
    }
}
