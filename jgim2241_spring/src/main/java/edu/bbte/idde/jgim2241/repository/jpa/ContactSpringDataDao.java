package edu.bbte.idde.jgim2241.repository.jpa;

import edu.bbte.idde.jgim2241.model.Contact;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jpa")
public interface ContactSpringDataDao extends JpaRepository<Contact, Long> {
    List<Contact> findByName(String name);
}
