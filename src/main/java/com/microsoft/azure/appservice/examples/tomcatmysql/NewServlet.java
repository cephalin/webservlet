package com.microsoft.azure.appservice.examples.tomcatmysql;

import java.io.IOException;
import java.util.logging.Logger;

import com.microsoft.azure.appservice.examples.tomcatmysql.models.StudentDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/create")
public class NewServlet extends HttpServlet  {

    private static Logger logger = Logger.getLogger(NewServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET /create");
    
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/newStudent.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("POST /create");

        String name = req.getParameter("name");
        String stdStr = req.getParameter("std");


        if (name != null && stdStr != null) {
            int std = Integer.parseInt(stdStr);
            try {
                StudentDAO.create(name, std);
            } catch (Exception e) {
                throw e;
            }

            String path = req.getContextPath();
            if(path != "") {
                resp.sendRedirect(path);
            } else {
                resp.sendRedirect("/");
            }
        } else {
            throw new ServletException("Error: parameters missing.");
        }

    }
}
