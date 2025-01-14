package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.dao.MatchDAO;
import org.fizz_buzz.dto.MatchDTO;
import org.fizz_buzz.dto.PaginationDTO;
import org.fizz_buzz.exception.PageNotExistException;
import org.fizz_buzz.mapper.MatchMapper;
import org.fizz_buzz.model.Match;
import org.fizz_buzz.validation.DomainSpecificValidator;
import org.fizz_buzz.validation.ParamValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = MatchesServlet.URL_PATTERN)
public class MatchesServlet extends HttpServlet {

    public static final String URL_PATTERN = "/matches";

    //req-parameters
    public static final String PLAYER_NAME_PARAMETER = "playerName";
    public static final String PAGE_NUMBER_PARAMETER = "pageNumber";

    //JSP-attributes
    private static final String MATCHES_ATTRIBUTE = "matches";
    private static final String PAGINATION_ATTRIBUTE = "pagination";

    private static final String MATCHES_PAGE = "/HTML/Matches.jsp";

    private final ParamValidator paramValidator = ParamValidator.getInstance();
    private final DomainSpecificValidator domainSpecificValidator = DomainSpecificValidator.getInstance();

    private final MatchMapper matchMapper = MatchMapper.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var playerName = req.getParameter(PLAYER_NAME_PARAMETER);
        var pageNumberString = req.getParameter(PAGE_NUMBER_PARAMETER);

        int pageNumber = 1;

        if (!paramValidator.isEmpty(pageNumberString)) {
            pageNumber = Integer.parseInt(pageNumberString);
        }
        if (!domainSpecificValidator.isMatchPageExists(pageNumber)) {
            throw new PageNotExistException(pageNumber);
        }


        var matchesDAO = MatchDAO.getInstance();

        List<Match> matches;
        int totalPages;

        if (paramValidator.isEmpty(playerName)) {
            matches = matchesDAO.findAll(pageNumber);
            totalPages = matchesDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE);
        } else {
            playerName = playerName.trim().replaceAll("\\s+", " ");
            matches = matchesDAO.findByName(playerName, pageNumber);
            totalPages = matchesDAO.totalPages(MatchDAO.DEFAULT_PAGE_SIZE, playerName);
        }

        List<MatchDTO> matchDTOS = new ArrayList<>();
        for (Match match : matches) {
            matchDTOS.add(matchMapper.toDTO(match));
        }

        var pagination = new PaginationDTO(pageNumber, totalPages);

        req.setAttribute(MATCHES_ATTRIBUTE, matchDTOS);
        req.setAttribute(PAGINATION_ATTRIBUTE, pagination);

        getServletContext().getRequestDispatcher(MATCHES_PAGE)
                .forward(req, resp);
    }

}
