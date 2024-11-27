package de.philippstamp.oneWayElytra.listeners;

import de.philippstamp.oneWayElytra.OneWayElytra;
import de.philippstamp.oneWayElytra.utils.ActionBar;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.List;

public class OneWayElytraListener implements Listener {

    private int radius;
    private int boostMultiplier;
    private List<Player> playersFlying = new ArrayList<>();
    private List<Player> playersBoosted = new ArrayList<>();

    private OneWayElytra oneWayElytra;

    public OneWayElytraListener(OneWayElytra oneWayElytra){
        this.oneWayElytra = oneWayElytra;
        this.radius = oneWayElytra.getFm().getConfig().getInt("radius");

        Bukkit.getScheduler().runTaskTimer(oneWayElytra, () -> {
            Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world")).getPlayers().forEach(player -> {
                if(player.getGameMode() == GameMode.SURVIVAL){
                    player.setAllowFlight(playerInRadius(player));
                    if(playersFlying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()){
                        player.setAllowFlight(false);
                        player.setGliding(false);
                        playersBoosted.remove(player);

                        Bukkit.getScheduler().runTaskLater(oneWayElytra, () -> {
                            playersFlying.remove(player);
                        }, 5);
                    }
                }

            });
        }, 0, 3);

    }

    @EventHandler
    public void onToogleFlight(PlayerToggleFlightEvent event){
        if(event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            if (playerInRadius(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().setGliding(true);
                playersFlying.add(event.getPlayer());
                ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage")));

                /*
                String message = oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage"));
                String[] split = message.split("%keybinding%");

                /// NEU ANFANG
                ChatColor primary_color = ChatColor.GRAY;
                //if(oneWayElytra.getFm().getMessages().getString("primary_color") != null)
                    //primary_color = ChatColor.of(oneWayElytra.getFm().getMessages().getString("secondary_color"));

                ChatColor secondary_color = ChatColor.YELLOW;
                //if(oneWayElytra.getFm().getMessages().getString("secondary_color") != null)
                    //secondary_color = ChatColor.of(oneWayElytra.getFm().getMessages().getString("secondary_color"));


                TextComponent tc1 = new TextComponent(split[0]); tc1.setColor(primary_color);
                TextComponent tc2 = new TextComponent(new KeybindComponent("key.swapOffhand")); tc2.setColor(secondary_color);
                TextComponent tc3 = new TextComponent(split[1]); tc3.setColor(primary_color);
                BaseComponent[] baseComponent = new ComponentBuilder().append(tc1).append(tc2).append(tc3).create();


                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, baseComponent);
                /// NEU ENDE


                /**
                 *   Ursprüngliche Version
                 */
                /*
                TextComponent textComponent =  new TextComponent();
                textComponent.addExtra(new TextComponent(split[0]));
                textComponent.addExtra(new KeybindComponent("key.swapOffhand"));
                textComponent.addExtra(new TextComponent(split[1]));

                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
                */
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.PLAYER){
            if(playersFlying.contains(event.getEntity())){
                if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                    event.setCancelled(true);
                } else if(event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL){
                    event.setCancelled(true);
                }
            }
        }
    }

    /*
    @EventHandler
    public void onDoubleJump(PlayerSwapHandItemsEvent event){
        if (!playersBoosted.contains(event.getPlayer()) && playersFlying.contains(event.getPlayer())) {
            event.setCancelled(true);
            playersBoosted.add(event.getPlayer());
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(boostMultiplier));
        }
    }
    */

    @EventHandler
    public void onBoost(PlayerInteractEvent event){
        if (!playersBoosted.contains(event.getPlayer()) && playersFlying.contains(event.getPlayer())) {
            this.boostMultiplier = oneWayElytra.getFm().getConfig().getInt("boostMultiplier");
            if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
                event.setCancelled(true);
                playersBoosted.add(event.getPlayer());
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(boostMultiplier));
            } else if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                event.setCancelled(true);
                playersBoosted.add(event.getPlayer());
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(boostMultiplier));
            }
        }
    }

    @EventHandler
    public void onEntityGlide(EntityToggleGlideEvent event){
        if (event.getEntityType() == EntityType.PLAYER && playersFlying.contains(event.getEntity())) event.setCancelled(true);
    }

    private boolean playerInRadius(Player player){
        if(player.getWorld().getName().equals(oneWayElytra.getFm().getConfig().getString("location.world"))){
            Location location = new Location(
                    Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world")),
                    oneWayElytra.getFm().getConfig().getInt("location.x"),
                    oneWayElytra.getFm().getConfig().getInt("location.y"),
                    oneWayElytra.getFm().getConfig().getInt("location.z"));
            return location.distance(player.getLocation()) <= radius;
        } else {
            return false;
        }
    }


}
