package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        userService.createUsersTable();

        User user1 = new User("Mark", "Petrov", (byte) 21);
        User user2 = new User("Polina", "Ivanova", (byte) 34);
        User user3 = new User("Dmitriy", "Smirnov", (byte) 43);
        User user4 = new User("Mary", "Orlova", (byte) 25);

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.removeUserById(3);
        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
