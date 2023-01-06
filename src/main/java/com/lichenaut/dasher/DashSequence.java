package com.lichenaut.dasher;

public class DashSequence {

    private final Dasher plugin;
    private final boolean forward;
    private final boolean up;
    private final boolean right;

    public DashSequence(Dasher plugin, boolean forward, boolean up, boolean right) {
        this.plugin = plugin;
        this.forward = forward;
        this.up = up;
        this.right = right;
    }


}
