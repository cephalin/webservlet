package com.microsoft.azure.appservice.examples.tomcatmysql;

import java.io.IOException;
import java.util.List;
// import java.util.logging.Level;
// import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.azure.appservice.examples.tomcatmysql.models.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/")
public class ViewServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(ViewServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //logger.setLevel(Level.ALL);

        logger.info("GET / foobar");
        List<Student> students = StudentDAO.getAll();
        req.setAttribute("studentRecords", students);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/viewStudents.jsp");
        dispatcher.forward(req, resp);
    }
}
