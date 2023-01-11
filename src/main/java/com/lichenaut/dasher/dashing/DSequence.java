package com.lichenaut.dasher.dashing;

import com.lichenaut.dasher.Dasher;

public class DSequence {

    private final Dasher plugin;
    private final boolean forward;
    private final boolean up;
    private final boolean right;

    public DSequence(Dasher plugin, boolean forward, boolean up, boolean right) {
        this.plugin = plugin;
        this.forward = forward;
        this.up = up;
        this.right = right;
    }


}
