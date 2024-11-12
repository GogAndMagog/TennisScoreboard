package org.fizz_buzz.service.tennis.game;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.service.tennis.score.state.StandardGameScoreState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StandardGameTest {

    private static final String BOB_NAME = "Bob";
    private static final String JOE_NAME = "Joe";

    private static final String WINNER_NOT_FOUND = "Winner not found";

    @Test
    void addScore() {
        TennisGame tennisGame = new StandardGame(BOB_NAME, JOE_NAME);
        tennisGame.addScoreFirstPlayer();
        var bobScoreState = tennisGame.getFirstPlayer().getScore().getState();
        assertEquals(StandardGameScoreState.FIFTEEN, bobScoreState);
    }

    @Test
    void isFinished() {
        TennisGame tennisGame = new StandardGame(BOB_NAME, JOE_NAME);
        assertFalse(tennisGame.isFinished());
        for (int i = 0; i < 4; i++) {
            tennisGame.addScoreFirstPlayer();
        }
        assertTrue(tennisGame.isFinished());
    }

    @Test
    void getWinner() {
        TennisGame tennisGame = new StandardGame(BOB_NAME, JOE_NAME);
        assertFalse(tennisGame.isFinished());
        for (int i = 0; i < 4; i++) {
            tennisGame.addScoreFirstPlayer();
        }
        tennisGame.getWinner()
                .ifPresentOrElse(s -> assertEquals(BOB_NAME, s),
                        () -> fail(WINNER_NOT_FOUND));
    }

    @Test
    void deuceTest() {
        TennisGame tennisGame = new StandardGame(BOB_NAME, JOE_NAME);

        for (int i = 0; i < 3; i++) {
            tennisGame.addScoreFirstPlayer();
        }
        for (int i = 0; i < 3; i++) {
            tennisGame.addScoreSecondPlayer();
        }

        assertEquals(StandardGameScoreState.DEUCE, tennisGame.getFirstPlayer().getScore().getState());
        assertEquals(StandardGameScoreState.DEUCE, tennisGame.getSecondPlayer().getScore().getState());
    }

    @Test
    void advantageTest() {
        TennisGame tennisGame = new StandardGame(BOB_NAME, JOE_NAME);

        var bobScore = tennisGame.getFirstPlayer().getScore();
        var joeScore = tennisGame.getSecondPlayer().getScore();

        for (int i = 0; i < 3; i++) {
            tennisGame.addScoreFirstPlayer();
        }
        for (int i = 0; i < 3; i++) {
            tennisGame.addScoreSecondPlayer();
        }
        tennisGame.addScoreFirstPlayer();

        assertEquals(StandardGameScoreState.ADVANTAGE, bobScore.getState());
        assertEquals(StandardGameScoreState.FORTY, joeScore.getState());

        tennisGame.addScoreSecondPlayer();

        assertEquals(StandardGameScoreState.DEUCE, bobScore.getState());
        assertEquals(StandardGameScoreState.DEUCE, joeScore.getState());

        tennisGame.addScoreSecondPlayer();

        assertEquals(StandardGameScoreState.ADVANTAGE, joeScore.getState());
        assertEquals(StandardGameScoreState.FORTY, bobScore.getState());

        tennisGame.addScoreSecondPlayer();

        assertEquals(StandardGameScoreState.WIN, joeScore.getState());
        assertEquals(StandardGameScoreState.FORTY, bobScore.getState());
        assertTrue(tennisGame.isFinished());
        tennisGame.getWinner()
                .ifPresentOrElse(s -> assertEquals(JOE_NAME, s),
                        () -> fail(WINNER_NOT_FOUND));
    }
}