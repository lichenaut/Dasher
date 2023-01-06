package com.lichenaut.dasher.util;

import com.lichenaut.dasher.Dasher;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class DUpdateChecker {

    private final JavaPlugin plugin;
    private final Dasher jPlugin;

    public DUpdateChecker(JavaPlugin plugin, Dasher jPlugin) {this.plugin = plugin;this.jPlugin = jPlugin;}

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 107149).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {consumer.accept(scanner.next());}
            } catch (IOException e) {
                jPlugin.getLog().warning("Unable to check for updates!");
                e.printStackTrace();
            }
        });
    }
}