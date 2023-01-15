package com.lichenaut.dasher.references;

import java.util.HashSet;

public class DPropertyReference {

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
        knownProperties.add("in-air");
        return knownProperties;
    }

    public static HashSet<String> getIntProperties() {
        HashSet<String> intProperties = new HashSet<>(12);
        intProperties.add("cooldown");
        intProperties.add("forward");
        intProperties.add("backward");
        intProperties.add("up");
        intProperties.add("down");
        intProperties.add("left");
        intProperties.add("right");
        intProperties.add("damage");
        intProperties.add("experience");
        intProperties.add("invulnerable");
        intProperties.add("fall-damage");
        intProperties.add("buffer");
        return intProperties;
    }

    public static HashSet<String> getStringProperties() {
        HashSet<String> stringProperties = new HashSet<>(9);
        stringProperties.add("toggle-permission");
        stringProperties.add("permission");
        stringProperties.add("trigger");
        stringProperties.add("item-name");
        stringProperties.add("item-material");
        stringProperties.add("sound");
        stringProperties.add("particle");
        stringProperties.add("effect");
        stringProperties.add("look-affects-height");
        return stringProperties;
    }

    public static HashSet<String> getBooleanProperties() {
        HashSet<String> booleanProperties = new HashSet<>(4);
        booleanProperties.add("loops");
        booleanProperties.add("keep-momentum");
        booleanProperties.add("tp-top");
        booleanProperties.add("tp-floor");
        booleanProperties.add("in-air");
        return booleanProperties;
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

    public static HashSet<String> getNoCommaProperties() {//other properties shouldn't have commas, but this is just to protect against confusion with these properties in particular
        HashSet<String> noCommaProperties = new HashSet<>(3);
        noCommaProperties.add("toggle-permission");
        noCommaProperties.add("permission");
        noCommaProperties.add("look-affects-height");
        return noCommaProperties;
    }

    public static HashSet<String> getNoColonProperties() {//same as comma variant
        HashSet<String> noColonProperties = new HashSet<>(2);
        noColonProperties.add("toggle-permission");
        noColonProperties.add("permission");
        return noColonProperties;
    }
}
