package de.karimkeshta.oneWayElytra.configuration;

import de.karimkeshta.oneWayElytra.regions.ElytraRegion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Locale;

public class RegionConfig extends ConfigurationFile {

    public RegionConfig(String configPath, String configName) {
        super(configPath, configName);
    }

    /**
     * This functions adds a new region to the configuration file
     * @param region An ElytraRegion which should be added to config file
     */
    public void addRegion(ElytraRegion region) {
        getFileConfiguration().set("regions." + region.getName() + ".", );
    }

    /**
     * This function removes a region from the configuration file
     * @param region The region which should be removed
     */
    public void removeRegion(ElytraRegion region) {

    }

    /**
     * This function updates some parameters of a region
     * @param region The region which should be updated
     */
    public void updateRegion(ElytraRegion region) {

    }

    /**
     * Checking if a specific region exists at config
     * @param regionName name of the region
     * @return Returns if a region with this name exists at the configuration file
     */
    public boolean containsRegion(String regionName) {

        return false;
    }

    /**
     * Getting a region with a specific name
     * @param regionName Name of the region
     * @return Returns an ElytraRegion
     */
    public ElytraRegion getRegion(String regionName) {

        return null;
    }

    /**
     * Getting a list of all available regions
     * @return Return a list with all ElytraRegions
     */
    public ElytraRegion[] getRegions() {

        return null;
    }

    private int detectRegionIndex(String regionName) {
        List<?> regionList = getFileConfiguration().getList("regions");
        try {
            for (int i = 0; i < regionList.size(); i++) {
                ConfigurationSection regionSection = (ConfigurationSection) regionList.get(i);
                if(regionSection.getString("name").equalsIgnoreCase(regionName)) {
                    return i;
                }
            }
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("Could not load region list! \n Error: " + e.getMessage());
        }
        return -1;
    }

}
