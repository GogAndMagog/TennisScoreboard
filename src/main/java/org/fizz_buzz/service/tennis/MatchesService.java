package org.fizz_buzz.service.tennis;

import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.service.tennis.match.TennisMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchesService {
    private final Map<UUID, TennisMatch> ongoingMatches;

    private volatile static MatchesService instance = new MatchesService();

    private MatchesService() {
        ongoingMatches = new ConcurrentHashMap<>();
    }

    public static MatchesService getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new MatchesService();
                }
            }
        }
        return instance;
    }

    public UUID createMatch(String firstPlayer, String secondPlayer) {
        var id = UUID.randomUUID();
        ongoingMatches.put(id, new TennisMatch(id, firstPlayer, secondPlayer));

        return id;
    }

    public void deleteMatch(UUID id) {
        ongoingMatches.remove(id);
    }

    public void addScoreFirstPlayer(UUID id) {
        var match = ongoingMatches.get(id);
        match.addScoreFirstPlayer();
    }

    public void addScoreSecondPlayer(UUID id) {
        var match = ongoingMatches.get(id);
        match.addScoreSecondPlayer();
    }

    public Optional<TennisScoreboardDTO> getScoreboard(UUID matchId) {
        var match = ongoingMatches.get(matchId);
        if (match != null) {
            return Optional.ofNullable(TennisScoreboard.getInstance().getScoreboard(match));
        } else {
            return Optional.empty();
        }
    }
}
