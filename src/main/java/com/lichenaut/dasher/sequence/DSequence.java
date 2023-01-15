package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.references.DPropertyReference;
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
            if (!property.equals("sequence")) {
                Object propertyValue;
                if (DPropertyReference.getIntProperties().contains(property)) {
                    propertyValue = root.getInt(property);
                    if ((property.equals("forward") || property.equals("backward") || property.equals("left") || property.equals("right")) && (int) propertyValue > 43) {
                        plugin.getLog().warning("Make sure sequence '" + sequenceName + "', property '" + property + "' does not exceed 43! Skipping sequence.");return;
                    } else if ((property.equals("up") || property.equals("down")) && (int) propertyValue > 60) {
                        plugin.getLog().warning("Make sure sequence '" + sequenceName + "', property '" + property + "' does not exceed 60! Skipping sequence.");return;}
                } else if (DPropertyReference.getStringProperties().contains(property)) {propertyValue = root.getString(property);
                } else if (DPropertyReference.getBooleanProperties().contains(property)) {propertyValue = root.getBoolean(property);
                } else {plugin.getLog().warning("Sequence '" + sequenceName + "' has unknown property '" + property + "'! Skipping sequence.");return;}

                if (propertyValue.toString().contains(" ") && DPropertyReference.getNoSpacesProperties().contains(property)) {
                    plugin.getLog().warning("Make sure sequence '" + sequenceName + "', property '" + property + "' does not have a space! Skipping sequence.");return;}
                if (propertyValue.toString().contains(",") && DPropertyReference.getNoCommaProperties().contains(property)) {
                    plugin.getLog().warning("Make sure sequence '" + sequenceName + "', property '" + property + "' does not have a comma! Skipping sequence.");return;}
                if (propertyValue.toString().contains(":") && DPropertyReference.getNoColonProperties().contains(property)) {
                    plugin.getLog().warning("Make sure sequence '" + sequenceName + "', property '" + property + "' does not have a colon! Skipping sequence.");return;}

                globalProperties.put(property, propertyValue);
            }
        }

        dashes = new ArrayList<>();
        for (String dash : root.getStringList("sequence")) {
            dashes.add(new DDash(plugin, this, dash));
            if (dashes.get(dashes.size()-1).isInvalid()) {return;}
        }
        invalidSequence = false;
    }

    public boolean isInvalid() {return invalidSequence;}
    public String getSequenceName() {return sequenceName;}
    public HashMap<String, Object> getGlobalProperties() {return globalProperties;}
    public ArrayList<DDash> getDashes() {return dashes;}
}
