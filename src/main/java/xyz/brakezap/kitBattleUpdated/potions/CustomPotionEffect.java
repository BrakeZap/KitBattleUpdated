package xyz.brakezap.kitBattleUpdated.potions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class CustomPotionEffect extends PotionEffect {

    Player owner;

    public CustomPotionEffect(@NotNull PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon, Player owner) {
        super(type, duration, amplifier, ambient, particles, icon);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
