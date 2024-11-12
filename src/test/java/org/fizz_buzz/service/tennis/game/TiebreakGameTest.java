package org.fizz_buzz.service.tennis.game;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TiebreakGameTest {

    private static final String ANN_NAME = "Ann";
    private static final String ROB_NAME = "Rob";

    private static final String NO_WINNER = "No winner";
    private static final String PLAYER_INFO = "Player: %s Score: %s";

    @Test
    void addScore() {
        TennisGame tennisGame = new TiebreakGame(ANN_NAME, ROB_NAME);
        tennisGame.addScoreFirstPlayer();
        var annPlayerScoreState = tennisGame.getFirstPlayer().getScore().getState();

        assertEquals(1, annPlayerScoreState);
    }

    @Test
    void winTiebreak() {
        TennisGame tennisGame = new TiebreakGame(ANN_NAME, ROB_NAME);
        for (int i = 0; i < 7; i++) {
            tennisGame.addScoreFirstPlayer();
        }
        assertTrue(tennisGame.isFinished());
        tennisGame.getWinner()
                .ifPresentOrElse(winner -> assertEquals(ANN_NAME, winner),
                        () -> fail(NO_WINNER));
    }

//    @Test
//    void startTiebreakAgain() {
//        TennisGame tennisGame = new TiebreakGame(ANN_NAME, ROB_NAME);
//        for (int i = 0; i < 6; i++) {
//            tennisGame.addScore(ANN_NAME);
//        }
//        for (int i = 0; i < 5; i++) {
//            tennisGame.addScore(ROB_NAME);
//        }
//
//        var score = tennisGame.getScore();
//        assertEquals(Integer.toString(6), score.get(ANN_NAME).getScore().toString());
//        assertEquals(Integer.toString(5), score.get(ROB_NAME).getScore().toString());
//        assertFalse(tennisGame.isFinished());
//        log.info(PLAYER_INFO.formatted(score.get(ANN_NAME).getName(), score.get(ANN_NAME).getScore().toString()));
//        log.info(PLAYER_INFO.formatted(score.get(ROB_NAME).getName(), score.get(ROB_NAME).getScore().toString()));
//
//
//        tennisGame.addScore(ROB_NAME);
//        tennisGame.addScore(ROB_NAME);
//        tennisGame.addScore(ROB_NAME);
//        tennisGame.addScore(ROB_NAME);
//
////        score = tennisGame.getScore();
//
//        log.info(PLAYER_INFO.formatted(score.get(ANN_NAME).getName(), score.get(ANN_NAME).getScore().toString()));
//        log.info(PLAYER_INFO.formatted(score.get(ROB_NAME).getName(), score.get(ROB_NAME).getScore().toString()));
//
//        assertTrue(tennisGame.isFinished());
//        tennisGame.getWinner()
//                .ifPresentOrElse(winner -> assertEquals(ROB_NAME, winner),
//                        () -> fail(NO_WINNER));
//    }

}