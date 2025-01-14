package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.MatchDTO;
import org.fizz_buzz.model.Match;

public class MatchMapper {
    private PlayerMapper playerMapper = PlayerMapper.getInstance();

    private volatile static MatchMapper instance;

    private MatchMapper() {
    }

    public static MatchMapper getInstance() {
        if (instance == null) {
            synchronized (MatchMapper.class) {
                if (instance == null) {
                    instance = new MatchMapper();
                }
            }
        }
        return instance;
    }

    public MatchDTO toDTO(Match match)
    {
        return new MatchDTO(match.getId(),
                playerMapper.convertModelToDTO(match.getPlayer1()),
                playerMapper.convertModelToDTO(match.getPlayer2()),
                playerMapper.convertModelToDTO(match.getWinner()));
    }
}
