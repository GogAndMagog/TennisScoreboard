package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.dao.PlayerDAO;
import org.fizz_buzz.model.Player;
import org.fizz_buzz.service.OngoingMatchService;

import java.io.IOException;

@WebServlet(urlPatterns = NewMatchServlet.URL_PATTERN)
public class NewMatchServlet extends HttpServlet {

    public static final String URL_PATTERN = "/new-match";

    //req-parameters
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
        String firstPlayerName = req.getParameter(FIRST_PLAYER_PARAMETER).replaceAll("\\s+", " ");
        String secondPlayerName = req.getParameter(SECOND_PLAYER_PARAMETER).replaceAll("\\s+", " ");

        var playerDAO = PlayerDAO.getInstance();

        var firstPlayer = playerDAO.findByName(firstPlayerName);
        var secondPlayer = playerDAO.findByName(secondPlayerName);

        if (firstPlayer.isEmpty()) {
            playerDAO.create(new Player(firstPlayerName));
        }

        if (secondPlayer.isEmpty()) {
            playerDAO.create(new Player(secondPlayerName));
        }

        var matchID = OngoingMatchService.getInstance().createMatch(firstPlayerName, secondPlayerName);

        resp.sendRedirect(req.getContextPath()
                + MATCH_SCORE_PAGE
                + "?"
                + MATCH_UUID_PARAMETER
                + "=" + matchID);

    }
}