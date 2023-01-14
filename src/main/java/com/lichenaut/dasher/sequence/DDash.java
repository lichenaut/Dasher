package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.util.DPropertiesBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class DDash {

    private boolean invalidDash;
    private HashMap<String, Object> localProperties;

    public DDash(Dasher plugin, DSequence sequence, String dash) {
        invalidDash = true;

        if (dash.contains("sequence:") || dash.contains("permission:") || dash.contains("toggle-permission:") || dash.contains("loops:")) {
            plugin.getLog().warning("Make sure sequence '" + sequence.getSequenceName() + "', dash '" + dash +
                    "' does not have any of the following fields: 'sequence', 'permission', 'toggle-permission', 'loops'! Skipping sequence.");return;
        }

        HashSet<String> properties = Arrays.stream(dash.split(" ")).collect(Collectors.toCollection(HashSet::new));
        for (String property : properties) {
            int parts = property.split(":").length;
            if (parts < 2) {
                plugin.getLog().warning("Make sure sequence '" + sequence.getSequenceName() + "', dash '" + dash +
                        "' does not have an empty field (missing colon detected)! Skipping sequence.");return;
            } else if (parts > 2) {
                if (!(parts==property.split(",").length+1)) {
                    plugin.getLog().warning("Make sure sequence '" + sequence.getSequenceName() + "', dash '" + dash +
                            "' does not have a permission; make sure extra information is separated by a comma (extra colon detected)! Skipping sequence.");return;
                }
            }
        }

        localProperties = sequence.getGlobalProperties();
        for (String property : properties) {
            String[] propertyFields = property.split(":", 2);
            if (DPropertiesBuilder.getKnownProperties().contains(propertyFields[0])) {localProperties.put(propertyFields[0], propertyFields[1]);
            } else {plugin.getLog().warning("Sequence '" + sequence.getSequenceName() + "', dash '" + dash + "' has unknown property '" + propertyFields[0] +
                    "'! Skipping sequence.");return;
            }
        }
        invalidDash = false;
    }

    public boolean isInvalid() {return invalidDash;}
    public HashMap<String, Object> getLocalProperties() {return localProperties;}
}