package com.lichenaut.dasher.util;

import com.lichenaut.dasher.Dasher;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DResourceCreator {

    private final Dasher plugin;

    public DResourceCreator(Dasher plugin) {this.plugin = plugin;}

    public void createResource(String resourceName) {
        String resourcePath = plugin.getDataFolderPath() + DFileSeparatorGetter.getSeparator() + resourceName;
        if (!new File(resourcePath).exists()) {
            try {
                DCopier.byteCopy(Objects.requireNonNull(plugin.getResource(resourceName)), resourcePath);
            } catch (IOException e) {
                plugin.getLog().severe("IOException: Could not generate '" + resourceName + "'!");
                e.printStackTrace();
            } catch (NullPointerException e) {
                plugin.getLog().severe("NullPointerException: Could not generate '" + resourceName + "'!");
                e.printStackTrace();
            }
        }
    }
}