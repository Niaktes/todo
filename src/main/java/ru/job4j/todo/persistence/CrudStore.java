package ru.job4j.todo.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class CrudStore {

    private final SessionFactory sf;

    public boolean run(Consumer<Session> command) {
        boolean result;
        try {
            execute(session -> {
                command.accept(session);
                return null;
            });
            result = true;
        } catch (HibernateException e) {
            result = false;
        }
        return result;
    }

    public boolean run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sessionQuery = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
           sessionQuery.executeUpdate();
        };
        return run(command);
    }

    public <T> T getOne(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, T> command = session -> {
            var sessionQuery = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            return sessionQuery.uniqueResult();
        };
        return execute(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sessionQuery = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            return sessionQuery.uniqueResultOptional();
        };
        return execute(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return execute(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sessionQuery = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            return sessionQuery.list();
        };
        return execute(command);
    }

    private <T> T execute(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
    }

}