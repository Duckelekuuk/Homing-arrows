package com.duckelekuuk;

import com.duckelekuuk.enchantments.HomingEnchantment;
import com.duckelekuuk.listeners.ArrowFireListener;
import com.duckelekuuk.listeners.EnchantItemListener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class HomingArrow extends JavaPlugin {


    @Override
    public void onEnable() {
        HomingEnchantment homingEnchantment = new HomingEnchantment();
        registerEnchantment(homingEnchantment);

        getServer().getPluginManager().registerEvents(new ArrowFireListener(homingEnchantment), this);
        getServer().getPluginManager().registerEvents(new EnchantItemListener(homingEnchantment), this);
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
