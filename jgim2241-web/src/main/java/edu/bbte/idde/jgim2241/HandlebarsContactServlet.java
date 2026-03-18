package edu.bbte.idde.jgim2241;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.service.ServiceException;
import edu.bbte.idde.jgim2241.service.impl.ContactServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "HandlebarsContactServlet", urlPatterns = {"/contact-list"})
public class HandlebarsContactServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(HandlebarsContactServlet.class);
    private final ContactServiceImpl contactService = new ContactServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOG.info("contact servlet initialization");
    }
    
    @Override
    public void destroy() {
        super.destroy();
        LOG.info("destroying handlebars contact servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET /contact-list");
        try {
            Collection<Contact> contacts = contactService.findAll();
            Template template = HandlebarsTemplateFactory.getTemplate("contacts");
            template.apply(contacts, resp.getWriter());
        } catch (ServiceException e) {
            LOG.error("error fetch contacts");
            Template template = HandlebarsTemplateFactory.getTemplate("error");
            String errorMessage = "Error fetching contacts";
            resp.setStatus(500);
            template.apply(errorMessage, resp.getWriter());
        }
    }
}
