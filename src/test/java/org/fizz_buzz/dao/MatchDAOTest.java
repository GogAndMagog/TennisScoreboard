package org.fizz_buzz.dao;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MatchDAOTest {

    private static final String IVAN_NAME = "Ivan";
    private static final String OLEG_NAME = "Oleg";
    private static final String SASHA_NAME = "Sasha";
    private static final String UUID_DUMMY = "9aab33d5-0cfc-412d-bbfe-79ac357211e7";

    @Test
    void findByPage() {
        int PAGE_SIZE_1 = 6;
        int PAGE_SIZE_2 = 5;
        int PAGE_SIZE_3 = 0;

        var dao = MatchDAO.getInstance();
        var matches = dao.findByPage(PAGE_SIZE_1, 1);
        assertEquals(PAGE_SIZE_1, matches.size());
        System.out.println("First page:");
        for (Match match : matches) {
            System.out.println(match);
        }

        matches = dao.findByPage(PAGE_SIZE_1, 2);
        assertEquals(PAGE_SIZE_2, matches.size());
        System.out.println("Second page:");
        for (Match match : matches) {
            System.out.println(match);
        }

        matches = dao.findByPage(PAGE_SIZE_1, 3);
        assertEquals(PAGE_SIZE_3, matches.size());
        System.out.println("Third page:");
        for (Match match : matches) {
            System.out.println(match);
        }

        Assertions.assertThrows(Exception.class, () -> {
            dao.findByPage(PAGE_SIZE_1, -1);
        });
    }

    @Test
    void findByNameTest() {

        var dao = MatchDAO.getInstance();
        var olegMatches = dao.findByName(OLEG_NAME);

        log.info("Oleg matches: ");
        for (Match olegMatch : olegMatches) {
            log.info(olegMatch.toString());
        }

        var ivanMatches = dao.findByName(IVAN_NAME);

        log.info("Ivan matches first page: ");
        for (Match ivanMatch : ivanMatches) {
            log.info(ivanMatch.toString());
        }

        ivanMatches = dao.findByName(IVAN_NAME, 2);

        log.info("Ivan matches second page: ");
        for (Match ivanMatch : ivanMatches) {
            log.info(ivanMatch.toString());
        }
    }

    @Test
    void match_addTwoSamePlayers_sqlConstraintException() {

        var dao = MatchDAO.getInstance();

        var ivan = PlayerDAO.getInstance().findByName(IVAN_NAME);
        Match testMatch = Match.builder().player1(ivan.get())
                .player2(ivan.get())
                .winner(ivan.get())
                .uuid(UUID.fromString(UUID_DUMMY))
                .build();
        Assertions.assertThrows(RuntimeException.class, () -> {
            dao.create(testMatch);});
    }


    @Test
    void matchPage_countPages_noExceptions()
    {
        var dao = MatchDAO.getInstance();

        try {
            log.info(Long.toString(dao.totalPages(MatchDAO.DEFAULT_PAGE_SIZE)));
            log.info(Long.toString(dao.totalPages(MatchDAO.DEFAULT_PAGE_SIZE, SASHA_NAME)));
            log.info(Long.toString(dao.totalPages(1, SASHA_NAME)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}