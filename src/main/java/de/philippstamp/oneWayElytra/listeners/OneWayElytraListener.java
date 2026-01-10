package de.philippstamp.oneWayElytra.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import de.philippstamp.oneWayElytra.OneWayElytra;
import de.philippstamp.oneWayElytra.utils.ActionBar;
import de.philippstamp.oneWayElytra.utils.WorldguardFlags;
import de.philippstamp.oneWayElytra.utils.WorldguardUtils;
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

    private List<Location> positions = new ArrayList<>();

    private OneWayElytra oneWayElytra;

    public OneWayElytraListener(OneWayElytra oneWayElytra){
        this.oneWayElytra = oneWayElytra;
        this.radius = oneWayElytra.getFileManager().getConfig().getInt("radius");
        Bukkit.getScheduler().runTaskTimer(oneWayElytra, () -> {
            /*
            for (Location loc : this.positions) {
                World playerWorld = loc.getWorld();
                Bukkit.getWorld(playerWorld.getName()).getPlayers().forEach(player -> {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.setAllowFlight(startCheckingTask(player));
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
            }
             */

            for (Player player : Bukkit.getOnlinePlayers()) {
                if(WorldguardUtils.isWorldGuardInstalled()){

                    com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(loc);
                    LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

                    boolean wgAllowed = set.testState(localPlayer, WorldguardFlags.ONEWAYELYTRA);
                    boolean inRadiusArea = oneWayElytra.getRadiusManager().isInAnyArea(player.getLocation());

                    // Hier prüfen wir entweder WG-Flag oder Radius
                    if (wgAllowed || inRadiusArea) {
                        if (player.getGameMode() == GameMode.SURVIVAL && !playersFlying.contains(player)) {
                            player.setAllowFlight(true);
                        }
                    }
                }


                // Rest deines Codes: Landen erkennen, Boost entfernen
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (playersFlying.contains(player)
                            && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
                        player.setAllowFlight(false);
                        player.setGliding(false);
                        playersBoosted.remove(player);

                        Bukkit.getScheduler().runTaskLater(oneWayElytra, () -> {
                            playersFlying.remove(player);
                        }, 5);
                    }
                }
            }
        }, 0, 3);
    }

    /*
    @EventHandler
    public void onToogleFlight(PlayerToggleFlightEvent event){
        if(event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            event.getPlayer().sendMessage("TEST");
            if(isAllowedToFly(event.getPlayer())){
                event.getPlayer().sendMessage("ALLOWED");
            } else {
                event.getPlayer().sendMessage("NOT ALLOWED");
            }
        }
    }
     */

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event){
        Player player = event.getPlayer();

        // Nur im Survival-Modus prüfen
        if(player.getGameMode() != GameMode.SURVIVAL) return;

        // Prüfen, ob der Spieler gerade fliegen darf
        if(isAllowedToFly(player)){
            // Spieler darf fliegen → alles okay
            event.setCancelled(true);
            event.getPlayer().setGliding(true);
            playersFlying.add(event.getPlayer());
            ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFileManager().getMessages().getString("boostMessage")));
            player.sendMessage("§aDu darfst fliegen!");
        }
    }

    public boolean isAllowedToFly(Player player) {
        boolean wgAllowed = false;
        if (WorldguardUtils.isWorldGuardInstalled()) {
            com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(loc);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

            wgAllowed = set.testState(localPlayer, WorldguardFlags.ONEWAYELYTRA);
        }

        boolean inRadiusArea = oneWayElytra.getRadiusManager().isInAnyArea(player.getLocation());

        return wgAllowed || inRadiusArea;
    }


/*
    private boolean isAllowedToFly(Player player){
        boolean worldguardAllowed = false;
        boolean locationAllowed = false;

        // Prüfen, ob WorldGuard installiert ist und Flag erlaubt
        if(WorldguardUtils.isWorldGuardInstalled()){
            com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(loc);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

            // Prüfen ob Flag in irgendeiner Region aktiv ist
            if(set.testState(null, WorldguardFlags.ONEWAYELYTRA)){
                worldguardAllowed = true;
            }
        }



        // Rückgabe: WorldGuard erlaubt oder Radius erlaubt
        return false;
    }

 */

    /*
    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        // Nur Survival / Adventure
        if (player.getGameMode() != GameMode.SURVIVAL &&
                player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }

        // Event abbrechen → kein echtes Fliegen
        event.setCancelled(true);
        player.setFlying(false);
        player.setAllowFlight(false);

        // === HIER ist der Doublejump ===
        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(1));
    }
    */

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
            this.boostMultiplier = oneWayElytra.getFileManager().getConfig().getInt("boostMultiplier");
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

    void loadPositions() {
        List<String> positions = oneWayElytra.getFileManager().getConfig().getStringList("locations");
        for (String locString : positions) {
            String[] parts = locString.split(":");
            if (parts.length == 4) {
                try {
                    String worldName = parts[0];
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    if (Bukkit.getWorld(worldName) != null) {
                        Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
                        this.positions.add(loc);

                        // Ausgabe in Konsole
                        Bukkit.getConsoleSender().sendMessage("[OneWayElytra] Loaded position: " +
                                worldName + " " + x + " " + y + " " + z);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }

    }

    private boolean startCheckingTask(Player player) {
        for (Location loc : this.positions) {
            if (player.getWorld().equals(loc.getWorld())) {
                return loc.distance(player.getLocation()) <= radius;
            }
        }
        return false;
    }



}