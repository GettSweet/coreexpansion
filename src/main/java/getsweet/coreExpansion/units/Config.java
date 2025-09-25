package getsweet.coreExpansion.units;

import getsweet.coreExpansion.Core;
import getsweet.coreExpansion.listeners.*;
import org.bukkit.plugin.PluginManager;

public class Config {

    private final Core plugin;

    public Config(Core plugin) {
        this.plugin = plugin;
    }

    public void registerMechanics() {
        PluginManager pm = plugin.getServer().getPluginManager();

        if (plugin.getConfig().getBoolean("mechanics.firework_boost.enabled", true)) {
            pm.registerEvents(new FireworkBoostListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.firework_crossbow.enabled", true)) {
            pm.registerEvents(new FireworkCrossbowListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.totem_drop.enabled", true)) {
            pm.registerEvents(new TotemDropListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.action_bar.enabled", true)) {
            pm.registerEvents(new ActionBarListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.fireball_thrower.enabled", true)) {
            pm.registerEvents(new FireballThrower(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.fall_damage.enabled", true)) {
            pm.registerEvents(new FallDamageListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.shield_hit.enabled", true)) {
            pm.registerEvents(new ShieldHitListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.path_creator.enabled", true)) {
            pm.registerEvents(new PathCreatorListener(plugin), plugin);
        }

        if (plugin.getConfig().getBoolean("mechanics.skeleton_spawn.enabled", true)) {
            pm.registerEvents(new SkeletonSpawnListener(plugin), plugin);
        }

        // Добавляем регистрацию слушателя для снежков
        if (plugin.getConfig().getBoolean("mechanics.snowball_damage.enabled", true)) {
            pm.registerEvents(new SnowballDamageListener(plugin), plugin);
        }

        plugin.getLogger().info("Mechanics Status: " +
                "Firework Boost: " + plugin.getConfig().getBoolean("mechanics.firework_boost.enabled") +
                ", Firework Crossbow: " + plugin.getConfig().getBoolean("mechanics.firework_crossbow.enabled") +
                ", Totem Drop: " + plugin.getConfig().getBoolean("mechanics.totem_drop.enabled") +
                ", Action Bar: " + plugin.getConfig().getBoolean("mechanics.action_bar.enabled") +
                ", Fireball Thrower: " + plugin.getConfig().getBoolean("mechanics.fireball_thrower.enabled") +
                ", Fall Damage: " + plugin.getConfig().getBoolean("mechanics.fall_damage.enabled") +
                ", Shield Hit: " + plugin.getConfig().getBoolean("mechanics.shield_hit.enabled") +
                ", Path Creator: " + plugin.getConfig().getBoolean("mechanics.path_creator.enabled") +
                ", Skeleton Spawn: " + plugin.getConfig().getBoolean("mechanics.skeleton_spawn.enabled") +
                ", Snowball Damage: " + plugin.getConfig().getBoolean("mechanics.snowball_damage.enabled"));
    }
}