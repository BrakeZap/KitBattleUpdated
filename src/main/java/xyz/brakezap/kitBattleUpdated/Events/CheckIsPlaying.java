package xyz.brakezap.kitBattleUpdated.Events;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.KitbattleAPI;
import me.wazup.kitbattle.events.PlayerSelectKitEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;


public class CheckIsPlaying implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (KitbattleAPI.getPlayerData(e.getPlayer()).getMap() != null)
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }

    @EventHandler
    public void onLeaveArenas(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("kb leave") || e.getMessage().equalsIgnoreCase("kitbattle leave")) {
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
            e.getPlayer().setHealth(20);
        }
    }

}
