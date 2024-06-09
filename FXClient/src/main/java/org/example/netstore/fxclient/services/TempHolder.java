package org.example.netstore.fxclient.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class TempHolder {
    private static List<File> temps = new LinkedList<>();

    public static void addTemp(File file){
        temps.add(file);
    }

    public static void cleanup(){
        temps.forEach(f -> {
            try {
                Files.delete(f.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
