package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.openSession()) {
            session.beginTransaction();

            String sql = """
                    CREATE TABLE IF NOT EXISTS users(
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(128) NOT NULL ,
                        last_name VARCHAR(128) NOT NULL ,
                        age SMALLINT NOT NULL 
                      );
                    """;

            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.openSession()) {
            session.beginTransaction();

            String sql = """
                    DROP TABLE IF EXISTS users;
                    """;

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null)
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;

        try (Session session = Util.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.openSession()){
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
