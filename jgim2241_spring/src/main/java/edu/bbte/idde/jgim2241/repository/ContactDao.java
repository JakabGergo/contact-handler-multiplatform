package edu.bbte.idde.jgim2241.repository;

import edu.bbte.idde.jgim2241.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ContactDao extends Dao<Contact> {
    Collection<Contact> findByName(String name) throws RepositoryException;
}
