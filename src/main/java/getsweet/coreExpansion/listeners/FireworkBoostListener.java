package getsweet.coreExpansion.listeners;

import getsweet.coreExpansion.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class FireworkBoostListener implements Listener {

    private final Core plugin;

    public FireworkBoostListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseFirework(PlayerInteractEvent event) {
        if (!plugin.getConfig().getBoolean("mechanics.firework_boost.enabled", true)) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                plugin.getConfig().getBoolean("mechanics.firework_boost.work_on_air", false)) {

            Player player = event.getPlayer();

            if (event.getItem() != null && event.getItem().getType() == Material.FIREWORK_ROCKET) {
                double boostPower = plugin.getConfig().getDouble("mechanics.firework_boost.power", 1.0);
                Vector boost = player.getVelocity().setY(boostPower);
                player.setVelocity(boost);

                if (plugin.getConfig().getBoolean("mechanics.firework_boost.cancel_original_behavior", true)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}