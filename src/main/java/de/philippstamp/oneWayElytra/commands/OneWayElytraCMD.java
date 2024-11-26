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
                if (args.length == 1) {
                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("tooManyArgs")));
                } else if (args.length == 0){
                    oneWayElytra.getFm().getConfig().set("location.world", player.getWorld().getName());
                    oneWayElytra.getFm().getConfig().set("location.x", player.getLocation().getX());
                    oneWayElytra.getFm().getConfig().set("location.y", player.getLocation().getY());
                    oneWayElytra.getFm().getConfig().set("location.z", player.getLocation().getZ());
                    oneWayElytra.getFm().saveYamls();
                    player.sendMessage(oneWayElytra.prefix + oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("locationSet")));
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
