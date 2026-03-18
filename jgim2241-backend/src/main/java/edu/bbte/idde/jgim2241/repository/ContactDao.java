package edu.bbte.idde.jgim2241.repository;

import edu.bbte.idde.jgim2241.model.Contact;

import java.util.Collection;

public interface ContactDao extends Dao<Contact> {
    Collection<Contact> findByName(String name) throws RepositoryException;
}
