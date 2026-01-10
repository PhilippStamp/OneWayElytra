package de.philippstamp.oneWayElytra.commands;

import de.philippstamp.oneWayElytra.OneWayElytra;
import de.philippstamp.oneWayElytra.listeners.OneWayElytraListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OneWayElytraCMD implements CommandExecutor {

    private OneWayElytra oneWayElytra;
    private OneWayElytraListener oneWayElytraListener;

    public OneWayElytraCMD(OneWayElytra oneWayElytra, OneWayElytraListener oneWayElytraListener) {
        this.oneWayElytra = oneWayElytra;
        this.oneWayElytraListener = oneWayElytraListener;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("onewayelytra.command")){
                if(args.length == 4) {
                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                }
                if (args.length == 3){
                    if(args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("radius")) {
                            if (!args[2].isEmpty() && oneWayElytra.tools.isInt(args[2])) {
                                oneWayElytra.getFileManager().getConfig().set("radius", Integer.valueOf(args[2]));
                                //oneWayElytra.getFileManager().saveYamls();
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("radiusSet"), Integer.valueOf(args[2])));
                            } else {
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                            }
                        } else if (args[1].equalsIgnoreCase("boost")) {
                            if (!args[2].isEmpty() && oneWayElytra.tools.isInt(args[2])) {
                                oneWayElytra.getFileManager().getConfig().set("boostMultiplier", Integer.valueOf(args[2]));
                                //oneWayElytra.getFileManager().saveYamls();
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("boostMultiplierSet"), Integer.valueOf(args[2])));
                            } else {
                                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                            }
                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                        }
                    } else if(args[0].equalsIgnoreCase("location")){
                        if (args[1].equalsIgnoreCase("add")){
                            if(args[2] != null){
                                String locationName = args[2];
                                if(true) { // Check if Location exists
                                    oneWayElytra.getFileManager().getConfig().set("locations." + locationName + ".world", player.getLocation().getWorld().getName());
                                    oneWayElytra.getFileManager().getConfig().set("locations." + locationName + ".x", player.getLocation().getX());
                                    oneWayElytra.getFileManager().getConfig().set("locations." + locationName + ".y", player.getLocation().getY());
                                    oneWayElytra.getFileManager().getConfig().set("locations." + locationName + ".z", player.getLocation().getZ());
                                    oneWayElytra.getFileManager().getConfig().set("locations." + locationName + ".radius", oneWayElytra.getFileManager().getConfig().getInt("radius"));
                                    oneWayElytra.getFileManager().saveConfig();
                                    oneWayElytra.getRadiusManager().loadAreas(oneWayElytra.getFileManager().getConfig());
                                    //oneWayElytra.getFm().getConfig().set("locations." + locationName + ".boostMultiplier", oneWayElytra.getFm().getConfig().getInt("boostMultiplier"));
                                    //oneWayElytra.getFileManager().saveYamls();
                                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("locationSet")));
                                } else{
                                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("locationNotFound")));
                                }
                            }
                        } else if (args[1].equalsIgnoreCase("remove")){
                            if(args[2] != null){
                                String locationName = args[2];
                                //if(true) { // Check if Location exists
                                if(oneWayElytra.getFileManager().getConfig().getSection("locations").contains(locationName)) { // Check if Location exists
                                    //oneWayElytra.getFileManager().getConfig().set("locations." + locationName, null);
                                    oneWayElytra.getFileManager().getConfig().remove("locations." + locationName);
                                    oneWayElytra.getFileManager().saveConfig();
                                    oneWayElytra.getRadiusManager().loadAreas(oneWayElytra.getFileManager().getConfig());
                                    //oneWayElytra.getFileManager().saveYamls();
                                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("locationRemoved")));
                                } else{
                                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("locationNotFound")));
                                }
                            }
                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                        }
                        /*
                        if (args[1].equalsIgnoreCase("add")){
                            oneWayElytra.getFm().getConfig().set("location.world", player.getWorld().getName());
                            oneWayElytra.getFm().getConfig().set("location.x", player.getLocation().getX());
                            oneWayElytra.getFm().getConfig().set("location.y", player.getLocation().getY());
                            oneWayElytra.getFm().getConfig().set("location.z", player.getLocation().getZ());
                            oneWayElytra.getFm().saveYamls();
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("locationSet")));
                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("wrongArgs")));
                        }
                        */

                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                    }

                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("location")){
                        if (args[1].equalsIgnoreCase("list")){

                        } else {
                            player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                        }
                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                    }
                }
                if (args.length == 1){
                    if(args[0].equalsIgnoreCase("set")){
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                    } else {
                        player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("wrongArgs")));
                    }
                }
                if (args.length == 0){
                    player.sendMessage(oneWayElytra.prefix + "§7/onewayelytra set <location/radius/boost> [radius/boost]");
                }
            } else {
                player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("commandDenied")));
            }
        } else {
            sender.sendMessage(oneWayElytra.prefix + "Nice try :)");
        }
        return false;
    }
}
