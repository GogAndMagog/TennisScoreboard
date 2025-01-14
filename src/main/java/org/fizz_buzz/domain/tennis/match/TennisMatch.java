package org.fizz_buzz.domain.tennis.match;

import lombok.Getter;
import org.fizz_buzz.domain.tennis.PlayerInfo;
import org.fizz_buzz.domain.tennis.Scorable;
import org.fizz_buzz.domain.tennis.score.MatchScore;
import org.fizz_buzz.domain.tennis.set.TennisSet;

import java.util.UUID;

@Getter
public class TennisMatch implements Scorable {

    private final UUID matchId;
    private final PlayerInfo<MatchScore> firstPlayer;
    private final PlayerInfo<MatchScore> secondPlayer;
    private String winner;

    private TennisSet set;

    public TennisMatch(UUID matchId, String firstPlayerName, String secondPlayerName) {
        this.matchId = matchId;
        firstPlayer = new PlayerInfo<>(firstPlayerName, new MatchScore());
        secondPlayer = new PlayerInfo<>(secondPlayerName, new MatchScore());
        set = new TennisSet(firstPlayerName, secondPlayerName);
    }

    public void addScore(String playerName){
        if (playerName.equals(firstPlayer.getName())){
            addScoreFirstPlayer();
        }
        else if (playerName.equals(secondPlayer.getName())){
            addScoreSecondPlayer();
        }
        else{
            throw new IllegalArgumentException("Player " + playerName + " is not a valid player");
        }
    }

    @Override
    public void addScoreFirstPlayer() {
        if (isFinished()) {
            return;
        }
        set.addScoreFirstPlayer();
        addScore(firstPlayer, secondPlayer);
    }

    @Override
    public void addScoreSecondPlayer() {
        if (isFinished()) {
            return;
        }
        set.addScoreSecondPlayer();
        addScore(secondPlayer, firstPlayer);
    }

    private void addScore(PlayerInfo<MatchScore> player, PlayerInfo<MatchScore> opponent) {
        if (set.isFinished()) {
            player.getScore().setState(player.getScore().getState() + 1);
            if (checkWinConditions(player)) {
                winner = player.getName();
            }
            set = new TennisSet(player.getName(), opponent.getName());
        }
    }

    private boolean checkWinConditions(PlayerInfo<MatchScore> player) {
        return player.getScore().getState() == 2;
    }

    public boolean isFinished() {
        return winner != null;
    }
}
