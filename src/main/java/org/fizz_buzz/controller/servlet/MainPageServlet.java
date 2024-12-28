package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = MainPageServlet.URL_PATTERN)
public class MainPageServlet extends HttpServlet {

    public static final String URL_PATTERN = "";

    private static final String MAIN_PAGE_URL = "/HTML/MainPage.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(MAIN_PAGE_URL).forward(req, resp);
    }
}
