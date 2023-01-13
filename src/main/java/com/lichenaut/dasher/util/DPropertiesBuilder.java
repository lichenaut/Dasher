package com.lichenaut.dasher.util;

import java.util.HashSet;

public class DPropertiesBuilder {

    public static HashSet<String> getKnownProperties() {
        HashSet<String> knownProperties = new HashSet<>(26);
        knownProperties.add("sequence");
        knownProperties.add("toggle-permission");
        knownProperties.add("cooldown");
        knownProperties.add("permission");
        knownProperties.add("trigger");
        knownProperties.add("item-name");
        knownProperties.add("item-material");
        knownProperties.add("sound");
        knownProperties.add("particle");
        knownProperties.add("effect");
        knownProperties.add("look-affects-height");
        knownProperties.add("loops");
        knownProperties.add("keep-momentum");
        knownProperties.add("tp-top");
        knownProperties.add("tp-floor");
        knownProperties.add("forward");
        knownProperties.add("backward");
        knownProperties.add("up");
        knownProperties.add("down");
        knownProperties.add("left");
        knownProperties.add("right");
        knownProperties.add("damage");
        knownProperties.add("experience");
        knownProperties.add("invulnerable");
        knownProperties.add("fall-damage");
        knownProperties.add("buffer");
        return knownProperties;
    }

    public static HashSet<String> getNoSpacesProperties() {
        HashSet<String> noSpacesProperties = new HashSet<>(19);
        noSpacesProperties.add("toggle-permission");
        noSpacesProperties.add("cooldown");
        noSpacesProperties.add("permission");
        noSpacesProperties.add("look-affects-height");
        noSpacesProperties.add("loops");
        noSpacesProperties.add("keep-momentum");
        noSpacesProperties.add("tp-top");
        noSpacesProperties.add("tp-floor");
        noSpacesProperties.add("forward");
        noSpacesProperties.add("backward");
        noSpacesProperties.add("up");
        noSpacesProperties.add("down");
        noSpacesProperties.add("left");
        noSpacesProperties.add("right");
        noSpacesProperties.add("damage");
        noSpacesProperties.add("experience");
        noSpacesProperties.add("invulnerable");
        noSpacesProperties.add("fall-damage");
        noSpacesProperties.add("buffer");
        return noSpacesProperties;
    }

    public static HashSet<String> getNoColonProperties() {
        HashSet<String> noColonProperties = new HashSet<>(2);
        noColonProperties.add("toggle-permission");
        noColonProperties.add("permission");
        return noColonProperties;
    }
}
