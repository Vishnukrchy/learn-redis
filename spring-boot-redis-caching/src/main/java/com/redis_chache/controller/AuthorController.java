package com.redis_chache.controller;

import com.redis_chache.entity.Author;
import com.redis_chache.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

//    @PostMapping("/authors")
//    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
//        Author author1 = authorService.createAuthor(author);
//        return author1 != null
//                ? ResponseEntity.ok(author1)
//                : ResponseEntity.status(500).body("Failed to create author");
//    }
//
//    @GetMapping("/authors")
//    public ResponseEntity<?> getAuthors() {
//        return authorService.getAuthors();
//    }
//
//    @GetMapping("/authors/{id}")
//    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
//        Author author = authorService.getAuthorById(id);
//        return author != null
//                ? ResponseEntity.ok(author)
//                : ResponseEntity.status(404).body("Author not found with ID: " + id);
//    }
//
//    @PostMapping("/authors/{id}")
//    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
//        return authorService.updateAuthor(id, author);
//    }
//
//    @PostMapping("/authors/{id}/delete")
//    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
//        return authorService.deleteAuthor(id);
//    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return author != null
                ? ResponseEntity.ok(author)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found with ID: " + id);
    }

    @GetMapping("/authors")
    public ResponseEntity<?> getAuthors() {
        List<Author> authors = authorService.getAuthors();
        return authors != null && !authors.isEmpty()
                ? ResponseEntity.ok(authors)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No authors found");
    }

    @PostMapping("/authors/{id}/delete")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        return deleted
                ? ResponseEntity.ok("Author deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found with id: " + id);
    }

}
