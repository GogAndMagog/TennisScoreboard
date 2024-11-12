package org.fizz_buzz.service.tennis.match;

import lombok.Getter;
import org.fizz_buzz.dto.PlayerScoreDTO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.service.tennis.PlayerInfo;
import org.fizz_buzz.service.tennis.Scorable;
import org.fizz_buzz.service.tennis.score.MatchScore;
import org.fizz_buzz.service.tennis.set.TennisSet;

@Getter
public class TennisMatch implements Scorable {

    private final PlayerInfo<MatchScore> firstPlayer;
    private final PlayerInfo<MatchScore> secondPlayer;
    private String winner;

    private TennisSet set;

    public TennisMatch(String firstPlayerName, String secondPlayerName) {
        firstPlayer = new PlayerInfo<>(firstPlayerName, new MatchScore());
        secondPlayer = new PlayerInfo<>(secondPlayerName, new MatchScore());
        set = new TennisSet(firstPlayerName, secondPlayerName);
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

    boolean isFinished() {
        return winner != null;
    }

    public TennisScoreboardDTO getScoreboard() {
        PlayerScoreDTO firstPlayer = new PlayerScoreDTO(this.firstPlayer.getName(),
                this.firstPlayer.getScore().toString(),
                getSet().getFirstPlayer().getScore().toString(),
                getSet().getGame().getFirstPlayer().getScore().toString());
        PlayerScoreDTO secondPlayer = new PlayerScoreDTO(this.secondPlayer.getName(),
                this.secondPlayer.getScore().toString(),
                getSet().getSecondPlayer().getScore().toString(),
                getSet().getGame().getSecondPlayer().getScore().toString());
        return new TennisScoreboardDTO(firstPlayer, secondPlayer);
    }


}
