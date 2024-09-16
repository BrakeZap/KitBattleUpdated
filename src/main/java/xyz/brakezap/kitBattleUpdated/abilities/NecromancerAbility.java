package xyz.brakezap.kitBattleUpdated.abilities;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import xyz.brakezap.kitBattleUpdated.KitBattleUpdated;
import xyz.brakezap.kitBattleUpdated.ai.NecromancerPathFinder;

public class NecromancerAbility extends Ability {

    int duration;
    @Override
    public String getName() {
        return "Necromancer";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        duration = fileConfiguration.getInt("Abilities.Necromancer.Duration");
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
                Zombie z = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                z.setBaby();
                z.getPersistentDataContainer().set(KitBattleUpdated.dataKey, PersistentDataType.BOOLEAN, true);
                z.setShouldBurnInDay(false);
                EntityBrain brain = BukkitBrain.getBrain(z);
                brain.getGoalAI().clear();
                brain.getTargetAI().clear();
                brain.getTargetAI().put(new NecromancerPathFinder(z, player), 0);
                Bukkit.getScheduler().runTaskLater(KitBattleUpdated.instance, (task) -> {
                        z.setHealth(0);
                        }, duration*20L);
                return true;
            }
        }
        return false;
    }

}
