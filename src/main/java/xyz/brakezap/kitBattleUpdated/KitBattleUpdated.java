package xyz.brakezap.kitBattleUpdated;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import me.wazup.kitbattle.abilities.AbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.brakezap.kitBattleUpdated.events.CheckIsPlaying;
import xyz.brakezap.kitBattleUpdated.events.CheckProjectile;
import xyz.brakezap.kitBattleUpdated.events.CheckSplashPotion;
import xyz.brakezap.kitBattleUpdated.abilities.*;
import xyz.brakezap.kitBattleUpdated.potions.CustomPotionEffect;
import xyz.brakezap.kitBattleUpdated.runnables.PotionRunner;

import java.time.Duration;
import java.util.UUID;
import java.util.logging.Level;

public final class KitBattleUpdated extends JavaPlugin {
    public static KitBattleUpdated instance;
    private BukkitTask potionRunner;
    public static Cache<UUID, CustomPotionEffect> effectMap;
    public static NamespacedKey dataKey;
    public static NamespacedKey playerKey;
    public static NamespacedKey valuesKey;
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
        AbilityManager.getInstance().registerAbility(new ArcherAbility());

        AbilityManager.getInstance().loadAbilitiesConfig();

        AbilityManager.getInstance().updateKitAbilities();

        Bukkit.getPluginManager().registerEvents(new CheckSplashPotion(), this);
        Bukkit.getPluginManager().registerEvents(new CheckIsPlaying(), this);
        Bukkit.getPluginManager().registerEvents(new CheckProjectile(), this);


        potionRunner = new PotionRunner().runTaskTimer(this, 5*20, 1*20);
        effectMap = Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(BioticGrenadeAbility.getDuration())).build();
        dataKey = new NamespacedKey(this, "data");
        playerKey = new NamespacedKey(this, "player");
        valuesKey = new NamespacedKey(this, "values");
        this.getLogger().log(Level.INFO, "Finished setting up!");
    }

    @Override
    public void onDisable() {
        potionRunner.cancel();
    }
}
