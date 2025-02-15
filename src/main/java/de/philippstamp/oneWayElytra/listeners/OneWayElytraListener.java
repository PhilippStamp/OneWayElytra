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
import java.util.HashMap;
import java.util.List;

public class OneWayElytraListener implements Listener {

    private int radius;
    private int boostMultiplier;
    private HashMap<String, Location> positionsMap = new HashMap<>();
    private HashMap<String, Integer> boostMultiplierMap = new HashMap<>();
    private HashMap<String, String> lastLocation = new HashMap<>();
    private List<Location> positions = new ArrayList<>();
    private List<Player> playersFlying = new ArrayList<>();
    private List<Player> playersBoosted = new ArrayList<>();

    private OneWayElytra oneWayElytra;

    public OneWayElytraListener(OneWayElytra oneWayElytra) {
        this.oneWayElytra = oneWayElytra;
        this.radius = oneWayElytra.getFm().getConfig().getInt("radius");
        //old one-location system
        /*
        World world = Bukkit.getWorld(oneWayElytra.getFm().getConfig().getString("location.world"));
        if (world != null) {
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
        */
        Bukkit.getScheduler().runTaskTimer(oneWayElytra, () -> {
            //new multi-location system
            /*for (Location loc : this.positions) {
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
                if(WorldguardUtils.isWorldGuardInstalled() == true){
                    com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(loc);
                    LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                    for (ProtectedRegion region : set) {
                        if(set.testState(null, WorldguardFlags.ONEWAYELYTRA)){
                            if (player.getGameMode() == GameMode.SURVIVAL) {
                                if(!playersFlying.contains(player)){
                                    player.setAllowFlight(true);
                                }
                            }
                        }
                    }
                }
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (playersFlying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
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

    @EventHandler
    public void onToogleFlight(PlayerToggleFlightEvent event){
        if(event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            /*if (playerInRadius(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().setGliding(true);
                playersFlying.add(event.getPlayer());
                ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage")));
            } else if (startCheckingTask(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().setGliding(true);
                playersFlying.add(event.getPlayer());
                ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage")));
            }*/
            if(isAllowedToFly(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().setGliding(true);
                event.getPlayer().setAllowFlight(false);
                playersFlying.add(event.getPlayer());
                ActionBar.send(event.getPlayer(), oneWayElytra.getTools().replaceVariables(oneWayElytra.getFm().getMessages().getString("boostMessage")));
            }
        }


    }

    /**
     *
     * @param player The player which should be checked
     *
     */
    private boolean isAllowedToFly(Player player){
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        for (ProtectedRegion region : set) {
            if (set.testState(null, WorldguardFlags.ONEWAYELYTRA)) {
                return true;
            }
        }
        return false;
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

    public void loadPositions() {
        /*Set<String> locationKeys = oneWayElytra.getFm().getConfig().getKeys(false);
        for (String key : locationKeys) {
            this.positionsMap.put(key, new Location(
                    oneWayElytra.getServer().getWorld(oneWayElytra.getFm().getConfig().getString("locations." + key + ".world")),
                    oneWayElytra.getFm().getConfig().getDouble("locations." + key + ".x"),
                    oneWayElytra.getFm().getConfig().getDouble("locations." + key + ".y"),
                    oneWayElytra.getFm().getConfig().getDouble("locations." + key + ".z")
            ));
            this.boostMultiplierMap.put(key, oneWayElytra.getFm().getConfig().getInt("locations." + key + ".boostMultiplier"));
        }*/


        List<String> positions = oneWayElytra.getFm().getConfig().getStringList("positions");
        for (String locString : positions) {
            String[] parts = locString.split(":");
            if (parts.length == 4) {
                try {
                    String worldName = parts[0];
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    if (Bukkit.getWorld(worldName) != null) {
                        this.positions.add(new Location(Bukkit.getWorld(worldName), x, y, z));
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