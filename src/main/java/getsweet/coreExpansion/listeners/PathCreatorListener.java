package getsweet.coreExpansion.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class PathCreatorListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final boolean enabled;
    private final double pathChance;

    public PathCreatorListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.path_creator.enabled", true);
        this.pathChance = plugin.getConfig().getDouble("mechanics.path_creator.path_chance", 0.01);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!enabled) return;
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == from.getBlockZ()) {
            return;
        }

        Block block = to.getBlock().getRelative(0, -1, 0);

        if (random.nextDouble() < pathChance) {
            Material blockType = block.getType();

            if (blockType == Material.GRASS_BLOCK) {
                block.setType(Material.DIRT);
            } else if (blockType == Material.DIRT) {
                block.setType(Material.DIRT_PATH);
            }
        }
    }
}