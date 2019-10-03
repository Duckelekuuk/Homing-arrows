package com.duckelekuuk.runnables;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HomingArrowRunnable extends BukkitRunnable {

    private Entity arrow;
    private Entity target;

    public HomingArrowRunnable(Entity arrow) {
        this.arrow = arrow;
    }

    @Override
    public void run() {

        if (target == null) 
            setTarget();

        if (arrow.isDead() || target.isDead()) {
            cancel();
            return;
        }

        Vector newVector = target.getBoundingBox().getCenter().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(newVector);
    }


    private void setTarget() {
        List<Entity> nearbyEntities = arrow.getNearbyEntities(20, 20, 20);

        if (nearbyEntities.size() == 0)
            cancel();

        Optional<Entity> optionalEntity = nearbyEntities.stream()
                .filter(entity -> entity instanceof LivingEntity)
                .min(Comparator.comparing(entity -> entity.getLocation().distanceSquared(arrow.getLocation())));

        if (!optionalEntity.isPresent())
            cancel();

        target = optionalEntity.get();
    }
}
