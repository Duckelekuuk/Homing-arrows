package com.duckelekuuk;

import com.duckelekuuk.enchantments.HomingEnchantment;
import com.duckelekuuk.listeners.ArrowFireListener;
import com.duckelekuuk.listeners.EnchantItemListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class HomingArrow extends JavaPlugin {


    private HomingEnchantment homingEnchantment;

    @Override
    public void onEnable() {
        homingEnchantment = new HomingEnchantment();
        registerEnchantment(homingEnchantment);

        getServer().getPluginManager().registerEvents(new ArrowFireListener(homingEnchantment), this);
        getServer().getPluginManager().registerEvents(new EnchantItemListener(homingEnchantment), this);

        getCommand("givehoming").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        ItemStack itemStack = new ItemStack(Material.BOW);

        ItemMeta itemMeta = itemStack.getItemMeta();

        Enchantment enchantment = Enchantment.getByKey(homingEnchantment.getKey());

        itemMeta.addEnchant(enchantment, 1, true);

        if (itemMeta.hasLore()) {
            itemMeta.getLore().add(ChatColor.GRAY + enchantment.getName());
        } else {
            itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + enchantment.getName()));
        }

        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);

        return true;
    }

    private void registerEnchantment(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
