package edu.bbte.idde.jgim2241.repository.mem;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.ContactDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class ContactMemDao extends MemDao<Contact> implements ContactDao {
    @Override
    public Collection<Contact> findByName(String name) {
        return entities.values().stream()
                .filter(contact -> contact.getName().equals(name))
                .collect(Collectors.toList());
    }
}
