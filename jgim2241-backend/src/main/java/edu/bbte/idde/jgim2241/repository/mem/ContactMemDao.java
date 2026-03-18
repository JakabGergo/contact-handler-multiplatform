package edu.bbte.idde.jgim2241.repository.mem;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.ContactDao;

import java.util.Collection;
import java.util.stream.Collectors;

public class ContactMemDao extends MemDao<Contact> implements ContactDao {
    @Override
    public Collection<Contact> findByName(String name) {
        return entities.values().stream()
                .filter(contact -> contact.getName().equals(name))
                .collect(Collectors.toList());
    }
}
