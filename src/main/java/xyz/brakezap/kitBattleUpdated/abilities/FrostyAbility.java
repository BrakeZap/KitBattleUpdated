package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class FrostyAbility extends Ability {

    XMaterial activationMat;
    @Override
    public String getName() {
        return "Frosty";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        activationMat = XMaterial.matchXMaterial(fileConfiguration.getString("Abilities.Frosty.Activation-Material")).orElse(XMaterial.SNOWBALL);
    }

    @Override
    public Material getActivationMaterial() {
        return activationMat.parseMaterial();
    }

    @Override
    public EntityType getActivationProjectile() {
        return EntityType.SNOWBALL;
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
        if (event.getEventName().equalsIgnoreCase("PlayerInteractEvent")) {
            player.launchProjectile(Snowball.class, player.getLocation().getDirection());
            return true;
        }

        if (event.getEventName().equalsIgnoreCase("EntityDamageByEntityEvent")){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Snowball s = (Snowball) e.getDamager();
            Player shooter = (Player) s.getShooter();
            Vector looking = shooter.getLocation().getDirection();
            Player p = (Player) e.getEntity();
            Vector vel = p.getVelocity();
            //Bukkit.getConsoleSender().sendMessage("Pushing!!");
            p.setVelocity(new Vector(vel.getX() + looking.getX(), vel.getY() + looking.getY(), vel.getZ() + looking.getZ()));
            return true;
        }


        return false;
    }
}
