package de.karimkeshta.oneWayElytra.configuration;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Some base structure for Minecraft Plugin configurations
 * @author karimkeshta
 * @version 1.0
 */
public class ConfigurationFile {

    private final String configPath;
    private final String configName;

    private File file;
    private FileConfiguration fileConfiguration;

    private HashMap<String, Object> defaultValues;

    public ConfigurationFile(String configPath, String configName) {
        this.configPath = configPath;
        this.configName = configName;
    }

    //<editor-fold desc="Load and Save Functions">
    public void load() {
        this.file = new File(configPath + File.separator + configName);
        if (!file.exists()) {
            try {
                this.file.mkdir();
                this.file.createNewFile();
            } catch (IOException | SecurityException e) {
                Bukkit.getServer().getLogger().warning("Could not create file " + configPath + File.separator + configName);
                Bukkit.getServer().getLogger().warning("Error: " + e.getMessage());
            }
        }
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().warning("Could not save configuration file. \n Error: " + e.getMessage());
        }
    }
    //</editor-fold>

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
