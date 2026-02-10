package de.jugglegaming.oneWayElytra.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class WorldguardUtils {

    public static boolean isWorldGuardInstalled() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin worldGuard = pluginManager.getPlugin("WorldGuard");

        return worldGuard != null;
        //return worldGuard != null && worldGuard.isEnabled();
    }

}
