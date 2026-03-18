package edu.bbte.idde.jgim2241.service.impl;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.ContactDao;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import edu.bbte.idde.jgim2241.service.ContactService;
import edu.bbte.idde.jgim2241.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

@Service
@Slf4j
@Profile("!jpa")
public class ContactServiceImpl implements ContactService, Serializable {
    @Autowired
    private ContactDao contactDao;

    @Override
    public Collection<Contact> findAll() throws ServiceException {
        try {
            return contactDao.findAll();
        } catch (RepositoryException e) {
            log.error("Error at find all Contact" + e.getMessage());
            throw new ServiceException("Error at find all Contact", e);
        }
    }

    @Override
    public Contact findByID(Long id) throws ServiceException {
        try {
            return contactDao.findByID(id);
        } catch (RepositoryException e) {
            log.error("Error at find Contact by id" + e.getMessage());
            throw new ServiceException("Error at find Contact by id", e);
        }
    }

    @Override
    public Collection<Contact> findByName(String name) throws ServiceException {
        try {
            return contactDao.findByName(name);
        } catch (RepositoryException e) {
            log.error("Error at find Contact by name" + e.getMessage());
            throw new ServiceException("Error at find Contact by name", e);
        }
    }

    @Override
    public Long getCount() throws ServiceException {
        try {
            return contactDao.getCount();
        } catch (RepositoryException e) {
            log.error("Error at get Contact count" + e.getMessage());
            throw new ServiceException("Error at get Contact count", e);
        }
    }

    @Override
    public Contact create(Contact contact) throws ServiceException {
        try {
            return contactDao.create(contact);
        } catch (RepositoryException e) {
            log.error("Error at create Contact" + e.getMessage());
            throw new ServiceException("Error at create Contact", e);
        }
    }

    @Override
    public Contact update(Contact contact) throws ServiceException {
        try {
            Contact oldContact = contactDao.findByID(contact.getId());
            if (oldContact == null) {
                log.info("New contact created at update");
                return contactDao.create(contact);
            }
            return contactDao.update(contact);
        } catch (RepositoryException e) {
            log.error("Error at update Contact");
            throw new ServiceException("Error at update Contact", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            Contact contact = contactDao.findByID(id);
            if (contact == null) {
                log.error("Error at delete Contact");
                throw new ServiceException("Contact not found");
            }
            contactDao.delete(id);
        } catch (RepositoryException e) {
            log.error("Error at delete Contact");
            throw new ServiceException("Error at delete contact", e);
        }
    }
}
