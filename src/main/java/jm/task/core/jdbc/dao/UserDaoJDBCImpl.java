package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao { //  здесь реализовываю логику
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(128) NOT NULL ,
                    last_name VARCHAR(128) NOT NULL ,
                    age SMALLINT NOT NULL 
                  );
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        String sql = """
                DROP TABLE IF EXISTS users;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = """
                INSERT INTO users(name, last_name, age) 
                VALUES (?,?,?);
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        String sql = """
                DELETE FROM users
                WHERE id = ?;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT id,
                      name,
                      last_name,
                      age
                      FROM users;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet executeResult = preparedStatement.executeQuery();

//            while (executeResult.next()) {
//                users.add(new User(executeResult.getString("name"),
//                        executeResult.getString("last_name"),
//                        executeResult.getByte("age")));
//            }
            while (executeResult.next()){
                User user = new User();
                user.setId(executeResult.getLong("id"));
                user.setName(executeResult.getString("name"));
                user.setLastName(executeResult.getString("last_name"));
                user.setAge(executeResult.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {

        String sql = """
                DELETE FROM users;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
