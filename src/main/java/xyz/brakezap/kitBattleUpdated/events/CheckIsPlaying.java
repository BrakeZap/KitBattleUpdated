package xyz.brakezap.kitBattleUpdated.events;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class CheckIsPlaying implements Listener {


    @EventHandler
    public void onLeaveArenas(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().contains("kb leave") || e.getMessage().toLowerCase().contains("kitbattle leave")) {
            Bukkit.getConsoleSender().sendMessage("Left arena");
            e.getPlayer().clearActivePotionEffects();

        }
    }

}
