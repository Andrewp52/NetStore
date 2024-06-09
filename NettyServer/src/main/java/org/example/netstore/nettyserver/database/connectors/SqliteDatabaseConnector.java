package org.example.netstore.nettyserver.database.connectors;

import org.example.netstore.nettyserver.database.ConnectionProperties;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SqliteDatabaseConnector implements DatabaseConnector {
    private static final SQLiteDataSource datasource = new SQLiteDataSource();
    private static SqliteDatabaseConnector instance;
    private SqliteDatabaseConnector(ConnectionProperties properties) {
        datasource.setUrl(properties.url());
    }

    public static DatabaseConnector getInstance(ConnectionProperties properties){
        if(instance == null){
            synchronized (SqliteDatabaseConnector.class){
                if(instance == null){
                    instance = new SqliteDatabaseConnector(properties);
                }
            }
        }
        return instance;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }


}
