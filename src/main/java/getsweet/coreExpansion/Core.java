package getsweet.coreExpansion;

import getsweet.coreExpansion.listeners.*;
import getsweet.coreExpansion.units.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new FireworkCrossbowListener(this), this);
        getServer().getPluginManager().registerEvents(new FireworkBoostListener(this), this);
        getServer().getPluginManager().registerEvents(new TotemDropListener(this), this);
        getServer().getPluginManager().registerEvents(new ActionBarListener(this), this);
        getServer().getPluginManager().registerEvents(new FireballThrower(this), this);
        getServer().getPluginManager().registerEvents(new FallDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new ShieldHitListener(this), this);
        getServer().getPluginManager().registerEvents(new PathCreatorListener(this), this);
        getServer().getPluginManager().registerEvents(new SkeletonSpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new SnowballDamageListener(this), this);

        new Config(this).registerMechanics();

    }

    @Override
    public void onDisable() {
    }
}
