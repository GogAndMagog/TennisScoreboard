package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.tennis.MatchesService;

import java.util.UUID;

import java.io.IOException;

@WebServlet(urlPatterns = "/match-score")
public class MatchSoreServlet extends HttpServlet {

    private static final String MATCH_UUID_PARAMETER = "uuid";
    private static final String TENNIS_SCOREBOARD_ATTRIBUTE = "tennisScoreboard";
    private static final String TENNIS_SCOREBOARD_PAGE = "/HTML/TennisScoreboard.jsp";
    private static final String ERROR_MATCH_NOT_FOUND = "Match with UUID=%s not found";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter(MATCH_UUID_PARAMETER));
        if (matchId == null) {
            matchId = UUID.fromString(req.getAttribute(MATCH_UUID_PARAMETER).toString());
        }

        MatchesService.getInstance()
                .getScoreboard(matchId)
                .ifPresentOrElse(scoreboard -> {
                            req.setAttribute(TENNIS_SCOREBOARD_ATTRIBUTE, scoreboard);
                            try {
                                getServletContext().getRequestDispatcher(TENNIS_SCOREBOARD_PAGE)
                                        .forward(req, resp);
                            } catch (ServletException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            throw new RuntimeException(ERROR_MATCH_NOT_FOUND);
                        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
