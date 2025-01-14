package org.fizz_buzz.domain.tennis;

import org.fizz_buzz.domain.tennis.score.Score;

public interface Scorable<T extends Score> {
    void addScoreFirstPlayer();
    void addScoreSecondPlayer();
}
