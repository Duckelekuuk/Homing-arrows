package com.duckelekuuk;

import com.duckelekuuk.listeners.ArrowFireListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomingArrow extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ArrowFireListener(), this);
    }
}
