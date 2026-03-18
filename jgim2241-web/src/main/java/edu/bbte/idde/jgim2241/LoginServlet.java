package edu.bbte.idde.jgim2241;

import com.github.jknack.handlebars.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(HandlebarsContactServlet.class);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET /login");
        Template template = HandlebarsTemplateFactory.getTemplate("login");
        template.apply(new Object(), resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST /login");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            req.getSession().setAttribute("loggedIn", true);
            resp.sendRedirect(req.getContextPath() + "/contact-list");
        } else {
            Template template = HandlebarsTemplateFactory.getTemplate("login");
            template.apply(new Object(), resp.getWriter());
        }
    }
}
