package org.fizz_buzz.service.tennis.set;

import org.fizz_buzz.domain.tennis.game.TiebreakGame;
import org.fizz_buzz.domain.tennis.set.TennisSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TennisSetTest {

    private static final String NO_WINNER = "There is no winner in set";

    private static final String BOB_NAME = "Bob";
    private static final String JOE_NAME = "Joe";

    @Test
    void addScoreFirstPlayer() {
        TennisSet set = new TennisSet(BOB_NAME, JOE_NAME);

        winGameFirstPlayer(set);

        assertEquals(1, set.getFirstPlayer().getScore().getState());

        winGameFirstPlayer(set);

        assertEquals(2, set.getFirstPlayer().getScore().getState());
    }

    @Test
    void addScoreSecondPlayer() {
        TennisSet set = new TennisSet(BOB_NAME, JOE_NAME);

        winGameSecondPlayer(set);

        assertEquals(1, set.getSecondPlayer().getScore().getState());
    }

    @Test
    void winTest() {
        TennisSet set = new TennisSet(BOB_NAME, JOE_NAME);
        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer(set);
        }
        for (int i = 0; i < 4; i++) {
            winGameSecondPlayer(set);
        }

        winGameFirstPlayer(set);

        assertTrue(set.isFinished());
        set.getWinner().ifPresentOrElse(winner -> assertEquals(BOB_NAME, winner),
                () -> fail(NO_WINNER));
    }

    @Test
    void noWinTest() {
        TennisSet set = new TennisSet(BOB_NAME, JOE_NAME);
        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer(set);
        }
        for (int i = 0; i < 5; i++) {
            winGameSecondPlayer(set);
        }

        winGameFirstPlayer(set);

        assertFalse(set.isFinished());
        assertTrue(set.getWinner().isEmpty());
    }


    @Test
    void tiebreakTest() {
        TennisSet set = new TennisSet(BOB_NAME, JOE_NAME);

        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer(set);
        }
        for (int i = 0; i < 5; i++) {
            winGameSecondPlayer(set);
        }

        winGameFirstPlayer(set);
        winGameSecondPlayer(set);

        assertInstanceOf(TiebreakGame.class, set.getGame());

        winTiebreakSecondPlayer(set);

        assertTrue(set.isFinished());
        set.getWinner().ifPresentOrElse(winner -> assertEquals(BOB_NAME, winner),
                () -> fail(NO_WINNER));
    }

    private void winGameFirstPlayer(TennisSet set) {
        for (int i = 0; i < 4; i++) {
            set.addScoreFirstPlayer();
        }
    }

    private void winGameSecondPlayer(TennisSet set) {
        for (int i = 0; i < 4; i++) {
            set.addScoreSecondPlayer();
        }
    }

    private void winTiebreakSecondPlayer(TennisSet set) {
        for (int i = 0; i < 7; i++) {
            set.addScoreFirstPlayer();
        }
    }
}