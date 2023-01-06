package com.lichenaut.dasher.util;

import java.nio.file.FileSystems;

public class DFileSeparatorGetter {

    public static String getSeparator() {return FileSystems.getDefault().getSeparator();}
}