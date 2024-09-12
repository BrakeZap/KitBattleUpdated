package xyz.brakezap.kitBattleUpdated.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;


public class CheckSpashPotion implements Listener {

    @EventHandler
    public void onSplashPotion(PotionSplashEvent e) {
        if (e.getPotion().getPotionMeta().hasCustomEffect(PotionEffectType.UNLUCK)) {
            Bukkit.getConsoleSender().sendMessage("Splash!");
            Location loc = e.getHitBlock().getLocation();
            AreaEffectCloud cloud = (AreaEffectCloud) loc.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
            cloud.setDuration(20*5);
            cloud.setBasePotionType(PotionType.REGEN);
        }

    }
}
