package com.duckelekuuk.listeners;

import com.duckelekuuk.enchantments.HomingEnchantment;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@RequiredArgsConstructor
public class EnchantItemListener implements Listener {

    private final HomingEnchantment ENCHANTMENT;

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        if (!ENCHANTMENT.canEnchantItem(event.getItem())) {
            return;
        }

        if (probability(50)) {
            event.getEnchantsToAdd().put(ENCHANTMENT, 1);
            addEnchantmentName(event.getItem());
        }
    }

    private boolean probability(double percentage) {
        return Math.random() < percentage / 100;
    }

    private void addEnchantmentName(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        if (itemMeta.hasLore()) {
            itemMeta.getLore().add(ChatColor.GRAY + ENCHANTMENT.getName());
        } else {
            itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + ENCHANTMENT.getName() + " I"));
        }

        itemStack.setItemMeta(itemMeta);
    }
}
