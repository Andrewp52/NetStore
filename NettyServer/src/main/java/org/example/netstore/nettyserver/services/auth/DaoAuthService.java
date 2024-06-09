package org.example.netstore.nettyserver.services.auth;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.passencoders.PasswordEncoder;
import org.example.netstore.nettyserver.User;
import org.example.netstore.nettyserver.database.connectors.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAuthService extends AbstractAuthService{
    private final DatabaseConnector connector;
    private Connection connection;

    public DaoAuthService(DatabaseConnector connector, PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
        this.connector = connector;
    }

    @Override
    public void deleteProfile(Integer id) throws ProfileActionException {

    }

    @Override
    public User logIn(String username, String password) {
        try {
            User u = findUser(username);
            if(u != null){
                if(u.getPassword().equals(password)){
                    return u;
                }
            }
//            if(passwordEncoder.match(u.getPassword(), password)){
//                return u;
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User updateProfile(UserDto user) {
        return null;
    }

    @Override
    public boolean changePassword(String oldPass, String newPass) {
        return false;
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        if(this.connection == null || this.connection.isClosed()){
            this.connection = connector.getConnection();
        }
        return connection.prepareStatement(sql);
    }

    private User findUser(String username) throws SQLException {
        try (PreparedStatement statement = prepareStatement("SELECT * FROM users WHERE username=?")){
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            return getUserProfileFromResultSet(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private User getUserProfileFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getBoolean(4),
                resultSet.getLong(5),
                resultSet.getLong(6)
        );
    }
}
