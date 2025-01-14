package org.fizz_buzz.domain.tennis.score;

import lombok.NoArgsConstructor;
import org.fizz_buzz.domain.tennis.score.state.StandardGameScoreState;

import java.util.Objects;

@NoArgsConstructor
public class StandardGameScore implements Score<StandardGameScoreState> {
    private StandardGameScoreState state = StandardGameScoreState.ZERO;

    @Override
    public void setState(StandardGameScoreState state) {
        this.state = state;
    }

    @Override
    public StandardGameScoreState getState() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof StandardGameScore) {
            return this.state.equals(((StandardGameScore) obj).state);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
