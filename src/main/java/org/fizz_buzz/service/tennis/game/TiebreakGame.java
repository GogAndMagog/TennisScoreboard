package org.fizz_buzz.service.tennis.game;

import org.fizz_buzz.service.tennis.PlayerInfo;
import org.fizz_buzz.service.tennis.score.TiebreakGameScore;

public class TiebreakGame extends TennisGame<TiebreakGameScore> {

    private static final int WIN_PRECONDITION = 7;

    public TiebreakGame(String firstPlayerName, String secondPlayerName) {
        this.firstPlayer = new PlayerInfo<>(firstPlayerName, new TiebreakGameScore());
        this.secondPlayer = new PlayerInfo<>(secondPlayerName, new TiebreakGameScore());
    }

    @Override
    public void addScoreFirstPlayer() {
        addScore(firstPlayer, secondPlayer);
    }

    @Override
    public void addScoreSecondPlayer() {
        addScore(secondPlayer, firstPlayer);
    }

    public void addScore(PlayerInfo<TiebreakGameScore> player, PlayerInfo<TiebreakGameScore> opponent) {
        if (isFinished()) {
            return;
        }

        var score = player.getScore();
        var opponentScore = opponent.getScore();

        addScore(score);
        if (WIN_PRECONDITION <= score.getState()
                && (Math.abs(score.getState() - opponentScore.getState()) >= 2)) {
            winner = player.getName();
        }
    }

    private void addScore(TiebreakGameScore score) {
        score.setState(score.getState() + 1);
    }
}
