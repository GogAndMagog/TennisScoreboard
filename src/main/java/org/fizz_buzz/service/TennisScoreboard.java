package org.fizz_buzz.service;

import org.fizz_buzz.domain.tennis.match.TennisMatch;
import org.fizz_buzz.dto.PlayerScoreDTO;
import org.fizz_buzz.dto.TennisScoreboardDTO;


public class TennisScoreboard {
    private volatile static TennisScoreboard instance;

    private TennisScoreboard() {
    }

    public static TennisScoreboard getInstance() {
        if (instance == null) {
            synchronized (TennisScoreboard.class) {
                if (instance == null) {
                    instance = new TennisScoreboard();
                }
            }
        }
        return instance;
    }

    public TennisScoreboardDTO getScoreboard(TennisMatch match) {

        PlayerScoreDTO firstPlayer = new PlayerScoreDTO(match.getFirstPlayer().getName(),
                match.getFirstPlayer().getScore().toString(),
                match.getSet().getFirstPlayer().getScore().toString(),
                match.getSet().getGame().getFirstPlayer().getScore().toString());

        PlayerScoreDTO secondPlayer = new PlayerScoreDTO(match.getSecondPlayer().getName(),
                match.getSecondPlayer().getScore().toString(),
                match.getSet().getSecondPlayer().getScore().toString(),
                match.getSet().getGame().getSecondPlayer().getScore().toString());

        String winner = "";
        if (match.isFinished()){
            winner = match.getWinner();
        }

        return new TennisScoreboardDTO(match.getMatchId(), firstPlayer, secondPlayer, winner);
    }

}
