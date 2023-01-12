package com.lichenaut.dasher.startup;

import com.lichenaut.dasher.Dasher;
import com.lichenaut.dasher.sequence.DSequence;

import java.util.*;

public class DDasherRecorder {

    private final Dasher plugin;

    public DDasherRecorder(Dasher plugin) {this.plugin = plugin;}

    public HashMap<String, DSequence> getConfigSequences() {
        HashMap<String, DSequence> sequences = new HashMap<>();
        for (String sequenceName : plugin.getConfig().getConfigurationSection("sequences").getKeys(false)) {
            sequences.put(sequenceName, new DSequence(plugin, sequenceName));
            if (sequences.get(sequenceName).isInvalid()) {sequences.remove(sequenceName);}
        }
        return sequences;
    }

    public boolean compareCacheSequences() {
        //convert config to cache version, compare strings
        return true;
    }
}
