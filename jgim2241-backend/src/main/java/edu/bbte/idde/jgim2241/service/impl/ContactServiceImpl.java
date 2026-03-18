package edu.bbte.idde.jgim2241.service.impl;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.DaoFactory;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import edu.bbte.idde.jgim2241.repository.ContactDao;
import edu.bbte.idde.jgim2241.service.ContactService;
import edu.bbte.idde.jgim2241.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

public class ContactServiceImpl implements ContactService, Serializable {
    private ContactDao contactDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    public ContactServiceImpl() {
        this.contactDao = DaoFactory.getInstance().getContactDao();
    }

    public ContactDao getContactDao() {
        return contactDao;
    }

    public void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public Collection<Contact> findAll() throws ServiceException {
        try {
            return contactDao.findAll();
        } catch (RepositoryException e) {
            LOGGER.error("Error at find all Contact" + e.getMessage());
            throw new ServiceException("Error at find all Contact", e);
        }
    }

    @Override
    public Contact findByID(Long id) throws ServiceException {
        try {
            return contactDao.findByID(id);
        } catch (RepositoryException e) {
            LOGGER.error("Error at find Contact by id" + e.getMessage());
            throw new ServiceException("Error at find Contact by id", e);
        }
    }

    @Override
    public Long getCount() throws ServiceException {
        try {
            return contactDao.getCount();
        } catch (RepositoryException e) {
            LOGGER.error("Error at get Contact count" + e.getMessage());
            throw new ServiceException("Error at get Contact count", e);
        }
    }

    @Override
    public Contact create(Contact contact) throws ServiceException {
        try {
            return contactDao.create(contact);
        } catch (RepositoryException e) {
            LOGGER.error("Error at create Contact" + e.getMessage());
            throw new ServiceException("Error at create Contact", e);
        }
    }

    @Override
    public Contact update(Contact contact) throws ServiceException {
        try {
            Contact oldContact = contactDao.findByID(contact.getId());
            if (oldContact == null) {
                LOGGER.error("Error at update Contact");
                throw new ServiceException("Contact not found");
            }
            return contactDao.update(contact);
        } catch (RepositoryException e) {
            LOGGER.error("Error at update Contact");
            throw new ServiceException("Error at update Contact", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            Contact contact = contactDao.findByID(id);
            if (contact == null) {
                LOGGER.error("Error at delete Contact");
                throw new ServiceException("Contact not found");
            }
            contactDao.delete(id);
        } catch (RepositoryException e) {
            LOGGER.error("Error at delete Contact");
            throw new ServiceException("Error at delete contact", e);
        }
    }
}
