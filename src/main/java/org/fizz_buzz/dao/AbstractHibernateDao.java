package org.fizz_buzz.dao;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractHibernateDao<T extends Serializable> {

    protected Class<T> clazz;

    protected SessionProvider sessionFactory = SessionProvider.getInstance();

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    public Optional<T> findById(final long id) {
        try (var session = getCurrentSession()) {
            var entity = session.get(clazz, id);

            return Optional.ofNullable(entity);
        } catch (RuntimeException e) {
            throw new DAOException(this.clazz.toString(), "Can't find by id: %d".formatted(id), e);
        }
    }

    public List<T> findAll() {
        try (var session = getCurrentSession()) {
            return session.createSelectionQuery("from " + clazz.getName(), clazz).list();
        } catch (RuntimeException e) {
            throw new DAOException(this.clazz.toString(), "Can't find all", e);
        }
    }

    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        var currentSession = getCurrentSession();
        try {
            currentSession.beginTransaction();
            currentSession.persist(entity);
            currentSession.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            currentSession.getTransaction().rollback();
            throw new DAOException(this.clazz.toString(), "Can't create entity", e);
        } finally {
            currentSession.close();
        }
    }

    protected Session getCurrentSession() {
        return sessionFactory.getSession();
    }

}
