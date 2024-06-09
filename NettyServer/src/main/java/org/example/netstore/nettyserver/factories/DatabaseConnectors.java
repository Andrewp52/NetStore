package org.example.netstore.nettyserver.factories;

import org.example.netstore.nettyserver.PropertiesHolder;
import org.example.netstore.nettyserver.database.ConnectionProperties;
import org.example.netstore.nettyserver.database.connectors.ConnectorType;
import org.example.netstore.nettyserver.database.connectors.DatabaseConnector;
import org.example.netstore.nettyserver.database.connectors.SqliteDatabaseConnector;

import java.util.Properties;


public abstract class DatabaseConnectors {

    public static DatabaseConnector getDatabaseConnector(){
        ConnectionProperties connectionProperties = getConnectionProperties();
        DatabaseConnector connector;
        switch (connectionProperties.type()){
            case SQLITE -> connector = SqliteDatabaseConnector.getInstance(connectionProperties);
            case MYSQL -> connector = null;
            default -> {throw new RuntimeException("Invalid connector type");}
        }
        return connector;
    }

    private static ConnectionProperties getConnectionProperties(){
        Properties properties = PropertiesHolder.properties;
        ConnectorType type = ConnectorType.valueOf(properties.getProperty("db.type"));
        return ConnectionProperties.builder()
                .connectorType(type)
                .url(properties.getProperty("db.url", ""))
                .username(properties.getProperty("db.username", ""))
                .password(properties.getProperty("db.password", ""))
                .build();
    }
}
