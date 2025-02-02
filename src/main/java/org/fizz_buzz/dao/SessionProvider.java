package org.fizz_buzz.dao;

import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.SharedCacheMode;
import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.model.Match;
import org.fizz_buzz.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Slf4j
public class SessionProvider {

    private volatile static SessionProvider instance;
    private final SessionFactory factory;

    private SessionProvider() {

        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            factory =
                    new MetadataSources(registry)
                            .addAnnotatedClass(Match.class)
                            .addAnnotatedClass(Player.class)
                            .buildMetadata()
                            .buildSessionFactory();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionProvider getInstance() {
        if (instance == null) {
            synchronized (SessionProvider.class) {
                if (instance == null) {
                    instance = new SessionProvider();
                }
            }
        }
        return instance;
    }


    public Session getSession() {
        return factory.openSession();
    }
}
