package org.fizz_buzz.domain.tennis.score;

import java.util.Objects;

public class SetScore implements Score<Integer> {

    private static final String OUT_OF_BOUND = "State \"%d\" out of bounds. State must be between %d and %d.";
    private static final Integer LOWER_BOUND = 0;
    private static final Integer UPPER_BOUND = 7;

    private Integer state;

    public SetScore() {
        state = LOWER_BOUND;
    }

    @Override
    public void setState(Integer state) {
        if (state < LOWER_BOUND || state > UPPER_BOUND) {
            throw new IllegalArgumentException(OUT_OF_BOUND.formatted(state, LOWER_BOUND, UPPER_BOUND));
        } else {
            this.state = state;
        }
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public String toString() {
        return Integer.toString(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SetScore) {
            var other = (SetScore) obj;
            return Objects.equals(state, other.state);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }
}
