package org.fizz_buzz.dao;

import org.fizz_buzz.model.Player;

import java.util.Optional;

public class PlayerDAO extends AbstractHibernateDao<Player> {

    private volatile static PlayerDAO instance;

    private PlayerDAO() {
        setClazz(Player.class);
    }

    public static PlayerDAO getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new PlayerDAO();
                }
            }
        }
        return instance;
    }

    public Optional<Player> findByName(String name) {


        try(var session = sessionFactory.getSession()) {
            return Optional.ofNullable((Player) session
                    .createQuery("From Players WHERE name LIKE :name")
                    .setParameter("name", name)
                    .uniqueResult());
        } catch (RuntimeException e) {
            throw e;
        }
    }

}
