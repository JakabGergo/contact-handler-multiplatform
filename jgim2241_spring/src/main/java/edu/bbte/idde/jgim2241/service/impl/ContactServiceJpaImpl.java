package edu.bbte.idde.jgim2241.service.impl;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.jpa.ContactSpringDataDao;
import edu.bbte.idde.jgim2241.service.ContactService;
import edu.bbte.idde.jgim2241.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@Profile("jpa")
public class ContactServiceJpaImpl implements ContactService {
    @Autowired
    private ContactSpringDataDao contactDao;

    @Override
    public Collection<Contact> findAll() throws ServiceException {
        return contactDao.findAll();
    }

    @Override
    public Contact findByID(Long id) throws ServiceException {
        return contactDao.findById(id).orElse(null);
    }

    @Override
    public Collection<Contact> findByName(String name) throws ServiceException {
        return contactDao.findByName(name);
    }

    @Override
    public Long getCount() throws ServiceException {
        return contactDao.count();
    }

    @Override
    public Contact create(Contact contact) throws ServiceException {
        try {
            return contactDao.save(contact);
        } catch (OptimisticLockingFailureException e) {
            log.error("Error at create Contact" + e.getMessage());
            throw new ServiceException("Error at create Contact", e);
        }
    }

    @Override
    public Contact update(Contact contact) throws ServiceException {
        try {
            return contactDao.save(contact);
        } catch (OptimisticLockingFailureException e) {
            log.error("Error at update Contact" + e.getMessage());
            throw new ServiceException("Error at update Contact", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (contactDao.existsById(id)) {
            contactDao.deleteById(id);
        } else {
            log.error("Error at delete Contact");
            throw new ServiceException("Contact not found");
        }
    }
}
