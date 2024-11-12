package org.fizz_buzz.service.tennis.set;

import lombok.Getter;
import org.fizz_buzz.service.tennis.PlayerInfo;
import org.fizz_buzz.service.tennis.Scorable;
import org.fizz_buzz.service.tennis.game.StandardGame;
import org.fizz_buzz.service.tennis.game.TennisGame;
import org.fizz_buzz.service.tennis.game.TiebreakGame;
import org.fizz_buzz.service.tennis.score.SetScore;

import java.util.Optional;

@Getter
public class TennisSet implements Scorable {

    private final PlayerInfo<SetScore> firstPlayer;
    private final PlayerInfo<SetScore> secondPlayer;

    private String winner;
    private TennisGame game;

    public TennisSet(String firstPlayerName, String secondPlayerName) {
        firstPlayer = new PlayerInfo<>(firstPlayerName, new SetScore());
        secondPlayer = new PlayerInfo<>(secondPlayerName, new SetScore());

        game = new StandardGame(firstPlayerName, secondPlayerName);
    }

    @Override
    public void addScoreFirstPlayer() {
        game.addScoreFirstPlayer();
        if (game.isFinished()) {
            addScore(firstPlayer, secondPlayer);
        }
    }

    @Override
    public void addScoreSecondPlayer() {
        game.addScoreFirstPlayer();
        if (game.isFinished()) {
            addScore(secondPlayer, firstPlayer);
        }
    }

    private void addScore(PlayerInfo<SetScore> player, PlayerInfo<SetScore> opponent) {
        addScore(player.getScore());
        if (checkWin(player, opponent)) {
            winner = player.getName();
        } else if (player.getScore().getState() == 6
                && secondPlayer.getScore().getState() == 6) {
            game = new TiebreakGame(player.getName(), opponent.getName());
        } else {
            game = new StandardGame(player.getName(), opponent.getName());
        }
    }

    public boolean isFinished() {
        return winner != null;
    }

    private boolean checkWin(PlayerInfo<SetScore> player, PlayerInfo<SetScore> opponent) {
        return player.getScore().getState() == 7 ||
                (player.getScore().getState() == 6 &&
                        Math.abs(opponent.getScore().getState() - player.getScore().getState()) >= 2);
    }

    private void addScore(SetScore score) {
        score.setState(score.getState() + 1);
    }

    public Optional<String> getWinner() {
        return Optional.ofNullable(winner);
    }

}
