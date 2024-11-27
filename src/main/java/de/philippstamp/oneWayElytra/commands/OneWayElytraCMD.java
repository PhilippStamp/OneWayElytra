package de.philippstamp.oneWayElytra.commands;

import de.philippstamp.oneWayElytra.OneWayElytra;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OneWayElytraCMD implements CommandExecutor {

    private OneWayElytra oneWayElytra;

    public OneWayElytraCMD(OneWayElytra oneWayElytra){
        this.oneWayElytra = oneWayElytra;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("onewayelytra.command")){
                if(args.length == 4) {
                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                }
                if (args.length == 3){
                    if(args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("radius")) {
                            if (!args[2].isEmpty() && oneWayElytra.tools.isInt(args[2])) {
                                oneWayElytra.getFm().getConfig().set("radius", Integer.valueOf(args[2]));
                                oneWayElytra.getFm().saveYamls();
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("radiusSet"), Integer.valueOf(args[2])));
                            } else {
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                            }
                        } else if (args[1].equalsIgnoreCase("boost")) {
                            if (!args[2].isEmpty() && oneWayElytra.tools.isInt(args[2])) {
                                oneWayElytra.getFm().getConfig().set("boostMultiplier", Integer.valueOf(args[2]));
                                oneWayElytra.getFm().saveYamls();
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMultiplierSet"), Integer.valueOf(args[2])));
                            } else {
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                            }
                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                        }
                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                    }

                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("set")){
                        if (args[1].equalsIgnoreCase("location")){
                            oneWayElytra.getFm().getConfig().set("location.world", player.getWorld().getName());
                            oneWayElytra.getFm().getConfig().set("location.x", player.getLocation().getX());
                            oneWayElytra.getFm().getConfig().set("location.y", player.getLocation().getY());
                            oneWayElytra.getFm().getConfig().set("location.z", player.getLocation().getZ());
                            oneWayElytra.getFm().saveYamls();
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("locationSet")));
                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                        }
                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                    }
                }
                if (args.length == 1){
                    if(args[0].equalsIgnoreCase("set")){
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                    }
                }
                if (args.length == 0){
                    player.sendMessage(oneWayElytra.prefix + "§7/onewayelytra set <location/radius/boost> [radius/boost]");
                }
            } else {
                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("commandDenied")));
            }
        } else {
            sender.sendMessage(oneWayElytra.prefix + "Nice try :)");
        }
        return false;
    }
}
