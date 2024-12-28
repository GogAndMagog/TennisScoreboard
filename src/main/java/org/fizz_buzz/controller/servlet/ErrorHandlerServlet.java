package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    private static final String STATUS_CODE = "statusCode";
    private static final String ERROR_MESSAGE = "errorMsg";

    private static final String ERROR_PAGE = "/HTML/ErrorPage.jsp";

    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong";


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable error = (Throwable) req.getAttribute("jakarta.servlet.error.exception");

        switch (error) {
            case IllegalArgumentException e:
                req.setAttribute(STATUS_CODE, HttpServletResponse.SC_BAD_REQUEST);
                req.setAttribute(ERROR_MESSAGE, error.getMessage());
                break;
            default:
                req.setAttribute(ERROR_MESSAGE, DEFAULT_ERROR_MESSAGE);
                req.setAttribute(STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
    }

}
