package de.philippstamp.oneWayElytra;

import de.philippstamp.oneWayElytra.commands.OneWayElytraCMD;
import de.philippstamp.oneWayElytra.listeners.OneWayElytraListener;
import de.philippstamp.oneWayElytra.managers.FileManager;
import de.philippstamp.oneWayElytra.utils.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class OneWayElytra extends JavaPlugin {

    private static OneWayElytra Instance;

    public String prefix;
    private FileManager fileManager;
    public Tools tools;
    PluginManager pluginManager = Bukkit.getPluginManager();

    public ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();

    private OneWayElytraListener oneWayElytraListener;




    @Override
    public void onEnable() {
        Instance = this;
        loadFm();
        this.tools = new Tools(this);
        prefix = this.getTools().replaceVariables(getFm().getMessages().getString("prefix"));
        ccs.sendMessage(prefix + "Enabling...");

        registerListener();
        ccs.sendMessage(prefix + "Listeners successfully registered!");

        registerCommands();
        ccs.sendMessage(prefix + "Commands successfully registered!");

        //getFm().getConfig().set("positions", Arrays.asList("farmworld_nether,0,50,0", "farmworld,0,0,0"));
        getFm().saveYamls();
        oneWayElytraListener.loadPositions();

        ccs.sendMessage(prefix + "*~*~*~*~*~*~*~* <<OneWayElytra>> *~*~*~*~*~*~*~*");
        ccs.sendMessage(prefix + "Plugin successfully loaded!");
        ccs.sendMessage(prefix + "Version: " + getDescription().getVersion());
        ccs.sendMessage(prefix + "Author: " + getDescription().getAuthors());
        ccs.sendMessage(prefix + "*~*~*~*~*~*~*~* <<OneWayElytra>> *~*~*~*~*~*~*~*");

    }

    @Override
    public void onDisable() {
        ccs.sendMessage(prefix + "*~*~*~*~*~*~*~* <<OneWayElytra>> *~*~*~*~*~*~*~*");
        ccs.sendMessage(prefix + "Plugin successfully disabled!");
        ccs.sendMessage(prefix + "*~*~*~*~*~*~*~* <<OneWayElytra>> *~*~*~*~*~*~*~*");
    }

    public static OneWayElytra getInstance() {return Instance;}

    private void loadFm() {
        fileManager = new FileManager();
        try {
            fileManager.firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileManager.loadYamls();
    }

    public Tools getTools(){
        return  tools;
    }

    public FileManager getFm() {
        return fileManager;
    }

    @EventHandler
    public void registerListener() {
        oneWayElytraListener = new OneWayElytraListener(this);
        pluginManager.registerEvents(oneWayElytraListener, this);
    }

    @EventHandler
    public void registerCommands() {
        //Objects.requireNonNull(getCommand("onewayelytra")).setExecutor(new OneWayElytraCMD(Instance));
        getCommand("onewayelytra").setExecutor(new OneWayElytraCMD(Instance));
    }
}
