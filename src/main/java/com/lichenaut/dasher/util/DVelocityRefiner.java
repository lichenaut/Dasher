package com.lichenaut.dasher.util;

import com.lichenaut.dasher.DashSequence;
import com.lichenaut.dasher.PlayerDasher;
import org.bukkit.util.Vector;

public class DVelocityRefiner {

    private final DashSequence dashSequence;

    public DVelocityRefiner(DashSequence dashSequence) {this.dashSequence = dashSequence;}

    public Vector getRefinedVelocities(PlayerDasher p) {
        Double pX = p.getLocation().getDirection().getX();
        Double pY = p.getLocation().getDirection().getY();
        Double pZ = p.getLocation().getDirection().getZ();
        if (!forward) {
            pX = -pX;
        }
        if (!up) {
            pY = -pY;
        }
        if (!right) {
            pZ
        }
    }
}
