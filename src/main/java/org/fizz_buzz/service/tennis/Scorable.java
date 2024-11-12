package org.fizz_buzz.service.tennis;

import org.fizz_buzz.service.tennis.score.Score;

public interface Scorable<T extends Score> {
    void addScoreFirstPlayer();
    void addScoreSecondPlayer();
}
