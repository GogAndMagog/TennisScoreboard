package org.fizz_buzz.model;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
public class PlayerTest {

    public static SessionFactory sessionFactory;
    public static final String DEFAULT_NAME = "Biba";

    @BeforeAll
    public static void setUpBeforeClass() {
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        log.info("Setting up SessionFactory");
        try {
            sessionFactory =
                    new MetadataSources(registry)
                            .addAnnotatedClass(Player.class)
                            .buildMetadata()
                            .buildSessionFactory();
            log.info("Successfully setup SessionFactory");
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            log.error("Error occurred: ", e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    public void persistenceTest()
    {
        Player player = Player.builder()
                .name(DEFAULT_NAME)
                .build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(player);
        session.getTransaction().commit();

        Player player2 = session.createQuery("FROM Players WHERE name =: Name", Player.class)
                .setParameter("Name", DEFAULT_NAME)
                .getSingleResult();

        Assertions.assertEquals(DEFAULT_NAME, player2.getName());
    }

}
