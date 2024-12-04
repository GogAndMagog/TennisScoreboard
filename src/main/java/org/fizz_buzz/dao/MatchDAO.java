package org.fizz_buzz.dao;

import org.fizz_buzz.model.Match;
import org.hibernate.query.Order;
import org.hibernate.query.Query;

import java.util.List;

public class MatchDAO extends AbstractHibernateDao<Match> {

    private volatile static MatchDAO instance;

    private static final int DEFAULT_PAGE_SIZE = 6;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private MatchDAO() {
        setClazz(Match.class);
    }

    public static MatchDAO getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new MatchDAO();
                }
            }
        }
        return instance;
    }

    public List<Match> findByPage(int limitPerPage, int page) {
        String sql = "SELECT m FROM Matches m";

        try(var session = getCurrentSession()) {
            Query query = session.createQuery(sql)
                    .setFirstResult(calculateOffset(page, limitPerPage))
                    .setMaxResults(limitPerPage)
                    .setOrder(Order.asc(Match.class, "id"));
            return query.getResultList();
        } catch (RuntimeException e) {
            throw e;
        }

    }

    public List<Match> findByName(String name) {
        return findByPage(DEFAULT_PAGE_SIZE, 1);
    }

    public List<Match> findByName(String name, int page) {


        try(var session = getCurrentSession()) {
            String sql =
                    """
                                        
                                            SELECT m FROM Matches m\s
                                            WHERE   m.player1.name LIKE :name\s
                                                OR  m.player2.
                            name LIKE :name""";

            Query query
                    = session.createQuery(sql)
                    .setParameter("name", name)
                            .
                    setFirstResult(
                            calculateOffset(page, pageSize))
                    .setMaxResults(pageSize)
                    .setOrder(Order.asc(Match.class, "id"));
            return query.getResultList();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private int calculateOffset(int page, int limit) {
        return ((limit * page) - limit);
    }
}
