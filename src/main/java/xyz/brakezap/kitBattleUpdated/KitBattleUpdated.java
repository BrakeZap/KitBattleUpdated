package xyz.brakezap.kitBattleUpdated;

import me.wazup.kitbattle.abilities.AbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.brakezap.kitBattleUpdated.abilities.NinjaAbility;

import java.util.logging.Level;

public final class KitBattleUpdated extends JavaPlugin {

    @Override
    public void onEnable() {

        if (!getServer().getPluginManager().isPluginEnabled("KitBattle")) {
            Bukkit.getLogger().log(Level.SEVERE, "KitBattle plugin not found! Disabling new KitBattle kits!");
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("KitBattleUpdated"));
        }

        AbilityManager.getInstance().registerAbility(new NinjaAbility());

        AbilityManager.getInstance().loadAbilitiesConfig();

        AbilityManager.getInstance().updateKitAbilities();

        Bukkit.getLogger().log(Level.FINEST, "Successfully added new kits!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
