package xyz.brakezap.kitBattleUpdated.runnables;

import me.wazup.kitbattle.KitbattleAPI;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.brakezap.kitBattleUpdated.KitBattleUpdated;
import xyz.brakezap.kitBattleUpdated.potions.CustomPotionEffect;



public class PotionRunner extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (KitbattleAPI.getPlayerData(p).getMap() == null) continue;
            CustomPotionEffect potion = KitBattleUpdated.effectMap.getIfPresent(p.getUniqueId());
            if (potion != null) {

                Player owner = potion.getOwner();

                if (owner.getUniqueId() == p.getUniqueId()) { //Heal the player
                    int amplifier = potion.getAmplifier();
                    if (amplifier == 0) {amplifier++;}
                    double currentMaxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    double addedHealth = p.getHealth() + (amplifier*4);
                    if (addedHealth > currentMaxHealth) {
                        p.setHealth(currentMaxHealth);
                    } else {
                        p.setHealth(addedHealth);
                    }
                } else { //Damage the player
                    if (p.getHealth() - potion.getAmplifier() <= 0) {
                        p.setKiller(owner);
                    }
                    p.damage(potion.getAmplifier());
                }
            }
        }
    }
}
