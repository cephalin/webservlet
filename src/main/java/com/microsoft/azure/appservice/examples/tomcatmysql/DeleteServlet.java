package com.microsoft.azure.appservice.examples.tomcatmysql;

import java.io.IOException;
import java.util.logging.Logger;

import com.microsoft.azure.appservice.examples.tomcatmysql.models.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(DeleteServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("POST /delete");

        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            try {
                StudentDAO.delete(id);
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
            throw new ServletException("Error: id parameter missing.");
        }
    }
}
