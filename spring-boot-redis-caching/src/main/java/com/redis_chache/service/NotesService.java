package com.redis_chache.service;

import com.redis_chache.entity.Notes;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotesService {
    public ResponseEntity<?> createNotes(Notes notes);

    public ResponseEntity<?> updateNotes(Long notes);

    public ResponseEntity<?> deleteNotes(Long notes);

    public ResponseEntity<?> getNotesById(Long notes);

    public ResponseEntity<?> getNotes();

    ResponseEntity<?> getNotesByAuthorId(Long authorId);

    ResponseEntity<?> createNotes(Long authorId, List<Notes> notes);
}
