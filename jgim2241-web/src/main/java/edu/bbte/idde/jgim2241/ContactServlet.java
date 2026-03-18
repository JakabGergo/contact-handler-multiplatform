package edu.bbte.idde.jgim2241;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet(name = "ContactServlet", urlPatterns = {"/contacts"})
public class ContactServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ContactServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ContactServiceImpl contactService = new ContactServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOG.info("contact servlet initialization");
    }

    @Override
    public void destroy() {
        super.destroy();
        LOG.info("destroying contact servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET /contacts");
        resp.setHeader("Content-Type", "application/json");

        String idParam = req.getParameter("id");

        try {
            //getAll
            if (idParam == null) {
                Collection<Contact> contacts = contactService.findAll();
                objectMapper.writeValue(resp.getOutputStream(), contacts);
            } else {
                //getByID
                Long id = Long.parseLong(idParam);
                Contact contact = contactService.findByID(id);
                if (contact == null) {
                    handleError(resp, "Contact not found", 404);
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), contact);
                }
            }
        } catch (ServiceException e) {
            handleError(resp, "Error fetch contact", 500);
        } catch (NumberFormatException e) {
            handleError(resp, "Invalid ID format", 400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST /contacts");
        resp.setHeader("Content-Type", "application/json");

        Contact contactInput;
        try {
            contactInput = objectMapper.readValue(req.getInputStream(), Contact.class);
        } catch (JsonMappingException e) {
            handleError(resp, "Invalid JSON format", 400);
            return;
        }
        LOG.info("Recieved contact input:{}", contactInput);

        // input validations
        if (!validContactInput(resp, contactInput)) {
            return;
        }

        // create contact
        try {
            Contact createdContact = contactService.create(contactInput);
            objectMapper.writeValue(resp.getOutputStream(), createdContact);
        } catch (ServiceException e) {
            handleError(resp, "Error create contact", 500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("DELETE /contacts");
        resp.setHeader("Content-Type", "application/json");

        String idParam = req.getParameter("id");

        try {
            if (idParam == null) {
                handleError(resp, "ID is required", 400);
            } else {
                Long id = Long.parseLong(idParam);
                contactService.delete(id);
                RespMessage respMessage = new RespMessage("SUCCES", "Contact deleted");
                objectMapper.writeValue(resp.getOutputStream(), respMessage);
            }
        } catch (ServiceException e) {
            handleError(resp, "Error delete contact", 500);
        } catch (NumberFormatException e) {
            handleError(resp, "Invalid ID format", 400);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("PUT /contacts");
        resp.setHeader("Content-Type", "application/json");

        String idParam = req.getParameter("id");
        try {
            if (idParam == null) {
                handleError(resp, "ID is required", 400);
            } else {
                Contact contactInput = objectMapper.readValue(req.getInputStream(), Contact.class);
                LOG.info("Recieved contact input:{}", contactInput);

                // input validations
                if (!validContactInput(resp, contactInput)) {
                    return;
                }
                Long id = Long.parseLong(idParam);
                contactInput.setId(id);

                Contact oldContact = contactService.findByID(id);
                if (oldContact == null) {
                    handleError(resp, "contact not found", 404);
                } else {
                    Contact updatedContact = contactService.update(contactInput);
                    objectMapper.writeValue(resp.getOutputStream(), updatedContact);
                }
            }
        } catch (JsonMappingException e) {
            handleError(resp, "Invalid JSON format", 400);
        } catch (ServiceException e) {
            handleError(resp, "Error update contact", 500);
        } catch (NumberFormatException e) {
            handleError(resp, "Invalid ID format", 400);
        }
    }

    private boolean validInputStringField(String fieldName, String fieldValue,
                                          HttpServletResponse resp) throws IOException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            LOG.error("{} is required", fieldName);
            resp.setStatus(400);
            RespMessage respMessage = new RespMessage("error", fieldName + " is required");
            objectMapper.writeValue(resp.getOutputStream(), respMessage);
            return false;
        }
        return true;
    }

    private boolean validContactInput(HttpServletResponse resp, Contact contactInput) throws IOException {
        if (!validInputStringField("Name", contactInput.getName(), resp)) {
            return false;
        }
        if (!validInputStringField("Email", contactInput.getEmail(), resp)) {
            return false;
        }
        if (!validInputStringField("Phone number", contactInput.getPhoneNumber(), resp)) {
            return false;
        }
        if (!validInputStringField("Adress", contactInput.getAddress(), resp)) {
            return false;
        }
        if (contactInput.getBirthdate() == null) {
            handleError(resp, "Birthdate is required", 400);
            return false;
        }
        return true;
    }

    private void handleError(HttpServletResponse resp, String message, int status) throws IOException {
        LOG.error(message);
        resp.setStatus(status);
        RespMessage errorMessage = new RespMessage("ERROR", message);
        objectMapper.writeValue(resp.getOutputStream(), errorMessage);
    }
}
