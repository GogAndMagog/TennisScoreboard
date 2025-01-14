package org.fizz_buzz.service;

import org.fizz_buzz.dao.MatchDAO;
import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.dto.TennisScoreboardDTO;
import org.fizz_buzz.model.Match;
import org.fizz_buzz.model.Player;
import org.fizz_buzz.exception.SomethingNotFound;
import org.fizz_buzz.domain.tennis.match.TennisMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
    private final Map<UUID, TennisMatch> ongoingMatches;
    private final MatchDAO matchDAO = MatchDAO.getInstance();
    private final PlayerDAO playerDAO = PlayerDAO.getInstance();

    private volatile static OngoingMatchService instance;

    private OngoingMatchService() {
        ongoingMatches = new ConcurrentHashMap<>();
    }

    public static OngoingMatchService getInstance() {
        if (instance == null) {
            synchronized (OngoingMatchService.class) {
                if (instance == null) {
                    instance = new OngoingMatchService();
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

    public void addScore(UUID matchId, String playerName) {
        var match = ongoingMatches.get(matchId);
        match.addScore(playerName);
    }

    public Optional<TennisScoreboardDTO> getScoreboard(UUID matchId) {
        var match = ongoingMatches.get(matchId);
        if (match != null) {
            var scoreboard = Optional.ofNullable(TennisScoreboard.getInstance().getScoreboard(match));

            if (match.isFinished()) {
                processFinishedMatch(matchId);
            }

            return scoreboard;
        } else {
            return Optional.empty();
        }
    }

    private void processFinishedMatch(UUID matchId)
    {
        var match = ongoingMatches.get(matchId);

        var firstPlayerName = match.getFirstPlayer().getName();
        var secondPlayerName = match.getSecondPlayer().getName();

        var firstPlayer = playerDAO.findByName(firstPlayerName);
        if (firstPlayer.isEmpty()) {
            throw new SomethingNotFound("Player", firstPlayerName);
        }

        var secondPlayer = playerDAO.findByName(match.getSecondPlayer().getName());
        if (secondPlayer.isEmpty()) {
            throw new SomethingNotFound("Player", secondPlayerName);
        }

        Player winner;

        if (firstPlayerName.equals(match.getWinner())) {
            winner = firstPlayer.get();
        } else {
            winner = secondPlayer.get();
        }

        matchDAO.create(Match.builder()
                .player1(firstPlayer.get())
                .player2(secondPlayer.get())
                .winner(winner)
                .uuid(match.getMatchId())
                .build());

        ongoingMatches.remove(matchId);
    }
}
