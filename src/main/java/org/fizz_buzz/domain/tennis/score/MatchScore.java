package org.fizz_buzz.domain.tennis.score;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MatchScore implements Score<Integer> {

    private static final String OUT_OF_BOUNDS = "Match score must be positive integer.";
    private static final Integer LOWER_BOUND = 0;

    private Integer state = LOWER_BOUND;

    @Override
    public void setState(Integer state) {
        if (state < LOWER_BOUND) {
            throw new IllegalArgumentException(OUT_OF_BOUNDS);
        }
        else {
            this.state = state;
        }
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
