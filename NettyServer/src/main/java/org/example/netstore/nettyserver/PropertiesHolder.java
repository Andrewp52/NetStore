package org.example.netstore.nettyserver;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertiesHolder {
    private static final String[] PROPS_FILES = {
            "/database.properties",
            "/storage.properties"
    };
    public static final Properties properties = new Properties();

    static {
        for (String s: PROPS_FILES) {
            properties.putAll(readFromFile(s));
        }
    }

    private static Properties readFromFile(String fileName){
        Properties props = new Properties();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(PropertiesHolder.class.getResourceAsStream(fileName))))
        ){
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
