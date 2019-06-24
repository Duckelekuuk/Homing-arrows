package com.duckelekuuk.runnables;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@AllArgsConstructor
public class HomingArrowRunnable extends BukkitRunnable {

    private Entity arrow;
    private Entity target;

    @Override
    public void run() {

        if (arrow.isDead() || target.isDead()) {
            cancel();
            return;
        }

        Vector newVector = target.getBoundingBox().getCenter().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(newVector);
    }
}
