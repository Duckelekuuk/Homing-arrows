package com.duckelekuuk.listeners;

import com.duckelekuuk.HomingArrow;
import com.duckelekuuk.enchantments.HomingEnchantment;
import com.duckelekuuk.runnables.HomingArrowRunnable;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ArrowFireListener implements Listener {

    private final HomingEnchantment ENCHANTMENT;

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player) || event.getEntity().getType() != EntityType.ARROW) {
            return;
        }

        Player shooter = (Player) event.getEntity().getShooter();

        if (shooter.getInventory().getItemInMainHand().getItemMeta() == null)
            return;
        if (!shooter.getInventory().getItemInMainHand().getItemMeta().hasEnchant(ENCHANTMENT))
            return;

        new HomingArrowRunnable(event.getEntity()).runTaskTimer(JavaPlugin.getPlugin(HomingArrow.class), 5, 1);
    }

}
