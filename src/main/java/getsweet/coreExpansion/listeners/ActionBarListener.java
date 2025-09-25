package getsweet.coreExpansion.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ActionBarListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final boolean enabled;
    private final long updateInterval;
    private final int randomCoordinatesRange;
    private final int randomCoordinatesYMax;

    public ActionBarListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.action_bar.enabled", true);
        this.updateInterval = plugin.getConfig().getLong("mechanics.action_bar.update_interval", 1);
        this.randomCoordinatesRange = plugin.getConfig().getInt("mechanics.action_bar.random_coordinates_range", 50001);
        this.randomCoordinatesYMax = plugin.getConfig().getInt("mechanics.action_bar.random_coordinates_y_max", 256);

        if (enabled) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        updateActionBar(player);
                    }
                }
            }.runTaskTimer(plugin, 0, updateInterval);
        }
    }

    private void updateActionBar(Player player) {
        if (!enabled) return;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();

        boolean isRegularCompass = isCompassWithoutLodestone(itemInMainHand) || isCompassWithoutLodestone(itemInOffHand);
        boolean isLodestoneCompass = hasLodestone(itemInMainHand) || hasLodestone(itemInOffHand);

        if (isRegularCompass) {
            if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                String randomCoordinates = String.format("X: %d, Y: %d, Z: %d",
                        random.nextInt(randomCoordinatesRange) - randomCoordinatesRange / 2,
                        random.nextInt(randomCoordinatesYMax),
                        random.nextInt(randomCoordinatesRange) - randomCoordinatesRange / 2
                );
                sendActionBar(player, randomCoordinates);
            } else {
                Location loc = player.getLocation();
                String coordinates = String.format("X: %.1f, Y: %.1f, Z: %.1f", loc.getX(), loc.getY(), loc.getZ());
                sendActionBar(player, coordinates);
            }
        } else if (isLodestoneCompass) {
            sendActionBar(player, "");
        } else if (itemInMainHand.getType() == Material.CLOCK || itemInOffHand.getType() == Material.CLOCK) {
            if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                int randomHours = random.nextInt(24);
                int randomMinutes = random.nextInt(60);
                String randomTime = String.format("%02d:%02d", randomHours, randomMinutes);
                sendActionBar(player, randomTime);
            } else {
                long time = player.getWorld().getTime();
                int hours = (int) ((time / 1000 + 6) % 24);
                int minutes = (int) ((time % 1000) * 60 / 1000);
                String worldTime = String.format("%02d:%02d", hours, minutes);
                sendActionBar(player, worldTime);
            }
        }
    }

    private boolean isCompassWithoutLodestone(ItemStack item) {
        if (item.getType() != Material.COMPASS) {
            return false;
        }
        CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
        return compassMeta != null && !compassMeta.hasLodestone();
    }

    private boolean hasLodestone(ItemStack item) {
        if (item.getType() != Material.COMPASS) {
            return false;
        }
        CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
        return compassMeta != null && compassMeta.hasLodestone();
    }

    private void sendActionBar(Player player, String message) {
        TextComponent textComponent = new TextComponent(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if (!enabled) return;
        Player player = event.getPlayer();
        updateActionBar(player);
    }
}