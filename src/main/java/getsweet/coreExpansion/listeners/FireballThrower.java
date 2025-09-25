package getsweet.coreExpansion.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FireballThrower implements Listener {

    private final JavaPlugin plugin;
    private final boolean enabled;
    private final double velocityMultiplier;
    private final boolean incendiary;
    private final float yield;

    public FireballThrower(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.fireball_thrower.enabled", true);
        this.velocityMultiplier = plugin.getConfig().getDouble("mechanics.fireball_thrower.velocity_multiplier", 2.0);
        this.incendiary = plugin.getConfig().getBoolean("mechanics.fireball_thrower.incendiary", false);
        this.yield = (float) plugin.getConfig().getDouble("mechanics.fireball_thrower.yield", 0.0);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!enabled) return;
        Player player = event.getPlayer();

        if (event.getAction().toString().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection().normalize().multiply(velocityMultiplier);

            Fireball fireball = player.launchProjectile(Fireball.class);
            fireball.setIsIncendiary(incendiary);
            fireball.setYield(yield);
            fireball.setVelocity(direction);

            ItemStack fireCharge = player.getInventory().getItemInMainHand();
            fireCharge.setAmount(fireCharge.getAmount() - 1);
        }
    }
}