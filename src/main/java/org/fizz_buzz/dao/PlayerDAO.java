package org.fizz_buzz.dao;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.exception.DAOException;
import org.fizz_buzz.model.Player;
import org.fizz_buzz.validation.ParamValidator;

import java.util.Optional;

@Slf4j
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
        try (var session = sessionFactory.getSession()) {
            return Optional.ofNullable(session
                    .createSelectionQuery("FROM Players WHERE name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResult());
        } catch (RuntimeException e) {
            throw new DAOException(this.clazz.toString(),
                    "Can't find by name %s".formatted(name),
                    e);
        }
    }

}
