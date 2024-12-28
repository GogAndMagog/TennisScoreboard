package org.fizz_buzz.service.tennis.score;

import org.fizz_buzz.service.tennis.score.state.StandardGameScoreState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardGameScoreTest {

    @Test
    void getState() {
        Score score = new StandardGameScore();
        assertEquals(StandardGameScoreState.ZERO.toString(), score.toString());
    }

    @Test
    @Disabled
    void addScore() {
        StandardGameScore score = new StandardGameScore();
        score.setState(StandardGameScoreState.FIFTEEN);
        assertEquals(StandardGameScoreState.FIFTEEN.toString(), score.toString());

        assertEquals(StandardGameScoreState.values()[StandardGameScoreState.values().length - 1].toString(), score.toString());
    }

    @Test
    void testEquals() {
        Score scoreState = new StandardGameScore();

        Assertions.assertTrue((scoreState.getState() == StandardGameScoreState.ZERO));
    }
}