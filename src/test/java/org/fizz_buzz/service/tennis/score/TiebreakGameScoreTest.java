package org.fizz_buzz.service.tennis.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiebreakGameScoreTest {

    @Test
    void addScore() {
        TiebreakGameScore tiebreakGameScore = new TiebreakGameScore();
        addScore(tiebreakGameScore);
        assertEquals(Integer.toString(1), tiebreakGameScore.toString());
    }

    @Test
    void removeScore() {
        TiebreakGameScore tiebreakGameScore = new TiebreakGameScore();
        assertThrows(IllegalArgumentException.class, () ->  removeScore(tiebreakGameScore));
    }

    @Test
    void testToString() {
        Score tiebreakGameScore = new TiebreakGameScore();
        assertEquals("0", tiebreakGameScore.toString());
    }

    private void addScore(TiebreakGameScore score) {
        score.setState(score.getState() + 1);
    }

    private void removeScore(TiebreakGameScore score) {
        score.setState(score.getState() - 1);
    }
}