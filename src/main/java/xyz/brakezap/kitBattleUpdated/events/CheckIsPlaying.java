package xyz.brakezap.kitBattleUpdated.events;


import me.wazup.kitbattle.KitbattleAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.brakezap.kitBattleUpdated.KitBattleUpdated;


public class CheckIsPlaying implements Listener {


    @EventHandler
    public void onLeaveArenas(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().contains("kb leave") || e.getMessage().toLowerCase().contains("kitbattle leave")) {
            e.getPlayer().clearActivePotionEffects();
            KitBattleUpdated.effectMap.invalidate(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (KitbattleAPI.getPlayerData(e.getPlayer()).getMap() == null) return;

        KitBattleUpdated.effectMap.invalidate(e.getPlayer().getUniqueId());

    }

}
