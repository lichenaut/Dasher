package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.startup.DSequencesBuilder;
import com.lichenaut.dasher.util.DPropertiesBuilder;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DSequence {

    private final String sequenceName;
    private boolean invalidSequence;
    private HashMap<String, Object> globalProperties;
    private ArrayList<DDash> dashes;

    public DSequence(Dasher plugin, DSequencesBuilder builder, String sequenceName) {this.sequenceName = sequenceName;
        //'global' means 'the whole sequence', 'local' means 'only that dash'
        //'sequence', 'permission', 'toggle-permission', and 'loops' are the only strictly global properties
        //the others can exist in either space, local versions override their global ones
        //a sequence must have a name, 'trigger' and 'sequence' fields
        invalidSequence = true;
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("sequences").getConfigurationSection(sequenceName);

        HashSet<String> properties = new HashSet<>(root.getKeys(false));//names of properties
        String permission = "default";
        if (properties.contains("permission")) {permission = root.getString("permission");}
        if (!builder.getSequencePermissions().add(permission)) {
            plugin.getLog().warning("Sequence '" + sequenceName + "' has the same permission as another sequence! Skipping sequence.");return;}
        if (!properties.contains("sequence") || !properties.contains("trigger")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' has 'sequence' and 'trigger' fields! Skipping sequence.");return;}
        if (properties.contains("forward") && properties.contains("backward")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'forward' and 'backward' fields! Skipping sequence.");return;}
        if (properties.contains("up") && properties.contains("down")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'up' and 'down' fields! Skipping sequence.");return;}
        if (properties.contains("left") && properties.contains("right")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'left' and 'right' fields! Skipping sequence.");return;}
        if (properties.contains("tp-top") && properties.contains("tp-floor")) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName + "' does not have both 'tp-top' and 'tp-floor' fields! Skipping sequence.");return;}
        if ((properties.contains("tp-top") || properties.contains("tp-floor")) && (properties.contains("forward") || properties.contains("backward") || properties.contains("up") ||
                properties.contains("down") || properties.contains("left") || properties.contains("right"))) {
            plugin.getLog().warning("Make sure sequence '" + sequenceName +
                    "' does not have either 'tp-top' or 'tp-floor' and one of the following fields: 'forward', 'backward', 'up', 'down', 'left', 'right'! Skipping sequence.");return;
        }

        globalProperties = new HashMap<>();//names of properties mapped to their values
        for (String property : properties) {
            if (!property.equals("permission")) {
                if (DPropertiesBuilder.getStringProperties().contains(property)) {globalProperties.put(property, root.getString(property));
                } else if (DPropertiesBuilder.getIntProperties().contains(property)) {globalProperties.put(property, root.getInt(property));
                } else if (DPropertiesBuilder.getBooleanProperties().contains(property)) {globalProperties.put(property, root.getBoolean(property));
                } else {plugin.getLog().warning("Sequence '" + sequenceName + "' has unknown property '" + property + "'! Skipping sequence.");return;}
            }
        }

        dashes = new ArrayList<>();
        for (String dash : new HashSet<>(root.getStringList("sequence"))) {
            dashes.add(new DDash(plugin, this, dash));
            int current = dashes.size()-1;
            if (dashes.get(current).isInvalid()) {return;}
        }
        invalidSequence = false;
    }

    public boolean isInvalid() {return invalidSequence;}
    public String getSequenceName() {return sequenceName;}
    public HashMap<String, Object> getGlobalProperties() {return globalProperties;}
    public ArrayList<DDash> getDashes() {return dashes;}
}
