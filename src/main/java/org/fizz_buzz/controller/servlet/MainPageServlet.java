package org.fizz_buzz.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;

import java.io.IOException;

@WebServlet(urlPatterns = {""})
public class MainPageServlet extends HttpServlet {

    private static final String MAIN_PAGE_URL = "/HTML/MainPage.shtml";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(MAIN_PAGE_URL).forward(req, resp);
    }
}
