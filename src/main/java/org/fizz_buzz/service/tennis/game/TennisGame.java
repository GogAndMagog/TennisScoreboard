package org.fizz_buzz.service.tennis.game;

import lombok.Getter;
import lombok.NonNull;
import org.fizz_buzz.service.tennis.PlayerInfo;
import org.fizz_buzz.service.tennis.Scorable;
import org.fizz_buzz.service.tennis.score.Score;

import java.util.Optional;

@Getter
public abstract class TennisGame<T extends Score> implements Scorable<T> {

    private static final String NO_SUCH_PLAYER = "No such player: %s";
    private static final String SEVERAL_PLAYERS_WITH_SAME_NAME = "Several players with same name: %s";

    @NonNull
    protected PlayerInfo<T> firstPlayer;
    @NonNull
    protected PlayerInfo<T> secondPlayer;

    protected String winner;

    public boolean isFinished() {
        return winner != null;
    }

    public Optional<String> getWinner() {
        return Optional.ofNullable(winner);
    }
}
