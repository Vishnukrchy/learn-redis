package com.redis_chache.service;

import com.redis_chache.entity.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {



//    // Implement the methods from AuthorService interface here
//    public Author createAuthor(Author author);
//
//    public Author getAuthorById(Long id);
//
//    public ResponseEntity<?> getAuthors();
//
//    public ResponseEntity<?> updateAuthor(Long id, Author author);
//
//    public ResponseEntity<?> deleteAuthor(Long id);

    Author createAuthor(Author author);
    Author getAuthorById(Long id);
    List<Author> getAuthors();
    Author updateAuthor(Long id, Author author);
    boolean deleteAuthor(Long id);
}
