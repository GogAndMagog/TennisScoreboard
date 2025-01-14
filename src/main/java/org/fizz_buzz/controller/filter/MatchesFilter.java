package org.fizz_buzz.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.controller.servlet.MatchesServlet;
import org.fizz_buzz.validation.ParamValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = MatchesServlet.URL_PATTERN)
public class MatchesFilter extends HttpFilter {

    private static final String PAGE_NUMBER_MUST_BE_INTEGER = "Page number must be an integer";
    private static final String PAGE_NUMBER_MUST_BE_POSITIVE = "Page number must be positive";

    private static final String ERROR_MESSAGE_JSTL_PARAMETER = "msg";

    private static final String MATCHES_PAGE = "/HTML/Matches.jsp";

    private final ParamValidator paramValidator = ParamValidator.getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().equals("GET")) {
            if (!validateParameters(req, res)) {
                return;
            }
        }

        chain.doFilter(req, res);
    }

    public boolean validateParameters(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<String> validationErrors = new ArrayList<>();

        String pageNumber = req.getParameter(MatchesServlet.PAGE_NUMBER_PARAMETER);
        String playerName = req.getParameter(MatchesServlet.PLAYER_NAME_PARAMETER);

        if (!paramValidator.isEmpty(pageNumber)) {
            if (!paramValidator.isInteger(pageNumber)) {
                validationErrors.add(PAGE_NUMBER_MUST_BE_INTEGER);
            }
            if (paramValidator.isNegative(pageNumber)) {
                validationErrors.add(PAGE_NUMBER_MUST_BE_POSITIVE);
            }
        }

        if (!validationErrors.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            validationErrors.forEach(
                    s -> errors.append(s)
                            .append(" \\n")
            );
            validationErrors.clear();

            req.setAttribute(ERROR_MESSAGE_JSTL_PARAMETER, errors.toString());
            req.getRequestDispatcher(MATCHES_PAGE).forward(req, res);

            return false;
        } else {
            return true;
        }
    }
}
