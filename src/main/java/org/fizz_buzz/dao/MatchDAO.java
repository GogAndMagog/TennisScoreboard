package org.fizz_buzz.dao;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.model.Match;
import org.hibernate.query.Order;

import java.util.List;

@Slf4j
public class MatchDAO extends AbstractHibernateDao<Match> {

    private volatile static MatchDAO instance;

    public static final int DEFAULT_PAGE_SIZE = 5;

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

        try (var session = getCurrentSession()) {
            var query = session
                    .createSelectionQuery(sql, Match.class)
                    .setFirstResult(calculateOffset(page, limitPerPage))
                    .setMaxResults(limitPerPage)
                    .setOrder(Order.asc(Match.class, "id"));
            return query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Match> findByName(String name) {
        return findByPage(DEFAULT_PAGE_SIZE, 1);
    }

    public List<Match> findByName(String name, int page) {

        try (var session = getCurrentSession()) {
            String sql =
                    """                                        
                            SELECT m FROM Matches m\s
                                WHERE   m.player1.name ILIKE :name\s
                                    OR  m.player2.name ILIKE :name""";

            var query = session
                    .createSelectionQuery(sql, Match.class)
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult(calculateOffset(page, DEFAULT_PAGE_SIZE))
                    .setMaxResults(DEFAULT_PAGE_SIZE)
                    .setOrder(Order.asc(Match.class, "id"));

            return query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private int calculateOffset(int page, int limit) {
        return ((limit * page) - limit);
    }

    public List<Match> findAll(int page) {
        return findByPage(DEFAULT_PAGE_SIZE, page);
    }

    public int totalPages(int pageSize) {
        try (var session = getCurrentSession()) {
            String sql =
                    """                                        
                            SELECT COUNT(m) FROM Matches m""";

            var query = session.createSelectionQuery(sql, Long.class);

            var totalRows = query.uniqueResult().intValue();
            var pageNumbers = totalRows / pageSize;
            if (totalRows % pageSize != 0) {
                pageNumbers++;
            }

            return pageNumbers;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int totalPages(int pageSize, String playerName) {
        try (var session = getCurrentSession()) {
            String sql =
                    """                                        
                            SELECT COUNT(m) FROM Matches m
                                WHERE   m.player1.name ILIKE :name\s
                                    OR  m.player2.name ILIKE :name
                            """;

            var query = session
                    .createSelectionQuery(sql, Long.class)
                    .setParameter("name", "%" + playerName + "%");

            var totalRows = query.uniqueResult().intValue();
            var pageNumbers = totalRows / pageSize;
            if (totalRows % pageSize != 0) {
                pageNumbers++;
            }

            return pageNumbers;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
