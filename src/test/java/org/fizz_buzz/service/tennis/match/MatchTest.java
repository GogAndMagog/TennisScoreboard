package org.fizz_buzz.service.tennis.match;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.domain.tennis.match.TennisMatch;
import org.fizz_buzz.service.TennisScoreboard;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MatchTest {

    private static final String BOB_NAME = "Bob";
    private static final String JOE_NAME = "Joe";

    @Test
    void matchTest() {
        TennisMatch testTennisMatch = new TennisMatch(UUID.randomUUID(), BOB_NAME, JOE_NAME);
        TennisScoreboard testScoreboard = TennisScoreboard.getInstance();

        winSetSecondPlayer(testTennisMatch);
        winSetSecondPlayer(testTennisMatch);

        winSetSecondPlayer(testTennisMatch);
        log.info(getScoreString(testTennisMatch));
        assertEquals(2, testTennisMatch.getSecondPlayer().getScore().getState());
        assertTrue(testTennisMatch.isFinished());
        assertEquals(JOE_NAME, testTennisMatch.getWinner());
        log.info(testScoreboard.getScoreboard(testTennisMatch).toString());

        testTennisMatch = new TennisMatch(UUID.randomUUID(), BOB_NAME, JOE_NAME);
        testScoreboard = TennisScoreboard.getInstance();

        winSetFirstPlayer(testTennisMatch);
        winSetSecondPlayer(testTennisMatch);
        winSetFirstPlayer(testTennisMatch);
        log.info(getScoreString(testTennisMatch));
        assertEquals(2, testTennisMatch.getFirstPlayer().getScore().getState());
        assertTrue(testTennisMatch.isFinished());
        assertEquals(BOB_NAME, testTennisMatch.getWinner());

        log.info(testScoreboard.getScoreboard(testTennisMatch).toString());
    }

    private void winGameFirstPlayer(TennisMatch tennisMatch) {
        for (int i = 0; i < 4; i++) {
            tennisMatch.addScoreFirstPlayer();
        }
    }

    private void winGameSecondPlayer(TennisMatch tennisMatch) {
        for (int i = 0; i < 4; i++) {
            tennisMatch.addScoreSecondPlayer();
        }
    }

    private void winSetFirstPlayer(TennisMatch tennisMatch) {
        for (int i = 0; i < 6; i++) {
            winGameFirstPlayer(tennisMatch);
        }
    }

    private void winSetSecondPlayer(TennisMatch tennisMatch) {
        for (int i = 0; i < 6; i++) {
            winGameSecondPlayer(tennisMatch);
        }
    }

    private String getScoreString(TennisMatch tennisMatch) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nFirst player set: %s ".formatted(tennisMatch.getFirstPlayer().getScore()))
                .append("games: %s ".formatted(tennisMatch.getSet().getFirstPlayer().getScore()))
                .append("score: %s\n".formatted(tennisMatch.getSet().getGame().getFirstPlayer().getScore()))
                .append("Second player set: %s ".formatted(tennisMatch.getSecondPlayer().getScore()))
                .append("games: %s ".formatted(tennisMatch.getSet().getSecondPlayer().getScore()))
                .append("score: %s\n".formatted(tennisMatch.getSet().getGame().getSecondPlayer().getScore()));
        return sb.toString();
    }
}