package getsweet.coreExpansion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class FallDamageListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final boolean enabled;
    private final double effectChance;
    private final int darknessDuration;
    private final int darknessAmplifier;
    private final int slownessDuration;
    private final int slownessAmplifier;

    public FallDamageListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.fall_damage.enabled", true);
        this.effectChance = plugin.getConfig().getDouble("mechanics.fall_damage.effect_chance", 0.3);
        this.darknessDuration = plugin.getConfig().getInt("mechanics.fall_damage.darkness_duration", 100);
        this.darknessAmplifier = plugin.getConfig().getInt("mechanics.fall_damage.darkness_amplifier", 1);
        this.slownessDuration = plugin.getConfig().getInt("mechanics.fall_damage.slowness_duration", 100);
        this.slownessAmplifier = plugin.getConfig().getInt("mechanics.fall_damage.slowness_amplifier", 1);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!enabled) return;
        if (event.getEntity() instanceof org.bukkit.entity.Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (random.nextDouble() < effectChance) {
                org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, darknessDuration, darknessAmplifier));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, slownessDuration, slownessAmplifier));
            }
        }
    }
}