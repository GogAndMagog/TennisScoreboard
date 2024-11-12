package org.fizz_buzz.service.tennis;

import org.fizz_buzz.dto.PlayerScoreDTO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.service.tennis.match.TennisMatch;


public class TennisScoreboard {

    private final TennisMatch match;

    public TennisScoreboard(TennisMatch match) {
        this.match = match;
    }

    public TennisScoreboardDTO getScoreboard() {
        PlayerScoreDTO firstPlayer = new PlayerScoreDTO(match.getFirstPlayer().getName(),
                match.getFirstPlayer().getScore().toString(),
                match.getSet().getFirstPlayer().getScore().toString(),
                match.getSet().getGame().getFirstPlayer().getScore().toString());
        PlayerScoreDTO secondPlayer = new PlayerScoreDTO(match.getSecondPlayer().getName(),
                match.getSecondPlayer().getScore().toString(),
                match.getSet().getSecondPlayer().getScore().toString(),
                match.getSet().getGame().getSecondPlayer().getScore().toString());
        return new TennisScoreboardDTO(firstPlayer, secondPlayer);
    }

}
