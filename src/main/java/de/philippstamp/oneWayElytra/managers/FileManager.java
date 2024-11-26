/*
 * Created by Philipp A. Stamp
 * Date: 08. Jul 2019
 */
package de.philippstamp.oneWayElytra.managers;

import de.philippstamp.oneWayElytra.OneWayElytra;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.InputStream;

public class FileManager {
    File cfgFile = new File(OneWayElytra.getInstance().getDataFolder(), "config.yml");
    FileConfiguration cfg;

    //File msgFile = new File(OneWayElytra.getInstance().getDataFolder(), this.cfg.getString("language") + ".yml");
    File msgFile = new File(OneWayElytra.getInstance().getDataFolder(), "language.yml");
    FileConfiguration msg;

    public void firstRun() throws Exception {
        if (!this.cfgFile.exists()) {
            this.cfgFile.getParentFile().mkdirs();
            copy(OneWayElytra.getInstance().getResource("config.yml"), this.cfgFile);
        }
        if (!this.msgFile.exists()) {
            this.msgFile.getParentFile().mkdirs();
            //copy(OneWayElytra.getInstance().getResource(getConfig().getString("language") + ".yml"), this.msgFile);
            copy(OneWayElytra.getInstance().getResource("language.yml"), this.msgFile);
        }
    }

    public FileConfiguration getConfig()
    {
        return this.cfg;
    }

    public FileConfiguration getMessages() {
        return this.msg;
    }



    private void copy(InputStream in, File file) {
        try {
            java.io.OutputStream out = new java.io.FileOutputStream(file);
            byte[] buf = new byte['Ѐ'];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadYamls() {
        try {
            this.cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.cfgFile);
            this.msg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.msgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveYamls() {
        try {
            this.cfg.save(this.cfgFile);
            this.msg.save(this.msgFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
