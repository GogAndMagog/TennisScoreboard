package org.fizz_buzz.domain.tennis.score.state;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StandardGameScoreState implements ScoreState {

    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    DEUCE("DE"),
    ADVANTAGE("AD"),
    WIN("WN");

    private final String textView;

    @Override
    public String toString() {
        return this.textView;
    }
}
