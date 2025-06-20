package com.redis_chache.service;

import com.redis_chache.entity.Author;
import com.redis_chache.entity.Notes;
import com.redis_chache.repository.AuthorRepository;
import com.redis_chache.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    // Implement the methods from NotesService interface here
    @Autowired
    private AuthorServiceImpl authorService;
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public ResponseEntity<?> createNotes(Notes notes) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            // Ensure author is set
            if (notes.getAuthor() == null) {
                throw new IllegalArgumentException("Author cannot be null");
            }
            Author author = authorRepository.findById(notes.getAuthor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + notes.getAuthor().getId()));
            notes.setAuthor(author); // Set the author for the notes
            //notes.setAuthor((Author) authorService.getAuthorById(notes.getAuthor().getId()).getBody());

            Notes savedNotes = notesRepository.save(notes);
            response.put("status", "success");
            response.put("data", savedNotes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to create notes: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> updateNotes(Long notes) {
        // Logic to update notes
        HashMap<String, Object> response = new HashMap<>();
        try {
            Notes existingNotes = notesRepository.findById(notes).orElseThrow(() -> new RuntimeException("Notes not found"));
            existingNotes.setUpdatedAt(LocalDate.now());
            Notes updatedNotes = notesRepository.save(existingNotes);
            response.put("status", "success");
            response.put("data", updatedNotes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to update notes: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteNotes(Long notes) {
        // Logic to delete notes
        HashMap<String, Object> response = new HashMap<>();
        try {
            Notes existingNotes = notesRepository.findById(notes).orElseThrow(() -> new RuntimeException("Notes not found"));
            notesRepository.delete(existingNotes);
            response.put("status", "success");
            response.put("message", "Notes deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to delete notes: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getNotesById(Long id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            // Logic to get notes by ID
            // Notes existingNotes = notesRepository.findById(notes.getId()).orElseThrow(() -> new RuntimeException("Notes not found"));
            Optional<Notes> existingNotes = notesRepository.findById(id);
            if (existingNotes.isPresent()) {
                response.put("status", "success");
                response.put("data", existingNotes.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Notes not found with id: " + id);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to get notes: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getNotes() {
        // Logic to get all notes
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<Notes> notesList = notesRepository.findAll();
            if (notesList != null && !notesList.isEmpty()) {
                response.put("status", "success");
                response.put("data", notesList);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "No notes found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to get notes: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getNotesByAuthorId(Long authorId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            // Logic to get notes by author ID
            List<Notes> notesList = notesRepository.findByAuthorId(authorId);
            if (notesList != null && !notesList.isEmpty()) {
                response.put("status", "success");
                response.put("data", notesList);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "No notes found for author with id: " + authorId);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to get notes by author ID: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
//    public ResponseEntity<?> createNotes(Long authorId, List<Notes> notes) {
////        HashMap<String, Object> response = new HashMap<>();
////        try {
////            for (Notes note : notes) {
////                note.setAuthor(authorId); // Set the author ID for each note
////                note.setCreatedAt(LocalDate.now()); // Set the creation date
////                note.setUpdatedAt(LocalDate.now()); // Set the update date
////                notesRepository.save(note);
////            }
////            response.put("status", "success");
////            response.put("message", "Notes created successfully");
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            response.put("status", "error");
////            response.put("message", "Failed to create notes: " + e.getMessage());
////            return ResponseEntity.status(500).body(response);
////        }
////    }
//        return null;
//    }

    public ResponseEntity<?> createNotes(Long authorId, List<Notes> notes) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Fetch author from the database
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + authorId));

            // Set common fields on all notes
            LocalDate now = LocalDate.now();
            for (Notes note : notes) {
                note.setAuthor(author);
                note.setCreatedAt(now);
                note.setUpdatedAt(now);
            }

            // Save all notes in one batch
            notesRepository.saveAll(notes);

            response.put("status", "success");
            response.put("message", "Notes created successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to create notes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
