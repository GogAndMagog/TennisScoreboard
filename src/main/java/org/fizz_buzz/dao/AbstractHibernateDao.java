package org.fizz_buzz.dao;

import com.google.common.base.Preconditions;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractHibernateDao<T extends Serializable> {

    private Class<T> clazz;

    protected SessionProvider sessionFactory = SessionProvider.getInstance();

    public final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    public Optional<T> findOne(final long id) {
        var session = getCurrentSession();
        var entity = session.get(clazz, id);
        session.close();

        return Optional.ofNullable(entity);
    }

    public List<T> findAll() {
        var session = getCurrentSession();
        var entities = session.createQuery("from " + clazz.getName()).list();
        session.close();

        return entities;
    }

    public T create(final T entity) {
        Preconditions.checkNotNull(entity);
        var currentSession = getCurrentSession();
        currentSession.saveOrUpdate(entity);
        currentSession.close();
        return entity;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getSession();
    }

}
