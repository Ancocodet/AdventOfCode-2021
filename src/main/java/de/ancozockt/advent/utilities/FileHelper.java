package de.ancozockt.advent.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHelper {

    private InputStream getFile(final String fileName){
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public BufferedReader getFileInput(String fileName){
        return new BufferedReader(new InputStreamReader(getFile(fileName)));
    }

}
