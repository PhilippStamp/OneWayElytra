package de.philippstamp.oneWayElytra.utils;

import org.bukkit.Location;


/**
 * This class is for bundling all information about a region.
 * @author Karim Keshta
 * @version 1.0
 */
public class ElytraRegion {

    private String name;
    private Location location;

    private int radiusHorizontal;
    private int radiusVertical;

    private float boostMultiplier;


    /**
     *
     * @param name The name of the region
     * @param location The location of the region
     * @param radiusHorizontal The horizontal radius of the region
     * @param radiusVertical The vertical radius of the region
     * @param boostMultiplier The boostMultiplier for this region
     */
    public ElytraRegion(String name, Location location, int radiusHorizontal, int radiusVertical, float boostMultiplier) {
        this.name = name;
        this.location = location;
        this.radiusHorizontal = radiusHorizontal;
        this.radiusVertical = radiusVertical;
        this.boostMultiplier = boostMultiplier;
    }

    /**
     *
     * @return Returns the Name of the class
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Returns the center location of the region
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return Returns the boostMultiplier of the region
     */
    public float getBoostMultiplier() {
        return boostMultiplier;
    }

    /**
     *
     * @return Returns the horizontal radius of the region
     */
    public int getRadiusHorizontal() {
        return radiusHorizontal;
    }

    /**
     *
     * @return Returns the vertical radius of the region
     */
    public int getRadiusVertical() {
        return radiusVertical;
    }

    /**
     *
     * @param playerLocation The Locations of the player to be checked
     * @return Returns if the player is inside the region
     */
    public boolean isPlayerInRegion(Location playerLocation){
        //return playerLocation.distance(location) <= radiusHorizontal;
        if(playerLocation.distance(location) <= radiusHorizontal && playerLocation.getY() <= location.getY() + radiusVertical && playerLocation.getY() >= location.getY() - radiusVertical){
            return true;
        }
        return false;
    }



}

