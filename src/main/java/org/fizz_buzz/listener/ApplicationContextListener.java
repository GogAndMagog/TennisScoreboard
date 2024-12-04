package org.fizz_buzz.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load H2 driver");
            throw new RuntimeException(e);
        }
    }
}
