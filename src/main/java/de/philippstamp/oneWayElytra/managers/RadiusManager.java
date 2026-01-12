package de.philippstamp.oneWayElytra.managers;

import de.philippstamp.oneWayElytra.utils.RadiusArea;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RadiusManager {

    private final List<RadiusArea> areas = new ArrayList<>();

    public RadiusManager(YamlDocument config) {
        loadAreas(config);
    }

    public void loadAreas(YamlDocument config) {
        areas.clear();
        if (!config.contains("locations")) return;

        for (String key : config.getSection("locations").getRoutesAsStrings(false)) {

            String path = "locations." + key;

            String world = config.getString(path + ".world");
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");
            double radius = config.getDouble(path + ".radius");

            if (world == null || radius <= 0) continue;

            areas.add(new RadiusArea(key, world, x, y, z, radius));
        }
    }

    public boolean isInAnyArea(Location location) {
        for (RadiusArea area : areas) {
            if (area.isInside(location)) {
                return true;
            }
        }
        return false;
    }
}
