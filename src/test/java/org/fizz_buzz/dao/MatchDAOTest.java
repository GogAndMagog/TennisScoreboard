package org.fizz_buzz.dao;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MatchDAOTest {

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
        String nameIvan = "Ivan";
        String nameOleg = "Oleg";

        var dao = MatchDAO.getInstance();
        var olegMatches = dao.findByName(nameOleg);

        log.info("Oleg matches: ");
        for (Match olegMatch : olegMatches) {
            log.info(olegMatch.toString());
        }

        var ivanMatches = dao.findByName(nameIvan);

        log.info("Ivan matches first page: ");
        for (Match ivanMatch : ivanMatches) {
            log.info(ivanMatch.toString());
        }

        ivanMatches = dao.findByName(nameIvan, 2);

        log.info("Ivan matches second page: ");
        for (Match ivanMatch : ivanMatches) {
            log.info(ivanMatch.toString());
        }

    }
}