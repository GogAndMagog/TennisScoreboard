package org.fizz_buzz.dao;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractHibernateDao<T extends Serializable> {

    private Class<T> clazz;

    protected SessionProvider sessionFactory = SessionProvider.getInstance();

    public final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    public Optional<T> findOne(final long id) {
        try (var session = getCurrentSession()) {
            var entity = session.get(clazz, id);

            return Optional.ofNullable(entity);

        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<T> findAll() {
        try (var session = getCurrentSession()) {
            return session.createSelectionQuery("from " + clazz.getName(), clazz).list();
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        try (var currentSession = getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.persist(entity);
            currentSession.flush();
            currentSession.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    protected Session getCurrentSession() {
        return sessionFactory.getSession();
    }

}
