package de.philippstamp.oneWayElytra.listeners;

import de.philippstamp.oneWayElytra.OneWayElytra;
import de.philippstamp.oneWayElytra.utils.ActionBar;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.*;

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
        String worldName;
        World world = Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world"));
        if(world != null) {
            Bukkit.getScheduler().runTaskTimer(oneWayElytra, () -> {
                Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world")).getPlayers().forEach(player -> {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.setAllowFlight(playerInRadius(player));
                        if (playersFlying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
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

    }

    @EventHandler
    public void onToogleFlight(PlayerToggleFlightEvent event){
        if(event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            if (playerInRadius(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().setGliding(true);
                playersFlying.add(event.getPlayer());
                ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage")));
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


    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        World fromWorld = event.getFrom().getWorld();
        World toWorld = event.getTo().getWorld();

        if (!fromWorld.equals(toWorld)) {
            if(playersFlying.contains(event.getPlayer())){
                event.getPlayer().setGliding(false);
                playersFlying.remove(event.getPlayer());
            }
        }
    }

    private boolean playerInRadius(Player player){
        String worldName;
        World world = Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world"));
        if(world != null){
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

        return false;
    }


}