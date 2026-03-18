package edu.bbte.idde.jgim2241;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(filterName = "LoggedInFilter", urlPatterns = {"/contact-list"})
public class LoggedInFilter extends HttpFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoggedInFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LOG.info("{} {}", req.getMethod(), req.getRequestURI());
        Boolean loggedIn = (Boolean) req.getSession().getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}
