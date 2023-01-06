package com.lichenaut.dasher;

import com.lichenaut.dasher.commands.DasherCommand;
import com.lichenaut.dasher.commands.DasherTabCompleter;
import com.lichenaut.dasher.util.DDirectoryMaker;
import com.lichenaut.dasher.util.DResourceCreator;
import com.lichenaut.dasher.util.DUpdateChecker;
import com.lichenaut.dasher.util.DVelocitiesBuilder;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public final class Dasher extends JavaPlugin {

    private Logger log;
    private String dataFolderPath;

    HashMap<Integer, Double> yVelocities;
    HashMap<Integer, Double> xzVelocities;

    @Override
    public void onEnable() {
        final Dasher plugin = this;
        log = getLogger();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Configuration config = getConfig();

        if (config.getBoolean("disable-plugin")) {
            log.info("Plugin disabled in config.yml.");
        } else {
            new DUpdateChecker(this, plugin).getVersion(version -> {
                if (this.getDescription().getVersion().equals(version)) {getLog().info("No new update available.");
                } else {getLog().info("Update available.");}
            });

            dataFolderPath = getDataFolder().getPath();

            DDirectoryMaker dirMaker = new DDirectoryMaker(plugin);
            dirMaker.makeDir(dataFolderPath);

            DResourceCreator resourceCreator = new DResourceCreator(plugin);
            resourceCreator.createResource("README.txt");
            resourceCreator.createResource("cache.txt");

            yVelocities = DVelocitiesBuilder.getYVelocities();
            xzVelocities = DVelocitiesBuilder.getXZVelocities();

            Objects.requireNonNull(getCommand("dasher")).setExecutor(new DasherCommand(plugin));
            Objects.requireNonNull(getCommand("dasher")).setTabCompleter(new DasherTabCompleter());

            //int pluginId = ;
            //noinspection ALL
            //Metrics metrics = new Metrics(plugin, pluginId);


        }
    }

    public Logger getLog() {return log;}
    public String getPluginFolderPath() {return dataFolderPath;}

    public HashMap<Integer, Double> getVelocities(char direction) {
        if (direction == 'y') {return yVelocities;
        } else {return xzVelocities;}
    }
}
