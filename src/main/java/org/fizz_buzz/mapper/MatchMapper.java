package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.MatchDTO;
import org.fizz_buzz.model.Match;

public class MatchMapper {
    public static MatchDTO convertModelToDTO(Match match)
    {
        return new MatchDTO(match.getId(),
                PlayerMapper.convertModelToDTO(match.getPlayer1()),
                PlayerMapper.convertModelToDTO(match.getPlayer2()),
                PlayerMapper.convertModelToDTO(match.getWinner()));
    }

}
