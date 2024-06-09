package org.example.netstore.fxclient;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(PropertiesHolder.class.getResourceAsStream("/netclient.properties"));
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(Object key){
        return (String) properties.get(key);
    }
}
