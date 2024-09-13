package xyz.brakezap.kitBattleUpdated.ai;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NecromancerPathFinder extends CustomPathfinder {
    private final Player owner;
    private final double speed;
    private final EntityBrain brain;

    public NecromancerPathFinder(@NotNull Mob m, Player owner) {
        super(m);
        this.owner = owner;
        this.speed = 1.5;
        this.brain = BukkitBrain.getBrain(m);
    }

    @NotNull
    @Override
    public PathfinderFlag[] getFlags() {
        return new PathfinderFlag[]{PathfinderFlag.LOOKING, PathfinderFlag.TARGETING, PathfinderFlag.MOVEMENT};
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void start() {

    }

    @Override
    public void tick() {
        List<Player> targetList = entity.getWorld().getNearbyEntities(entity.getLocation(), 5.0, 5.0, 5.0, (e) -> e instanceof Player)
                .stream().map((e) -> (Player) e).filter((e) -> e.getUniqueId() != owner.getUniqueId())
                .toList();
        if (!targetList.isEmpty()) {
            Player target = targetList.get(0);
            brain.getController().moveTo(target.getLocation(), speed);
            brain.getEntity().attack(target);
        }

    }
}
