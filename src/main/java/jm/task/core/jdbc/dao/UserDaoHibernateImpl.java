package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(30), " +
                    "lastName VARCHAR(30), " +
                    "age SMALLINT NOT NULL)";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE users";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table dropped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User removed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table cleaned");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
