package xyz.brakezap.kitBattleUpdated;

import me.wazup.kitbattle.abilities.AbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.brakezap.kitBattleUpdated.events.CheckIsPlaying;
import xyz.brakezap.kitBattleUpdated.events.CheckSpashPotion;
import xyz.brakezap.kitBattleUpdated.abilities.*;

import java.util.logging.Level;

public final class KitBattleUpdated extends JavaPlugin {
    public static KitBattleUpdated instance;
    @Override
    public void onEnable() {
        instance = this;
        if (!getServer().getPluginManager().isPluginEnabled("KitBattle")) {
            Bukkit.getLogger().log(Level.SEVERE, "KitBattle plugin not found! Disabling new KitBattle kits!");
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("KitBattleUpdated"));
        }

        AbilityManager.getInstance().registerAbility(new NinjaAbility());
        AbilityManager.getInstance().registerAbility(new BioticGrenadeAbility());
        AbilityManager.getInstance().registerAbility(new FrostyAbility());
        AbilityManager.getInstance().registerAbility(new ZombieAbility());
        AbilityManager.getInstance().registerAbility(new NecromancerAbility());

        AbilityManager.getInstance().loadAbilitiesConfig();

        AbilityManager.getInstance().updateKitAbilities();

        Bukkit.getPluginManager().registerEvents(new CheckSpashPotion(), this);
        Bukkit.getPluginManager().registerEvents(new CheckIsPlaying(), this);

        Bukkit.getLogger().log(Level.FINEST, "Successfully added new kits!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
