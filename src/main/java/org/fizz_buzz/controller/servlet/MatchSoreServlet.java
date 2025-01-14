package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.OngoingMatchService;
import org.fizz_buzz.exception.SomethingNotFound;

import java.util.UUID;

import java.io.IOException;

@WebServlet(urlPatterns = MatchSoreServlet.URL_PATTERN)
public class MatchSoreServlet extends HttpServlet {

    public static final String URL_PATTERN = "/match-score";

    //req-parameters
    public static final String MATCH_UUID_PARAMETER = "uuid";
    public static final String PLAYER_NAME_PARAMETER = "playerName";

    //JSP-attributes
    private static final String TENNIS_SCOREBOARD_ATTRIBUTE = "tennisScoreboard";

    private static final String TENNIS_SCOREBOARD_PAGE = "/HTML/TennisScoreboard.jsp";
    private static final String FINISHED_MATCH_PAGE = "/HTML/FinishedMatch.jsp";
    private static final String MATCH_SCORE_PAGE = "/match-score";

    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        UUID matchId = UUID.fromString(req.getParameter(MATCH_UUID_PARAMETER));
        req.setAttribute(MATCH_UUID_PARAMETER, matchId);

        ongoingMatchService
                .getScoreboard(matchId)
                .ifPresentOrElse(scoreboard -> {
                            req.setAttribute(TENNIS_SCOREBOARD_ATTRIBUTE, scoreboard);
                            try {
                                if (scoreboard.winner().isEmpty()) {
                                    getServletContext().getRequestDispatcher(TENNIS_SCOREBOARD_PAGE)
                                            .forward(req, resp);
                                } else {
                                    getServletContext().getRequestDispatcher(FINISHED_MATCH_PAGE)
                                            .forward(req, resp);
                                }
                            }
                            catch (ServletException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            throw new SomethingNotFound("Match", matchId.toString());
                        });
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var matchId = UUID.fromString(req.getParameter(MATCH_UUID_PARAMETER));
        var playerName = req.getParameter(PLAYER_NAME_PARAMETER);

        ongoingMatchService.addScore(matchId, playerName);
        resp.sendRedirect(new StringBuilder()
                .append(req.getContextPath())
                .append(MATCH_SCORE_PAGE)
                .append("?")
                .append(MATCH_UUID_PARAMETER)
                .append("=")
                .append(matchId)
                .toString());
    }
}
