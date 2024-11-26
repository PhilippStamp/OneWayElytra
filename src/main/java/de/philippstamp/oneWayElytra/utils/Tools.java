package de.philippstamp.oneWayElytra.utils;

import de.philippstamp.oneWayElytra.OneWayElytra;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.KeybindComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tools {

  private OneWayElytra oneWayElytra;

  public Tools(OneWayElytra oneWayElytra){
    oneWayElytra = oneWayElytra;
  }

  public String replaceVariables(String text) {
    return ChatColor.translateAlternateColorCodes('&', text)
              .replaceAll("%>>%", "»")
              .replaceAll("%<<%", "«")
              .replaceAll("%ue%", "ü")
              .replaceAll("%ae%", "ä")
              .replaceAll("%oe%", "ö")
              .replaceAll("%ss%", "ß")
              .replaceAll("%coin%", "⛂")
              .replaceAll("%coins%", "⛃");
  }

  public String replaceVariables(Player player, String text) {
    return ChatColor.translateAlternateColorCodes('&', text)
              .replaceAll("%>>%", "»")
              .replaceAll("%<<%", "«")
              .replaceAll("%ue%", "ü")
              .replaceAll("%ae%", "ä")
              .replaceAll("%oe%", "ö")
              .replaceAll("%ss%", "ß")
              .replaceAll("%coin%", "⛂")
              .replaceAll("%coins%", "⛃")
              .replaceAll("%player%", player.getName());
  }

  public String replaceVariables(String text, Double money) {
    return ChatColor.translateAlternateColorCodes('&', text)
                    .replaceAll("%>>%", "»")
                    .replaceAll("%<<%", "«")
                    .replaceAll("%ue%", "ü")
                    .replaceAll("%ae%", "ä")
                    .replaceAll("%oe%", "ö")
                    .replaceAll("%ss%", "ß")
                    .replaceAll("%coin%", "⛂")
                    .replaceAll("%coins%", "⛃")
                    .replaceAll("%money%", money.toString());
  }

  public String replaceVariables(Player player, String text, Double money) {
    return ChatColor.translateAlternateColorCodes('&', text)
                    .replaceAll("%>>%", "»")
                    .replaceAll("%<<%", "«")
                    .replaceAll("%ue%", "ü")
                    .replaceAll("%ae%", "ä")
                    .replaceAll("%oe%", "ö")
                    .replaceAll("%ss%", "ß")
                    .replaceAll("%coin%", "⛂")
                    .replaceAll("%coins%", "⛃")
                    .replaceAll("%money%", money.toString())
                    .replaceAll("%player%", player.getName());
  }

  public String replaceVariables(String text, int time) {
    return ChatColor.translateAlternateColorCodes('&', text)
            .replaceAll("%>>%", "»")
            .replaceAll("%<<%", "«")
            .replaceAll("%ue%", "ü")
            .replaceAll("%ae%", "ä")
            .replaceAll("%oe%", "ö")
            .replaceAll("%ss%", "ß")
            .replaceAll("%coin%", "⛂")
            .replaceAll("%coins%", "⛃")
            .replaceAll("%time%", time + "");
  }

  public boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

}
