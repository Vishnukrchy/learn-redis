package com.redis_chache.repository;

import com.redis_chache.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // This interface will automatically provide CRUD operations for Author entities
    // Additional custom query methods can be defined here if needed
}
