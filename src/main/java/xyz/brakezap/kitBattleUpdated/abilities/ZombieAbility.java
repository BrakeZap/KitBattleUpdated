package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ZombieAbility extends Ability {
    int hearts;

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        hearts = fileConfiguration.getInt("Abilities.Zombie.Heart-Amount");
    }

    @Override
    public Material getActivationMaterial() {
        return null;
    }

    @Override
    public EntityType getActivationProjectile() {
        return null;
    }

    @Override
    public boolean isAttackActivated() {
        return true;
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
        if (event.getEventName().equalsIgnoreCase("EntityDamageByEntityEvent")){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Player p = (Player) e.getEntity();
            if (p.getHealth() <= e.getFinalDamage()) {
                int currentBoost = 0;
                List<PotionEffect> effects = player.getActivePotionEffects().stream().toList();
                player.clearActivePotionEffects();
                if (!effects.isEmpty()) {
                    //Bukkit.getConsoleSender().sendMessage("Effects not empty!");
                    for (PotionEffect potion : effects) {
                        //Bukkit.getConsoleSender().sendMessage(potion.getType().getName());
                        //Bukkit.getConsoleSender().sendMessage((potion.getType().getName().equalsIgnoreCase("HEALTH_BOOST")) + "");
                        if (potion.getType().getName().equalsIgnoreCase("HEALTH_BOOST")) {
                            //Bukkit.getConsoleSender().sendMessage("Current boost of " + potion.getAmplifier());
                            currentBoost = potion.getAmplifier();
                            if (currentBoost >= 0) {
                                currentBoost++;
                            }
                            continue;
                        }
                        player.addPotionEffect(potion);
                    }
                }
                //Bukkit.getConsoleSender().sendMessage("Current hearts are: " + hearts);
                //Bukkit.getConsoleSender().sendMessage("Adding health boost of " + (((hearts/2)+currentBoost)-1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, PotionEffect.INFINITE_DURATION, ((hearts/2)+currentBoost)-1));
                return true;
            }
        }
        return false;
    }
}
