package ru.job4j.todo.persistence;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

@Repository
@Slf4j
@AllArgsConstructor
public class HibernateUserStore implements UserStore {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            result = Optional.of(user);
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        } finally {
           session.close();
        }
        return result;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM User WHERE login = :uLogin AND password = :uPassword",
                            User.class)
                    .setParameter("uLogin", login)
                    .setParameter("uPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

}