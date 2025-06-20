package com.redis_chache.controller;

import com.redis_chache.entity.Notes;
import com.redis_chache.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotesController {
    // This interface will automatically provide CRUD operations for Notes entities
    // Additional custom query methods can be defined here if needed
    @Autowired
    private NotesService notesService;

    @PostMapping("/notes")
    public ResponseEntity<?> createNotes(@RequestBody Notes notes) {
        // Logic to create a note
        return notesService.createNotes(notes);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<?> updateNotes(@PathVariable Long id) {
        // Logic to update a note by ID
        return notesService.updateNotes(id);
    }

    @DeleteMapping("/notes/{id}/delete")
    public ResponseEntity<?> deleteNotes(@PathVariable Long id) {
        // Logic to delete a note by ID
        return notesService.deleteNotes(id);
    }

    @GetMapping("/notes/{id}/get")
    public ResponseEntity<?> getNotesById(@PathVariable Long id) {
        // Logic to get a note by ID
        return notesService.getNotesById(id);
    }

    @GetMapping("/notes")
    public ResponseEntity<?> getNotes() {
        // Logic to get all notes
        return notesService.getNotes();
    }

    @GetMapping("/notes/author/{authorId}")
    public ResponseEntity<?> getNotesByAuthorId(@PathVariable Long authorId) {
        // Logic to get notes by author ID
        return notesService.getNotesByAuthorId(authorId);
    }

    @PostMapping("/notes/{id}")
    public ResponseEntity<?> saveNotes(@PathVariable(value = "id") Long authorId, @RequestBody List<Notes> notes) {
        // Logic to save notes by ID
        return notesService.createNotes(authorId, notes);


    }
}
