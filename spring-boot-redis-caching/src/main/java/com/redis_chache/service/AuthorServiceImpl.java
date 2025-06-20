package com.redis_chache.service;

import com.redis_chache.entity.Author;
import com.redis_chache.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthorServiceImpl.class);

  /*
    // Implement the methods from AuthorService interface here
    @Override
    public Author createAuthor(Author author) {
        // Implementation logic
        author = authorRepository.save(author);

        return author != null
                ? author
                : null; // Return the saved author or null if the save operation failed

    }

    @Override
    public Author getAuthorById(Long id) {
        // Implementation logic
      Optional<Author> author= authorRepository.findById(id);
        return author.orElse(null); // Return the author if found, otherwise return null


    }

    @Override
    public ResponseEntity<?> getAuthors() {
        // Implementation logic
        List<Author> authors = authorRepository.findAll();
        return authors != null && !authors.isEmpty()
                ? ResponseEntity.ok(authors)
                : ResponseEntity.status(404).body("No authors found");
    }

    @Override
    public ResponseEntity<?> updateAuthor(Long id, Author author) {
        // Implementation logic
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            author.setId(id); // Ensure the ID is set for the update
            author.setUpdatedAt(LocalDate.now()); // Update the timestamp
            Author updatedAuthor = authorRepository.save(author);
            return ResponseEntity.ok(updatedAuthor);
        } else {
            return ResponseEntity.status(404).body("Author not found with id: " + id);
        }


    }

    @Override
    public ResponseEntity<?> deleteAuthor(Long id) {
        // Implementation logic
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            /*
            If the author exists, delete it from the repository
            if cascade delete is not set up, you may need to handle related entities manually
            like notes associated with the author
            example: if you have notes associated with the author, you may need to delete them first
            before deleting the author to avoid foreign key constraint violations
            If cascade delete is set up, the related entities will be deleted automatically


            authorRepository.delete(existingAuthor.get());
            return ResponseEntity.ok("Author deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Author not found with id: " + id);
        }

    }

   */

    @Override
    @Cacheable(value = "author", key = "#id")
    public Author getAuthorById(Long id) {

        log.debug("🔸 getAuthorById({}) -> checking DB", id);
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable("authors")
    public List<Author> getAuthors() {
        log.debug("🔸 getAuthors() -> checking DB");
        return authorRepository.findAll();
    }

    @Override
    public boolean deleteAuthor(Long id) {
        Optional<Author> existing = authorRepository.findById(id);
        if (existing.isPresent()) {
            authorRepository.delete(existing.get());
            return true;
        }
        return false;
    }
    @Override
    @CachePut(value = "author", key = "#result.id")
    @CacheEvict(value = "authors", allEntries = true)
    public Author createAuthor(Author author) {
        author.setCreatedAt(LocalDate.now());
        author.setUpdatedAt(LocalDate.now());
        author = authorRepository.save(author);

        return author != null ? author : null;
    }

    @Override
    @CachePut(value = "author", key = "#id")
    @CacheEvict(value = "authors", allEntries = true)
    public Author updateAuthor(Long id, Author author) {
        Optional<Author> existingAuthor = authorRepository.findById(id);

        if (existingAuthor.isPresent()) {
            author.setId(id); // Set the same ID for update
            author.setUpdatedAt(LocalDate.now()); // Set update time
            Author updatedAuthor = authorRepository.save(author);
            return updatedAuthor;
        } else {
            return null;
        }
    }


}
