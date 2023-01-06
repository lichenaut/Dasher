package com.lichenaut.dasher.util;

import com.lichenaut.dasher.Dasher;

import java.io.File;

public class DDirectoryMaker {

    private final Dasher plugin;

    public DDirectoryMaker(Dasher plugin) {this.plugin = plugin;}

    public void makeDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {if (!dir.mkdirs()) {plugin.getLog().severe("Could not create file '" + path + "'! SecurityException?");}}
    }
}