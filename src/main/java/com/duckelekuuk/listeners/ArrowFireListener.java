package com.duckelekuuk.listeners;

import com.duckelekuuk.HomingArrow;
import com.duckelekuuk.runnables.HomingArrowRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ArrowFireListener implements Listener {

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player) || event.getEntity().getType() != EntityType.ARROW) {
            return;
        }

        Player shooter = (Player) event.getEntity().getShooter();

        List<Entity> nearbyEntities = shooter.getNearbyEntities(20, 20, 20);

        if (nearbyEntities.size() == 0) {
            return;
        }

        Optional<Entity> optionalEntity = nearbyEntities
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .min(Comparator.comparing(entity -> entity.getLocation().distanceSquared(shooter.getLocation())));

        if (!optionalEntity.isPresent()) {
            return;
        }

        new HomingArrowRunnable(event.getEntity(), optionalEntity.get()).runTaskTimer(JavaPlugin.getPlugin(HomingArrow.class), 5, 1);
    }
}
