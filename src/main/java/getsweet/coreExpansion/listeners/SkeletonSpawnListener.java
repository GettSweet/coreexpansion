package getsweet.coreExpansion.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class SkeletonSpawnListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final boolean enabled;
    private final double swordChance;
    private final Material swordMaterial;

    public SkeletonSpawnListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.skeleton_spawn.enabled", true);
        this.swordChance = plugin.getConfig().getDouble("mechanics.skeleton_spawn.sword_chance", 0.3);
        this.swordMaterial = Material.getMaterial(plugin.getConfig().getString("mechanics.skeleton_spawn.sword_material", "IRON_SWORD"));
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!enabled) return;
        if (event.getEntityType() == EntityType.SKELETON) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            if (random.nextDouble() <= swordChance) {
                skeleton.getEquipment().setItemInMainHand(new ItemStack(swordMaterial));
            }
        }
    }
}