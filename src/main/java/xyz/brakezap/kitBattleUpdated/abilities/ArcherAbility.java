package xyz.brakezap.kitBattleUpdated.abilities;

import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArcherAbility extends Ability {

    int cooldown;
    XMaterial activationMat;
    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public void load(FileConfiguration fileConfiguration) {
        cooldown = fileConfiguration.getInt("Abilities.Archer.Cooldown");
        activationMat = XMaterial.matchXMaterial(fileConfiguration.getString("Abilities.Archer.Activation-Material")).orElse(XMaterial.BOW);
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
            if (e.getAction().isRightClick()) {
                return false;
            }
        }
        if (playerData.hasCooldown(player, "Archer")) return false;
        playerData.setCooldown(player, "Archer", cooldown, true);

        player.launchProjectile(Arrow.class);

        return true;
    }
}
