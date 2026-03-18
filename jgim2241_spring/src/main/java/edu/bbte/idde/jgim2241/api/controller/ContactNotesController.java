package edu.bbte.idde.jgim2241.api.controller;

import edu.bbte.idde.jgim2241.api.dto.in.NoteCreationDto;
import edu.bbte.idde.jgim2241.api.dto.in.NoteUpdateDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.NoteResponseDto;
import edu.bbte.idde.jgim2241.api.exception.EntityNotFoundException;
import edu.bbte.idde.jgim2241.api.mapper.NoteMapper;
import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.model.Note;
import edu.bbte.idde.jgim2241.service.ContactService;
import edu.bbte.idde.jgim2241.service.ServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/contacts/{contactId}/notes")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
@Profile("jpa")
public class ContactNotesController {
    @Autowired
    private ContactService contactService;

    @Autowired
    NoteMapper noteMapper;

    @GetMapping
    public Collection<NoteResponseDto> findNotesForContact(@PathVariable("contactId") @Min(0) Long contactId)
            throws ServiceException {
        Contact contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }
        return noteMapper.modelsToDtos(contact.getNotes());
    }

    @GetMapping("/{noteId}")
    public NoteResponseDto findNoteById(@PathVariable("contactId") @Min(0) Long contactId,
                                        @PathVariable("noteId") @Min(0) Long noteId) throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }

        List<Note> notes = contact.getNotes();
        Note noteToReturn = notes.stream()
                .filter(note -> note.getId().equals(noteId))
                .findFirst()
                .orElse(null);
        if (noteToReturn == null) {
            throw new EntityNotFoundException("Note with ID " + noteId + " not found.");
        }
        return noteMapper.modelToDto(noteToReturn);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@PathVariable("contactId") @Min(0) Long contactId,
                                                      @RequestBody @Valid NoteCreationDto noteCreationDto)
            throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }

        //add input to contact
        Note noteInput = noteMapper.creationDtoToModel(noteCreationDto);
        noteInput.setContact(contact);
        contact.getNotes().add(noteInput);

        //the note is added to db now
        contactService.update(contact);
        Note newNote = contact.getNotes().get(contact.getNotes().size() - 1);
        URI createUri = URI.create("/api/contacts" + contact.getId() + "/notes/" + newNote.getId());
        return ResponseEntity.created(createUri).body(noteMapper.modelToDto(newNote));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable("contactId") @Min(0) Long contactId,
                                                      @PathVariable("noteId") @Min(0) Long noteId,
                                                      @RequestBody @Valid NoteUpdateDto noteUpdateDto)
            throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }

        List<Note> notes = contact.getNotes();
        Note noteToUpdate = notes.stream()
                .filter(note -> note.getId().equals(noteId))
                .findFirst()
                .orElse(null);
        if (noteToUpdate == null) {
            throw new EntityNotFoundException("Note with ID " + noteId + " not found.");
        }

        // update note content
        noteToUpdate.setContent(noteUpdateDto.getContent());
        contactService.update(contact);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteNote(@PathVariable("contactId") @Min(0) Long contactId,
                           @PathVariable("noteId") @Min(0) Long noteId) throws ServiceException {
        var contact = contactService.findByID(contactId);
        if (contact == null) {
            throw new EntityNotFoundException("Contact with ID " + contactId + " not found.");
        }

        List<Note> notes = contact.getNotes();
        Note noteToDelete = notes.stream()
                .filter(note -> note.getId().equals(noteId))
                .findFirst()
                .orElse(null);

        if (noteToDelete == null) {
            throw new EntityNotFoundException("Note with ID " + noteId + " not found.");
        }
        notes.remove(noteToDelete);
        contactService.update(contact);
    }
}
