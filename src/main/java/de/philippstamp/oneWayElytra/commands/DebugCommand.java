package de.philippstamp.oneWayElytra.commands;

import de.karimkeshta.oneWayElytra.regions.ElytraRegion;
import de.philippstamp.oneWayElytra.OneWayElytra;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class DebugCommand implements CommandExecutor {

    private OneWayElytra plugin;
    private final String commandName = "debugOneWayElytra";

    public DebugCommand(OneWayElytra plugin){
        this.plugin = plugin;
        this.plugin.getCommand(commandName).setExecutor(this);
        this.plugin.getCommand(commandName).setAliases(Arrays.asList("dowe", "debugOWE"));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            commandSender.sendMessage(ChatColor.RED + "This command is only available at console.");
            return true;
        }

        ConsoleCommandSender consoleSender = (ConsoleCommandSender) commandSender;

        if(args.length == 0){
            commandSender.sendMessage(ChatColor.RED + "WARNING! \n This command has no arguments validations. \n Only use it, if you know what you do!");
        }

        if(args[0].equalsIgnoreCase("create")){
            ElytraRegion elytraRegion = new ElytraRegion("Test", new Location(Bukkit.getServer().getWorlds().get(0), 0, 0, 0), 12, 6, 3.5f);

        }


        return false;
    }
}
