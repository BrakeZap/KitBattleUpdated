package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.brakezap.kitBattleUpdated.KitBattleUpdated;




public class BioticGrenadeAbility extends Ability {
    int cooldown;
    static int duration;
    XMaterial activationMat;
    int heal;
    int damage;
    @Override
    public String getName() {
        return "Biotic-Grenade";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        cooldown = fileConfiguration.getInt("Abilities.Biotic-Grenade.Cooldown");
        duration = fileConfiguration.getInt("Abilities.Biotic-Grenade.Duration");
        heal = fileConfiguration.getInt("Abilities.Biotic-Grenade.Heal-Amount");
        damage = fileConfiguration.getInt("Abilities.Biotic-Grenade.Damage-Amount");
        activationMat = XMaterial.matchXMaterial(fileConfiguration.getString("Abilities.Biotic-Grenade.Activation-Material")).orElse(XMaterial.GUNPOWDER);
    }

    @Override
    public Material getActivationMaterial() {
        return activationMat.parseMaterial();
    }

    @Override
    public EntityType getActivationProjectile() {
        return null;
    }

    @Override
    public boolean isAttackActivated() {
        return false;
    }

    @Override
    public boolean isAttackReceiveActivated() {
        return false;
    }

    @Override
    public boolean isDamageActivated() {
        return false;
    }

    @Override
    public boolean isEntityInteractionActivated() {
        return false;
    }

    @Override
    public boolean execute(Player player, PlayerData playerData, Event event) {
        if (event.getEventName().equalsIgnoreCase("PlayerInteractEvent")){
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if (e.getAction().isLeftClick()) {
                return false;
            }
        }
        if (playerData.hasCooldown(player, "Biotic-Grenade")) {return false;}
        playerData.setCooldown(player, "Biotic-Grenade", cooldown, true);

        ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);


        ItemStack item = new ItemStack(Material.POTION);

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(KitBattleUpdated.dataKey, PersistentDataType.STRING, duration+" "+damage);
        item.setItemMeta(meta);
        thrownPotion.setItem(item);

        return true;
    }

    public static int getDuration() {
        return duration;
    }
}
