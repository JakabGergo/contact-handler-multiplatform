package edu.bbte.idde.jgim2241.service;

import edu.bbte.idde.jgim2241.model.Contact;

import java.util.Collection;

public interface ContactService {
    Collection<Contact> findAll() throws ServiceException;

    Contact findByID(Long id) throws ServiceException;

    Collection<Contact> findByName(String name) throws ServiceException;

    Long getCount() throws ServiceException;

    Contact create(Contact contact) throws ServiceException;

    Contact update(Contact contact) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
