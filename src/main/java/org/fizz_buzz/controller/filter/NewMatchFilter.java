package org.fizz_buzz.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.controller.servlet.NewMatchServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = NewMatchServlet.URL_PATTERN)
public class NewMatchFilter extends HttpFilter {

    private static final String PARAMETER_MUST_BE_NO_LONGER_THAN = "Parameter %s must be no longer than %d";
    private static final String PARAMETER_MUST_NOT_BE_EMPTY = "Parameter %s must not be empty";
    private static final String PARAMETER_IS_OBSCENE = "Parameter %s is obscene that is not allowed";
    private static final String PARAMETERS_MUST_BE_UNIQUE = "Players must be unique";


    private static final String ERROR_MESSAGE_JSTL_PARAMETER = "msg";

    private static final String CREATE_NEW_MATCH_HTML = "/HTML/CreateMatch.jsp";

    private ParamValidator paramValidator = ParamValidator.getInstance();

    private List<String> validationErrors = new ArrayList<>();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().matches("POST")) {
           if (!isParametersValid(req, res))
           {
               return;
           }
        }

        chain.doFilter(req, res);
    }

    private boolean isParametersValid(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String firstPlayer = req.getParameter(NewMatchServlet.FIRST_PLAYER_PARAMETER);
        if (paramValidator.isEmpty(firstPlayer)) {
            validationErrors.add(PARAMETER_MUST_NOT_BE_EMPTY
                    .formatted(NewMatchServlet.FIRST_PLAYER_PARAMETER));
        }
        if (paramValidator.isLongerThan(15, firstPlayer)) {
            validationErrors.add(PARAMETER_MUST_BE_NO_LONGER_THAN
                    .formatted(NewMatchServlet.FIRST_PLAYER_PARAMETER, 15));
        }
        if (paramValidator.isObscene(firstPlayer)) {
            validationErrors.add(PARAMETER_IS_OBSCENE.
                    formatted(NewMatchServlet.FIRST_PLAYER_PARAMETER));
        }


        String secondPlayer = req.getParameter(NewMatchServlet.SECOND_PLAYER_PARAMETER);
        if (paramValidator.isEmpty(secondPlayer)) {
            validationErrors.add(PARAMETER_MUST_NOT_BE_EMPTY
                    .formatted(NewMatchServlet.SECOND_PLAYER_PARAMETER));
        }
        if (paramValidator.isLongerThan(15, secondPlayer)) {
            validationErrors.add(PARAMETER_MUST_BE_NO_LONGER_THAN
                    .formatted(NewMatchServlet.SECOND_PLAYER_PARAMETER, 15));
        }
        if (paramValidator.isObscene(secondPlayer)) {
            validationErrors.add(PARAMETER_IS_OBSCENE.
                    formatted(NewMatchServlet.SECOND_PLAYER_PARAMETER));
        }

        if (paramValidator.isNotUnique(firstPlayer, secondPlayer)) {
            validationErrors.add(PARAMETERS_MUST_BE_UNIQUE);
        }


        if (!validationErrors.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            validationErrors.forEach(
                    s -> errors.append(s)
                            .append(" \\n")
            );
            validationErrors.clear();

            req.setAttribute(ERROR_MESSAGE_JSTL_PARAMETER,  errors.toString());
            req.getRequestDispatcher(CREATE_NEW_MATCH_HTML).forward(req, res);

            return false;
        }
        else{
            return true;
        }
    }
}
