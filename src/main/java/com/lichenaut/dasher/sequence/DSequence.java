package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.startup.DSequencesBuilder;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DSequence {

    private final Dasher plugin;
    private final DSequencesBuilder builder;
    private final String sequenceName;
    private boolean invalidSequence;
    private HashMap<String, HashSet<String>> globalProperties;
    private HashSet<String> strings;

    public DSequence(Dasher plugin, DSequencesBuilder builder, String sequenceName) {this.plugin = plugin;this.builder = builder;this.sequenceName = sequenceName;
        //'global' means 'the whole sequence', 'local' means 'only that dash'
        //'sequence', 'toggle-permission', and 'loops' are the only strictly global properties. the others can exist in either space, local versions override their global ones
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("sequences").getConfigurationSection(sequenceName);
        invalidSequence = true;

        HashSet<String> properties = new HashSet<>(root.getKeys(false));//names of properties
        if (!properties.contains("sequence") || !properties.contains("trigger")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' has valid 'sequence' and 'trigger' fields! Skipping sequence.");return;}
        if (properties.contains("tp-top") && properties.contains("tp-floor")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'tp-top' and 'tp-floor' fields! Skipping sequence.");return;}
        if (properties.contains("forward") && properties.contains("backward")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'forward' and 'backward' fields! Skipping sequence.");return;}
        if (properties.contains("up") && properties.contains("down")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'up' and 'down' fields! Skipping sequence.");return;}
        if (properties.contains("left") && properties.contains("right")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'left' and 'right' fields! Skipping sequence.");return;}

        globalProperties = new HashMap<>();//names of properties mapped to their string sets

        for (String property : properties) {
            if (!builder.getKnownProperties().contains(property)) {
                plugin.getLog().warning("Sequence '" + sequenceName + "' has unknown property '" + property + "'! Skipping property.");continue;}

            HashSet<String> propertyStrings = new HashSet<>(root.getStringList(property));//string set of a property
            strings = new HashSet<>();//refined version
            if (!propertyStrings.isEmpty()) {
                switch (getRefinedStrings(property, propertyStrings)) {
                    case 1: continue;
                    case 2: return;
                }
                strings.removeIf(string -> !string.contains(":"));
            } else {plugin.getLog().warning("Sequence '" + sequenceName + "' has null property '" + property + "'! Skipping property.");continue;}
            globalProperties.put(property, new HashSet<>(strings));
        }

        ArrayList<DDash> dashes = new ArrayList<>();
        for (String dash : new HashSet<>(root.getStringList("sequence"))) {dashes.add(new DDash(plugin, this, dash));}
        invalidSequence = false;
    }

    public byte getRefinedStrings(String property, HashSet<String> propertyStrings) {//'0' = all good, '1' = skip property, '2' = skip sequence
        for (String string : propertyStrings) {
            if (string.contains(" ") && builder.getNoSpacesProperties().contains(property)) {
                plugin.getLog().warning("Sequence '" + sequenceName + "' has property '" + property +
                        "' that has at least one space (it should not have any)! Skipping property.");return 1;}
            if (string.contains(":") && builder.getNoColonProperties().contains(property)) {
                plugin.getLog().warning("Sequence '" + sequenceName + "' has permission-related property '" + property +
                        "' that has at least one colon (it should not have any)! Skipping property.");return 1;}

            String permission = "default";
            String restOfString = string;
            if (string.split(" ")[0].contains(":")) {
                String[] splitString = string.split(":", 2);
                permission = splitString[0];
                restOfString = splitString[1];
            }
            if (!strings.add(permission)) {
                if (permission.equals("default")) {
                    plugin.getLog().warning("Sequence '" + sequenceName + "' has property '" + property +
                            "' that has two fields with no permission (duplicate permission)! Skipping sequence.");
                } else {
                    plugin.getLog().warning("Sequence '" + sequenceName + "' has property '" + property + "' that has duplicate of permission '" + permission +
                            "'! Skipping sequence.");
                }
                return 2;
            } else {strings.add(permission + ":" + restOfString);}
        }
        return 0;
    }

    public boolean isInvalid() {return invalidSequence;}
    public HashMap<String, HashSet<String>> getGlobalProperties() {return globalProperties;}
}
