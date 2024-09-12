package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class NinjaAbility extends Ability {

    int cooldown;
    int duration;
    int speed;
    XMaterial activationMat;

    @Override
    public String getName() {
        return "Ninja";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        cooldown = fileConfiguration.getInt("Abilities.Ninja.Cooldown");
        duration = fileConfiguration.getInt("Abilities.Ninja.Duration");
        speed = fileConfiguration.getInt("Abilities.Ninja.Speed-Amount");
        activationMat = XMaterial.matchXMaterial(Objects.requireNonNull(fileConfiguration.getString("Abilities.Ninja.Activation-Material"))).orElse(XMaterial.DIAMOND_SWORD);
    }

    @Override
    public Material getActivationMaterial() {
        return activationMat.parseMaterial();
    }

    @Override
    public EntityType getActivationProjectile() {
        return null;
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
        if (event.getEventName().equalsIgnoreCase("PlayerInteractEvent")){
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if (e.getAction().isLeftClick()) {
                Bukkit.getConsoleSender().sendMessage("Is a left click!");
                return false;
            }

        }
        if (playerData.hasCooldown(player, "Ninja")) return false;
        Bukkit.getConsoleSender().sendMessage(event.getEventName());
        playerData.setCooldown(player, "Ninja", cooldown, true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration*20,
                speed));

        return true;
    }
}
