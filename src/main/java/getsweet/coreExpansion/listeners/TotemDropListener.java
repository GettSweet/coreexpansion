package getsweet.coreExpansion.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Evoker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TotemDropListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final boolean enabled;
    private final double dropChance;

    public TotemDropListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.totem_drop.enabled", true);
        this.dropChance = plugin.getConfig().getDouble("mechanics.totem_drop.drop_chance", 0.004);
    }

    @EventHandler
    public void onEvokerDeath(EntityDeathEvent event) {
        if (!enabled) return;
        if (event.getEntity() instanceof Evoker) {
            event.getDrops().removeIf(item -> item.getType() == Material.TOTEM_OF_UNDYING);
            if (random.nextDouble() <= dropChance) {
                event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING));
            }
        }
    }
}