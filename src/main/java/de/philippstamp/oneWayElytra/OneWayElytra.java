package de.philippstamp.oneWayElytra;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class OneWayElytra extends JavaPlugin {

    public ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();

    @Override
    public void onEnable() {
        ccs.sendMessage("ENABLED");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
