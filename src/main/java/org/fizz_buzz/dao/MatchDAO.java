package org.fizz_buzz.dao;

import jakarta.persistence.criteria.Selection;
import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.exception.DAOException;
import org.fizz_buzz.model.Match;
import org.fizz_buzz.validation.ParamValidator;
import org.hibernate.query.Order;
import org.hibernate.query.SelectionQuery;

import java.util.List;

@Slf4j
public class MatchDAO extends AbstractHibernateDao<Match> {

    private volatile static MatchDAO instance;

    public static final int DEFAULT_PAGE_SIZE = 5;

    private ParamValidator paramValidator = ParamValidator.getInstance();

    private MatchDAO() {
        setClazz(Match.class);
    }

    public static MatchDAO getInstance() {
        if (instance == null) {
            synchronized (MatchDAO.class) {
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
            throw new DAOException(this.clazz.toString(), "Can't find by page %d".formatted(page), e);
        }
    }

    public List<Match> findByName(String name) {
        return findByName(name, 1);
    }

    public List<Match> findByName(String name, int page) {

        try (var session = getCurrentSession()) {
            String sqlWithNameParameter =
                    """                                        
                            SELECT m FROM Matches m\s
                                WHERE   m.player1.name LIKE :name\s
                                    OR  m.player2.name LIKE :name""";
//            String sqlWithoutParameters =
//                    """
//                            SELECT m FROM Matches m\s""";

//            String sql;
            SelectionQuery<Match> query;

//            if (paramValidator.isEmpty(name)) {
//                query = session
//                        .createSelectionQuery(sqlWithoutParameters, Match.class)
//                        .setFirstResult(calculateOffset(page, DEFAULT_PAGE_SIZE))
//                        .setMaxResults(DEFAULT_PAGE_SIZE)
//                        .setOrder(Order.asc(Match.class, "id"));
//            } else {
                query = session
                        .createSelectionQuery(sqlWithNameParameter, Match.class)
                        .setParameter("name", "%" + name.toLowerCase() + "%")
                        .setFirstResult(calculateOffset(page, DEFAULT_PAGE_SIZE))
                        .setMaxResults(DEFAULT_PAGE_SIZE)
                        .setOrder(Order.asc(Match.class, "id"));
//            }

            return query.getResultList();
        } catch (Exception e) {
            throw new DAOException(this.clazz.toString(),
                    "Can't find by page %d with name %s".formatted(page, name),
                    e);
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
            return calculateTotalPages(totalRows, pageSize);
        } catch (Exception e) {
            throw new DAOException(this.clazz.toString(),
                    "Can't get total pages",
                    e);
        }
    }

    public int totalPages(int pageSize, String playerName) {
        try (var session = getCurrentSession()) {
            String sqlWithNameParameter =
                    """                                        
                            SELECT COUNT(m) FROM Matches m
                                WHERE   m.player1.name LIKE :name\s
                                    OR  m.player2.name LIKE :name
                            """;

//            String sqlWithoutNameParameter =
//                    """
//                            SELECT m FROM Matches m\s""";

            SelectionQuery<Long> query;

//            if (paramValidator.isEmpty(playerName)) {
//                query = session
//                        .createSelectionQuery(sqlWithoutNameParameter, Long.class);
//            } else {
                query = session
                        .createSelectionQuery(sqlWithNameParameter, Long.class)
                        .setParameter("name", "%" + playerName.toLowerCase() + "%");
//            }

            var totalRows = query.uniqueResult().intValue();
            return calculateTotalPages(totalRows, pageSize);
        } catch (Exception e) {
            throw new DAOException(this.clazz.toString(),
                    "Can't get total pages for name %s".formatted(playerName),
                    e);
        }
    }

    private int calculateTotalPages(int totalRows, int pageSize) {
        var pageNumbers = totalRows / pageSize;
        if (totalRows % pageSize != 0) {
            pageNumbers++;
        }

        return pageNumbers;
    }
}
