package getsweet.coreExpansion.listeners;

import getsweet.coreExpansion.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FireworkCrossbowListener implements Listener {

    private final Core plugin;

    public FireworkCrossbowListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!plugin.getConfig().getBoolean("mechanics.firework_crossbow.enabled", true)) {
            return;
        }

        Projectile projectile = event.getEntity();

        if (projectile instanceof Firework) {
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();

                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand.getType() == Material.CROSSBOW) {
                    Vector direction = projectile.getVelocity().normalize();
                    Vector playerVelocity = player.getVelocity();

                    double upwardMultiplier = plugin.getConfig().getDouble("mechanics.firework_crossbow.upward_multiplier", 2.0);
                    double backwardMultiplier = plugin.getConfig().getDouble("mechanics.firework_crossbow.backward_multiplier", -0.5);
                    double downwardForce = plugin.getConfig().getDouble("mechanics.firework_crossbow.downward_force", -5.0);
                    double horizontalMultiplier = plugin.getConfig().getDouble("mechanics.firework_crossbow.horizontal_multiplier", -2.0);

                    if (direction.getY() > plugin.getConfig().getDouble("mechanics.firework_crossbow.upward_threshold", 0.5)) {
                        playerVelocity.setY(upwardMultiplier);
                        playerVelocity.add(direction.multiply(backwardMultiplier));

                        playerVelocity.setY(playerVelocity.getY() + downwardForce);
                    } else {
                        playerVelocity.add(direction.multiply(horizontalMultiplier));
                    }

                    player.setVelocity(playerVelocity);

                    if (plugin.getConfig().getBoolean("mechanics.firework_crossbow.cancel_firework_launch", false)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}