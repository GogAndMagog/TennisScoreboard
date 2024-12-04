package org.fizz_buzz.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.controller.ParameterLength;
import org.fizz_buzz.controller.servlet.NewMatch;

import java.io.IOException;

@WebFilter(urlPatterns = "/new-match")
public class NewMatchFilter extends HttpFilter {

    private static final ParameterLength[] PARAMETER_MAX_LENGTHS = {
            new ParameterLength(NewMatch.FIRST_PLAYER_PARAMETER, 15),
            new ParameterLength(NewMatch.SECOND_PLAYER_PARAMETER, 15)};

    private static final ParameterLength[] PARAMETER_EXACT_LENGTHS = {
            new ParameterLength(NewMatch.MATCH_UUID_PARAMETER, 36)};

    private static final String PARAMETER_MUST_BE_NO_LONGER_THAN = "Parameter %s must be no longer than %d";
    private static final String UUID_MUST_BE_THAT_LENGTH = "UUID must be %d characters";
    private static final String ERROR_MESSAGE_PARAMETER = "msg";
    private static final String CREATE_NEW_MATCH_HTML = "/HTML/CreateMatch.jsp";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().matches("POST")) {
            if (!checkMaxLengths(req, res)) {
                return;
            }
        }

        chain.doFilter(req, res);
    }

    private boolean checkMaxLengths(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        for (ParameterLength parameterMaxLength : PARAMETER_MAX_LENGTHS) {
            var parameterValue = req.getParameter(parameterMaxLength.parameterName());
            if (parameterValue.length() > parameterMaxLength.parameterLength()) {
                req.setAttribute(ERROR_MESSAGE_PARAMETER, PARAMETER_MUST_BE_NO_LONGER_THAN
                        .formatted(parameterMaxLength.parameterName()
                                , parameterMaxLength.parameterLength()));
                req.getRequestDispatcher(CREATE_NEW_MATCH_HTML).forward(req, res);
                return false;
            }
        }
        return true;
    }

    private void checkExactLengths(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        for (ParameterLength parameterExactLength : PARAMETER_EXACT_LENGTHS) {
            var parameterValue = req.getParameter(parameterExactLength.parameterName());
            if (parameterValue.length() != parameterExactLength.parameterLength()) {
                req.setAttribute(ERROR_MESSAGE_PARAMETER, PARAMETER_MUST_BE_NO_LONGER_THAN
                        .formatted(parameterExactLength.parameterName()
                                , parameterExactLength.parameterLength()));
                req.getRequestDispatcher(CREATE_NEW_MATCH_HTML).forward(req, res);
            }
        }
    }
}
