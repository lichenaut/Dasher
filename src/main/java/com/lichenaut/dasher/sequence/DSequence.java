package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DSequence {

    private boolean invalidSequence;

    public DSequence(Dasher plugin, String sequenceName) {
        //'global' means 'the whole sequence', 'local' means 'only that dash'. left unmarked, if that property is in a local setting, it overrides the global version.
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("sequences").getConfigurationSection(sequenceName);
        invalidSequence = true;

        HashSet<String> properties = new HashSet<>(root.getKeys(false));
        if (!properties.contains("sequence") || !properties.contains("trigger")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' has both 'sequence' and 'trigger' fields! Skipping sequence.");return;}
        if (properties.contains("tp-top") && properties.contains("tp-floor")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'tp-top' and 'tp-floor' fields! Skipping sequence.");return;}
        if (properties.contains("forward") && properties.contains("backward")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'forward' and 'backward' fields! Skipping sequence.");return;}
        if (properties.contains("up") && properties.contains("down")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'up' and 'down' fields! Skipping sequence.");return;}
        if (properties.contains("left") && properties.contains("right")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'left' and 'right' fields! Skipping sequence.");return;}

        HashMap<String, HashSet<String>> globalProperties = new HashMap<>();//refined version

        HashSet<String> knownProperties = new HashSet<>(25);
        knownProperties.add("toggle-permission");
        knownProperties.add("cooldown");
        knownProperties.add("permission");
        knownProperties.add("trigger");
        knownProperties.add("item-name");
        knownProperties.add("item-material");
        knownProperties.add("sound");
        knownProperties.add("particle");
        knownProperties.add("effect");
        knownProperties.add("look-affects-height");
        knownProperties.add("loops");
        knownProperties.add("keep-momentum");
        knownProperties.add("tp-top");
        knownProperties.add("tp-floor");
        knownProperties.add("forward");
        knownProperties.add("backward");
        knownProperties.add("up");
        knownProperties.add("down");
        knownProperties.add("left");
        knownProperties.add("right");
        knownProperties.add("damage");
        knownProperties.add("experience");
        knownProperties.add("invulnerable");
        knownProperties.add("fall-damage");
        knownProperties.add("buffer");
        for (String property : properties) {
            if (!knownProperties.contains(property)) {
                plugin.getLog().warning("Sequence '" + sequenceName + "' has an unknown property '" + property + "'! Skipping property.");continue;}

            HashSet<String> strings;
            if (root.getStringList(property) != null) {
                strings = new HashSet<>();
                for (String string : root.getStringList(property)) {
                    String permission = "default";
                    String restOfString = string;
                    if (string.split(" ")[0].contains(":")) {
                        String[] splitString = string.split(":");
                        permission = splitString[0];
                        restOfString = splitString[1];
                    }
                    if (!strings.add(permission)) {
                        if (permission.equals("default")) {
                            plugin.getLog().warning("Sequence '" + sequenceName + "' has a property '" + property +
                                    "' that has two fields with no permission (duplicate permission)! Skipping sequence.");
                        } else {
                         plugin.getLog().warning("Sequence '" + sequenceName + "' has a property '" + property + "' that has duplicate permission '" + permission +
                                 "'! Skipping sequence.");
                        }
                        return;
                    } else {strings.add(permission + ":" + restOfString);}
                }
                strings.removeIf(string -> !string.contains(":"));
            } else {plugin.getLog().warning("Sequence '" + sequenceName + "' has null property '" + property + "'! Skipping sequence.");return;}

            globalProperties.put(property, strings);break;
        }

        ArrayList<DDash> dashes = new ArrayList<>();
        for (String dash : root.getStringList("sequence")) {dashes.add(new DDash(plugin, globalProperties, dash));}//add knownProperties to Dash
        invalidSequence = false;
    }

    HashSet<String> togglePermissions;

    public void setGlobalProperties(HashMap<String, Object> globalProperties) {

    }

    public boolean isInvalid() {return invalidSequence;}
}
