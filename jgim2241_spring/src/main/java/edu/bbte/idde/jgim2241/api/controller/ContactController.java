package edu.bbte.idde.jgim2241.api.controller;

import edu.bbte.idde.jgim2241.api.dto.in.ContactCreationDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.ContactDetailsDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.ContactReduceDto;
import edu.bbte.idde.jgim2241.api.exception.EntityNotFoundException;
import edu.bbte.idde.jgim2241.api.mapper.ContactMapper;
import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.service.ContactService;
import edu.bbte.idde.jgim2241.service.ServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/contacts")
@Validated // @Min(0) not working at pathVariables without this
@CrossOrigin(origins = "http://localhost:5173") // Csak erre az originre engedélyezett
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    ContactMapper contactMapper;

    @GetMapping
    public Collection<ContactReduceDto> findAllContacts(@RequestParam(required = false) String name)
            throws ServiceException {
        if (name != null && !name.isEmpty()) {
            var contacts = contactService.findByName(name);
            return contactMapper.modelsToReducedDtos(contacts);
        }
        var contacts = contactService.findAll();
        return contactMapper.modelsToReducedDtos(contacts);
    }

    @GetMapping("/{contactId}")
    public ContactDetailsDto findContactById(@PathVariable("contactId") @Min(0) Long contactId)
            throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }
        return contactMapper.modelToDetailsDto(contact);
    }

    @PostMapping
    public ResponseEntity<ContactDetailsDto> createContact(@RequestBody @Valid ContactCreationDto contactDto)
            throws ServiceException {
        try {
            Contact contactInput = contactMapper.creationDtoToModel(contactDto);
            Contact newContact = contactService.create(contactInput);
            URI createUri = URI.create("/api/contacts/" + newContact.getId());
            return ResponseEntity.created(createUri).body(contactMapper.modelToDetailsDto(newContact));
        } catch (ServiceException e) {
            throw new ServiceException("Failed to create contact.", e);
        }
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable @Min(0) Long contactId,
                                                 @RequestBody @Valid ContactCreationDto contactDto)
            throws ServiceException {
        Contact contact = contactMapper.creationDtoToModel(contactDto);
        var oldContact = contactService.findByID(contactId);
        if (oldContact == null) {
            Contact newContact = contactService.create(contact);
            URI createUri = URI.create("/api/contacts/" + newContact.getId());
            return ResponseEntity.created(createUri).body(newContact);
        }
        contact.setId(contactId);
        contact.setNotes(oldContact.getNotes());
        contactService.update(contact);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("contactId") @Min(0) Long contactId) throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }
        contactService.delete(contactId);
    }
}
