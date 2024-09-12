package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.KitbattleAPI;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
            Bukkit.getConsoleSender().sendMessage("Damaged with zombie event");
            Bukkit.getConsoleSender().sendMessage(p.getHealth() + "");
            if (p.getHealth() <= e.getFinalDamage()) {
                Bukkit.getConsoleSender().sendMessage("Adding hearts!");
                double currentHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentHealth + hearts);
                player.setHealth(currentHealth + hearts);
                player.sendHealthUpdate();
                return true;
            }
        }
        return false;

    }
}
