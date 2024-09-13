package xyz.brakezap.kitBattleUpdated.events;

import me.wazup.kitbattle.KitbattleAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class CheckProjectile implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        if (e.getEntity().getShooter() == null) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player p = (Player) e.getEntity().getShooter();
        if (!KitbattleAPI.getPlayerData(p).hasCooldown(p, "Archer")) return;
        p.setVelocity(new Vector().add(e.getEntity().getVelocity()));
    }
}
