package com.lichenaut.dasher;

import com.lichenaut.dasher.commands.DasherCommand;
import com.lichenaut.dasher.commands.DasherTabCompleter;
import com.lichenaut.dasher.sequence.DSequence;
import com.lichenaut.dasher.startup.DSequencesBuilder;
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
    private HashMap<Integer, Double> yVelocities;
    private HashMap<Integer, Double> xzVelocities;
    private HashMap<String, DSequence> sequences;

    @Override
    public void onEnable() {
        final Dasher plugin = this;
        log = getLogger();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Configuration config = getConfig();

        //int pluginId = ;
        //noinspection ALL
        //Metrics metrics = new Metrics(plugin, pluginId);

        if (config.getBoolean("disable-plugin")) {
            log.info("Plugin disabled in config.yml.");
        } else {
            new DUpdateChecker(this, plugin).getVersion(version -> {if (!this.getDescription().getVersion().equals(version)) {getLog().info("Update available.");}});

            dataFolderPath = getDataFolder().getPath();
            new DDirectoryMaker(plugin).makeDir(dataFolderPath);
            new DResourceCreator(plugin).createResource("README.txt");

            yVelocities = DVelocitiesBuilder.getYVelocities();
            xzVelocities = DVelocitiesBuilder.getXZVelocities();

            sequences = new DSequencesBuilder(plugin).getSequences();

            Objects.requireNonNull(getCommand("dasher")).setExecutor(new DasherCommand(plugin));//reload command?
            Objects.requireNonNull(getCommand("dasher")).setTabCompleter(new DasherTabCompleter());


        }
    }

    public Logger getLog() {return log;}
    public String getDataFolderPath() {return dataFolderPath;}
    public HashMap<Integer, Double> getVelocities(char axis) {if (axis == 'y') {return yVelocities;} else {return xzVelocities;}}
    public HashMap<String, DSequence> getSequences() {return sequences;}
}