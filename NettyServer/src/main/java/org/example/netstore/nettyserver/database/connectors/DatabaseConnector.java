package org.example.netstore.nettyserver.database.connectors;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
    Connection getConnection() throws SQLException;
}
