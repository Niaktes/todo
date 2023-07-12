package ru.job4j.todo.persistence;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

@Repository
@Slf4j
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {

    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Task result = null;
        try (session) {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE id = :tId", Task.class)
                    .setParameter("tId", id)
                    .uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        Collection<Task> result = null;
        try (session) {
            session.beginTransaction();
            result = session.createQuery("FROM Task", Task.class)
                    .list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try (session) {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            result = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try (session) {
            session.beginTransaction();
            int lines = session.createQuery("DELETE Task WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            result = lines > 0;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug(e.getMessage(), e);
        }
        return result;
    }

}