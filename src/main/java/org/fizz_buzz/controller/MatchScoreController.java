package org.fizz_buzz.controller;

import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.service.tennis.TennisScoreboard;
import org.fizz_buzz.service.tennis.match.TennisMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchScoreController {

    private static final String MATCH_NOT_FOUND = "Match with id: %s not found";

    private final Map<UUID, TennisMatch> ongoingMatches;

    private volatile static MatchScoreController instance = new MatchScoreController();

    private MatchScoreController() {
        ongoingMatches = new ConcurrentHashMap<>();
    }

    public static MatchScoreController getInstance() {
        if (instance == null) {
            synchronized (PlayerDAO.class) {
                if (instance == null) {
                    instance = new MatchScoreController();
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

    public void addScore(UUID matchId, String playerName){
        var match = ongoingMatches.get(matchId);

        if (match != null) {
            match.addScore(playerName);
        }
        else {
            throw new IllegalArgumentException(MATCH_NOT_FOUND.formatted(matchId.toString()));
        }
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
