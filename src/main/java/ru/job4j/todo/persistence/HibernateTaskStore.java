package ru.job4j.todo.persistence;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
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
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE id = :tId", Task.class)
                    .setParameter("tId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        Collection<Task> result = null;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task", Task.class)
                    .list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findDone() {
        Session session = sf.openSession();
        Collection<Task> result = null;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE done = true", Task.class)
                    .list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findNew() {
        Session session = sf.openSession();
        LocalDate afterDate = LocalDate.now().minusWeeks(1);
        Collection<Task> result = null;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE created > :afterDate", Task.class)
                    .setParameter("afterDate", afterDate)
                    .list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            result = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean getDone(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            int lines = session.createQuery("UPDATE Task SET done = true WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            result = lines > 0;
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            int lines = session.createQuery("DELETE Task WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            result = lines > 0;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

}