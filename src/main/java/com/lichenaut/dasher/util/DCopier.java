package com.lichenaut.dasher.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DCopier {

    public static void byteCopy(InputStream in, String outFilePath) throws IOException, NullPointerException {//avoid unnecessary overhead for small files by stream-ing bytes
        try (FileOutputStream out = new FileOutputStream(outFilePath)) {
            int len;while ((len = in.read()) != -1) {out.write((byte) len);}
        }
    }
}