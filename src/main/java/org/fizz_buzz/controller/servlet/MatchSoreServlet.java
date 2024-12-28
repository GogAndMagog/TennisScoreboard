package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.controller.MatchScoreController;
import org.fizz_buzz.dao.MatchDAO;
import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.model.Match;

import java.util.UUID;

import java.io.IOException;

@WebServlet(urlPatterns = MatchSoreServlet.URL_PATTERN)
public class MatchSoreServlet extends HttpServlet {

    public static final String URL_PATTERN = "/match-score";

    //req-parameters
    private static final String MATCH_UUID_PARAMETER = "uuid";
    private static final String PLAYER_NAME_PARAMETER = "playerName";

    //JSP-attributes
    private static final String TENNIS_SCOREBOARD_ATTRIBUTE = "tennisScoreboard";

    private static final String TENNIS_SCOREBOARD_PAGE = "/HTML/TennisScoreboard.jsp";
    private static final String FINISHED_MATCH_PAGE = "/HTML/FinishedMatch.jsp";
    private static final String MATCH_SCORE_PAGE = "/match-score";

    private static final String ERROR_MATCH_NOT_FOUND = "Match with UUID=%s not found";
    private static final String CANNOT_SAVE_MATCH = "Cannot save match.";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        UUID matchId = UUID.fromString(req.getParameter(MATCH_UUID_PARAMETER));
        req.setAttribute(MATCH_UUID_PARAMETER, matchId);

        MatchScoreController.getInstance()
                .getScoreboard(matchId)
                .ifPresentOrElse(scoreboard -> {
                            req.setAttribute(TENNIS_SCOREBOARD_ATTRIBUTE, scoreboard);
                            try {
                                if (scoreboard.winner().isEmpty()) {
                                    getServletContext().getRequestDispatcher(TENNIS_SCOREBOARD_PAGE)
                                            .forward(req, resp);
                                } else {
                                    var playerDAO = PlayerDAO.getInstance();
                                    var matchDAO = MatchDAO.getInstance();

                                    var player1 = playerDAO.findByName(scoreboard.firstPlayer().name());
                                    var player2 = playerDAO.findByName(scoreboard.secondPlayer().name());
                                    var winner = playerDAO.findByName(scoreboard.winner());

                                    if (player1.isPresent() && player2.isPresent() && winner.isPresent()) {
                                        var match = Match.builder()
                                                .player1(player1.get())
                                                .player2(player2.get())
                                                .winner(winner.get())
                                                .uuid(matchId)
                                                .build();

                                        matchDAO.create(match);

                                        MatchScoreController.getInstance().deleteMatch(matchId);
                                    }
                                    else{
                                        throw new RuntimeException(CANNOT_SAVE_MATCH);
                                    }

                                    getServletContext().getRequestDispatcher(FINISHED_MATCH_PAGE)
                                            .forward(req, resp);
                                }
                            }
                            catch (ServletException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            throw new RuntimeException(ERROR_MATCH_NOT_FOUND.formatted(matchId.toString()));
                        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var matchId = UUID.fromString(req.getParameter(MATCH_UUID_PARAMETER));
        var playerName = req.getParameter(PLAYER_NAME_PARAMETER);

        MatchScoreController.getInstance().addScore(matchId, playerName);
        resp.sendRedirect(req.getContextPath()
                + MATCH_SCORE_PAGE
                + "?"
                + MATCH_UUID_PARAMETER
                + "="
                + matchId);
    }
}
