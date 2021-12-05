package de.ancozockt.advent.utilities;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    public void downloadInput(String url, File file){
        try {
            InputStream inputStream = new URL(url).openStream();
            Files.copy(inputStream, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ignored){ }
    }

    public BufferedReader getFileInput(String fileName){
        return new BufferedReader(new InputStreamReader(getFile(fileName)));
    }

}
