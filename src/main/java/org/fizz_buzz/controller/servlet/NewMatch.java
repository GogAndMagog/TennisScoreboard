package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.controller.ParameterLength;
import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.model.Player;
import org.fizz_buzz.service.tennis.MatchesService;

import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "/new-match")
public class NewMatch extends HttpServlet {
    public static final String FIRST_PLAYER_PARAMETER = "firstPlayer";
    public static final String SECOND_PLAYER_PARAMETER = "secondPlayer";
    public static final String MATCH_UUID_PARAMETER = "uuid";
    private static final String CREATE_MATCH_PAGE = "/HTML/CreateMatch.jsp";
    private static final String MATCH_SCORE_PAGE = "/match-score";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(CREATE_MATCH_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter(FIRST_PLAYER_PARAMETER);
        String secondPlayerName = req.getParameter(SECOND_PLAYER_PARAMETER);

        var playerDAO = PlayerDAO.getInstance();

        var firstPlayer = playerDAO.findByName(firstPlayerName);
        var secondPlayer = playerDAO.findByName(secondPlayerName);

        if (firstPlayer.isEmpty()) {
            playerDAO.create(new Player(firstPlayerName));
        }

        if (secondPlayer.isEmpty()) {
            playerDAO.create(new Player(secondPlayerName));
        }

        var matchID = MatchesService.getInstance().createMatch(firstPlayerName, secondPlayerName);
//        var params = req.getParameterMap();
//        params.put(MATCH_UUID_PARAMETER, new String[]{matchID.toString()});

//        req.setAttribute(MATCH_UUID_PARAMETER, matchID);
        resp.sendRedirect(req.getContextPath()
                + MATCH_SCORE_PAGE
                + "?"
                + MATCH_UUID_PARAMETER
                + "=" + matchID);
//
//        var matchId = MatchesService.getInstance().createMatch(firstPlayerName, secondPlayerName);
//        MatchesService.getInstance().addScoreFirstPlayer(matchId);
//        var tennisScoreboard = MatchesService.getInstance().getScoreboard(matchId);
//        tennisScoreboard.ifPresentOrElse(tennisScoreboardDTO ->
//                {
//                    req.setAttribute(TENNIS_SCOREBOARD_ATTRIBUTE, tennisScoreboard.get());
//                    try {
//                        getServletContext().getRequestDispatcher("/HTML/TennisScoreboard.jsp").forward(req, resp);
//                    } catch (ServletException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                },
//                () -> {
//                    throw new RuntimeException("Cannot create match");
//                }
//        );

    }
}