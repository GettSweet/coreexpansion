package getsweet.coreExpansion.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldHitListener implements Listener {

    private final JavaPlugin plugin;
    private final boolean enabled;
    private final int slownessDuration;
    private final int slownessAmplifier;

    public ShieldHitListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("mechanics.shield_hit.enabled", true);
        this.slownessDuration = plugin.getConfig().getInt("mechanics.shield_hit.slowness_duration", 40);
        this.slownessAmplifier = plugin.getConfig().getInt("mechanics.shield_hit.slowness_amplifier", 2);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (!enabled) return;
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (target instanceof Player && ((Player) target).isBlocking()) {
            if (damager instanceof LivingEntity) {
                LivingEntity livingDamager = (LivingEntity) damager;
                ItemStack weapon = livingDamager.getEquipment().getItemInMainHand();

                if (weapon != null && weapon.getType().toString().endsWith("_AXE")) {
                    PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOWNESS, slownessDuration, slownessAmplifier);
                    livingDamager.addPotionEffect(slownessEffect);
                    ((LivingEntity) target).addPotionEffect(slownessEffect);
                }
            }
        }
    }
}