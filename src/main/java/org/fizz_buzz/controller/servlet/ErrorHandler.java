package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;

import java.io.IOException;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandler extends HttpServlet {

    private static final String STATUS_CODE = "statusCode";
    private static final String ERROR_MESSAGE = "errorMsg";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable error = null;
        try {
            error = (Throwable) req
                    .getAttribute("jakarta.servlet.error.exception");
        }
        catch (HibernateException | ExceptionInInitializerError e) {
            throw new RuntimeException(e);
        }

        switch (error) {
            case IllegalArgumentException e:
                req.setAttribute(STATUS_CODE, HttpServletResponse.SC_BAD_REQUEST);
                break;
            default:
                req.setAttribute(STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        req.setAttribute(ERROR_MESSAGE, error.getMessage());

        req.getRequestDispatcher("/HTML/ErrorPage.jsp").forward(req, resp);
    }

}
