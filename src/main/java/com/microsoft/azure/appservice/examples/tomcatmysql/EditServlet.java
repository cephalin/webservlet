package com.microsoft.azure.appservice.examples.tomcatmysql;

import java.io.IOException;
import java.util.logging.Logger;

import com.microsoft.azure.appservice.examples.tomcatmysql.models.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/edit")
public class EditServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(EditServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET /edit");

        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            Student student = StudentDAO.getById(id);
            req.setAttribute("studentRecord", student);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/editStudent.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("POST /edit");

        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String stdStr = req.getParameter("std");

        if (idStr != null && name != null && stdStr != null) {
            Long id = Long.parseLong(idStr);
            int std = Integer.parseInt(stdStr);
            try {
                StudentDAO.update(id, name, std);
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
