package getsweet.coreExpansion.listeners;

import getsweet.coreExpansion.Core;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballDamageListener implements Listener {

    private final Core plugin;

    public SnowballDamageListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) {
            return;
        }
        Snowball snowball = (Snowball) event.getEntity();
        if (!(event.getHitEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity target = (LivingEntity) event.getHitEntity();

        // Получаем параметры из конфига
        double damage = plugin.getConfig().getDouble("mechanics.snowball_damage.damage", 2.0);
        boolean apply_knockback = plugin.getConfig().getBoolean("mechanics.snowball_damage.apply_knockback", true);

        target.damage(damage);
        if (apply_knockback && snowball.getShooter() instanceof LivingEntity) {
            target.damage(0.0, (LivingEntity) snowball.getShooter());
        }
    }
}