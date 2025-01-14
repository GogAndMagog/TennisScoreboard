package org.fizz_buzz.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.validation.ParamValidator;
import org.fizz_buzz.exception.EmptyParameterException;
import org.fizz_buzz.exception.UUIDValidationException;
import org.fizz_buzz.controller.servlet.MatchSoreServlet;

import java.io.IOException;

@WebFilter(MatchSoreServlet.URL_PATTERN)
public class MatchScoreFilter extends HttpFilter {

    private final ParamValidator paramValidator = ParamValidator.getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        switch (req.getMethod()) {
            case "GET":
                validateGetParameters(req, res);
                break;
            case "POST":
                validatePostParameters(req, res);
                break;
            default:
                break;
        }

        chain.doFilter(req, res);
    }

    private void validateGetParameters(HttpServletRequest req, HttpServletResponse res) {
        validateUUIDParameter(req, res);
    }

    private void validatePostParameters(HttpServletRequest req, HttpServletResponse res) {
        validateUUIDParameter(req, res);
        validatePlayerNameParameter(req, res);
    }

    private void validatePlayerNameParameter(HttpServletRequest req, HttpServletResponse res){
        String playerNameParameter = req.getParameter(MatchSoreServlet.PLAYER_NAME_PARAMETER);
        if (paramValidator.isEmpty(playerNameParameter)) {
            throw new EmptyParameterException(MatchSoreServlet.PLAYER_NAME_PARAMETER);
        }
    }

    private void validateUUIDParameter(HttpServletRequest req, HttpServletResponse res){
        String uuidParameter = req.getParameter(MatchSoreServlet.MATCH_UUID_PARAMETER);
        if (paramValidator.isEmpty(uuidParameter)) {
            throw new EmptyParameterException(MatchSoreServlet.MATCH_UUID_PARAMETER);
        }
        if (!paramValidator.isUUID(uuidParameter)) {
            throw new UUIDValidationException(uuidParameter);
        }
    }
}
