package org.fizz_buzz.service.tennis.game;

import org.fizz_buzz.service.tennis.PlayerInfo;
import org.fizz_buzz.service.tennis.score.StandardGameScore;
import org.fizz_buzz.service.tennis.score.state.StandardGameScoreState;

import java.util.Optional;

public class StandardGame extends TennisGame<StandardGameScore> {

    public StandardGame(String firstPlayerName, String secondPlayerName) {
        this.firstPlayer = new PlayerInfo<>(firstPlayerName, new StandardGameScore());
        this.secondPlayer = new PlayerInfo<>(secondPlayerName, new StandardGameScore());
    }

    @Override
    public void addScoreFirstPlayer() {
        addScore(firstPlayer, secondPlayer);
    }

    @Override
    public void addScoreSecondPlayer() {
        addScore(secondPlayer, firstPlayer);
    }

    private void addScore(PlayerInfo<StandardGameScore> player, PlayerInfo<StandardGameScore> opponent) {
        if (isFinished()) {
            return;
        }

        var score = player.getScore();
        var opponentScore = opponent.getScore();

        if (score.getState() == StandardGameScoreState.ADVANTAGE) {
            addScore(score);
            winner = player.getName();
            return;
        }

        if (opponentScore.getState() == StandardGameScoreState.DEUCE) {
            addScore(score);
            removeScore(opponentScore);
            return;
        }

        if (score.getState() == StandardGameScoreState.FORTY
                && opponentScore.getState() == StandardGameScoreState.FORTY) {
            addScore(score);
            addScore(opponentScore);
            return;
        }

        if (score.getState() == StandardGameScoreState.FORTY
                && opponentScore.getState() == StandardGameScoreState.ADVANTAGE) {
            addScore(score);
            removeScore(opponentScore);
            return;
        }

        addScore(score);

        if (score.getState() == StandardGameScoreState.FORTY
                && opponentScore.getState() == StandardGameScoreState.FORTY) {
            addScore(score);
            addScore(opponentScore);
            return;
        }

        if (score.getState() == StandardGameScoreState.DEUCE
                && !(opponentScore.getState() == StandardGameScoreState.FORTY)) {
            addScore(score);
            addScore(score);
            winner = player.getName();
        }
    }

    private void addScore(StandardGameScore score) {
        score.setState(StandardGameScoreState.values()[score.getState().ordinal() + 1]);
    }

    private void removeScore(StandardGameScore score) {
        score.setState(StandardGameScoreState.values()[score.getState().ordinal() - 1]);
    }

    @Override
    public boolean isFinished() {
        return winner != null;
    }

    @Override
    public Optional<String> getWinner() {
        return Optional.ofNullable(winner);
    }
}
