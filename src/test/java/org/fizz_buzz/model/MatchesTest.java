package org.fizz_buzz.model;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@Disabled
public class MatchesTest {
    public static SessionFactory sessionFactory;

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
                            .addAnnotatedClass(Match.class)
                            .buildMetadata()
                            .buildSessionFactory();
            log.info("Successfully setup SessionFactory");
        } catch (Exception e) {
            log.error("Error occurred: ", e);
            StandardServiceRegistryBuilder.destroy(registry);
            throw new AssertionError("Error occurred: ", e);
        }
    }


    @Test
    public void testMatches() {
        var session = sessionFactory.openSession();
        var matches = session.createQuery("from Matches").getResultList();
        session.close();
        log.info(matches.toString());
    }
}
