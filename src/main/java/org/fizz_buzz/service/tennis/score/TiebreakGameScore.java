package org.fizz_buzz.service.tennis.score;

public class TiebreakGameScore implements Score<Integer> {

    private static final String MUST_BE_POSITIVE = "State must be positive integer value.";
    private static final int LOWER_BOUND = 0;

    private Integer state = 0;

    @Override
    public void setState(Integer state) {
        if (state < LOWER_BOUND) {
            throw new IllegalArgumentException(MUST_BE_POSITIVE);
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
}
