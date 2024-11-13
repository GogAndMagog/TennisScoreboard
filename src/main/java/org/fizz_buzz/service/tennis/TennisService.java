package org.fizz_buzz.service.tennis;

import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.service.tennis.match.TennisMatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TennisService {
    private final Map<UUID, TennisMatch> matches;
    private volatile static TennisService instance = new TennisService();

    private TennisService() {
        matches = new HashMap<>();
    }

    public static TennisService getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new TennisService();
                }
            }
        }
        return instance;
    }

    public UUID createMatch(String firstPlayer, String secondPlayer) {
        var id = UUID.randomUUID();
        matches.put(id, new TennisMatch(firstPlayer, secondPlayer));

        return id;
    }

    public void deleteMatch(UUID id) {
        matches.remove(id);
    }

    public void addScoreFirstPlayer(UUID id) {
        var match = matches.get(id);
        match.addScoreFirstPlayer();
    }

    public void addScoreSecondPlayer(UUID id) {
        var match = matches.get(id);
        match.addScoreSecondPlayer();
    }

    public Optional<TennisScoreboardDTO> getScoreboard(UUID matchId) {
        var match = matches.get(matchId);
        if (match != null) {
            return Optional.ofNullable(TennisScoreboard.getInstance().getScoreboard(match));
        } else {
            return Optional.empty();
        }
    }
}
