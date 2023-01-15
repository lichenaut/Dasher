package com.lichenaut.dasher.sequence;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.sequence.DSequence;

import java.util.*;

public class DSequencesBuilder {

    private final Dasher plugin;
    private HashSet<String> sequencePermissions;

    public DSequencesBuilder(Dasher plugin) {this.plugin = plugin;}

    public HashMap<String, DSequence> getSequences() {
        sequencePermissions = new HashSet<>();
        HashMap<String, DSequence> sequences = new HashMap<>();
        for (String sequenceName : plugin.getConfig().getConfigurationSection("sequences").getKeys(false)) {
            sequences.put(sequenceName, new DSequence(plugin, this, sequenceName));
            if (sequences.get(sequenceName).isInvalid()) {sequences.remove(sequenceName);}
        }
        return sequences;
    }

    public HashSet<String> getSequencePermissions() {return sequencePermissions;}
}
