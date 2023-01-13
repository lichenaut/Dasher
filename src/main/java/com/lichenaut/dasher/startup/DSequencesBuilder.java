package com.lichenaut.dasher.startup;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.sequence.DSequence;
import com.lichenaut.dasher.util.DPropertiesBuilder;

import java.util.*;

public class DSequencesBuilder {

    private final Dasher plugin;
    private HashSet<String> knownProperties;
    private HashSet<String> noSpacesProperties;
    private HashSet<String> noColonProperties;

    public DSequencesBuilder(Dasher plugin) {this.plugin = plugin;}

    public HashMap<String, DSequence> getSequences() {
        knownProperties = DPropertiesBuilder.getKnownProperties();
        noSpacesProperties = DPropertiesBuilder.getNoSpacesProperties();
        noColonProperties = DPropertiesBuilder.getNoColonProperties();

        HashMap<String, DSequence> sequences = new HashMap<>();
        for (String sequenceName : plugin.getConfig().getConfigurationSection("sequences").getKeys(false)) {
            sequences.put(sequenceName, new DSequence(plugin, this, sequenceName));
            if (sequences.get(sequenceName).isInvalid()) {sequences.remove(sequenceName);}
        }
        return sequences;
    }

    public HashSet<String> getKnownProperties() {return knownProperties;}
    public HashSet<String> getNoSpacesProperties() {return noSpacesProperties;}
    public HashSet<String> getNoColonProperties() {return noColonProperties;}
}
