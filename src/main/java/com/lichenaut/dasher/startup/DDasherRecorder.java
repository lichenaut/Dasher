package com.lichenaut.dasher.startup;

import com.lichenaut.dasher.Dasher;

import java.util.*;

public class DDasherRecorder {

    private final Dasher plugin;

    public DDasherRecorder(Dasher plugin) {this.plugin = plugin;}

    public HashSet<String> getConfigSequences() {
        return new HashSet<>(Objects.requireNonNull(plugin.getConfig().getConfigurationSection("sequences")).getKeys(false));
    }

    public HashSet<String> getCacheSequences() {

    }
}
