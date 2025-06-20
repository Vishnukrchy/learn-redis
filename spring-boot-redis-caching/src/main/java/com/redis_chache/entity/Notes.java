package com.redis_chache.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


@Entity(name = "notes")

public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_seq")
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Content cannot be blank")
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String status;
    private String tags;
    private String category;
    private String imageUrl;

    @JsonBackReference // Prevents infinite recursion during serialization
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false,referencedColumnName = "id")
    private Author author;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    public Notes() {
        // Default constructor
    }

    public Notes(Long id, String title, String content, LocalDate createdAt, LocalDate updatedAt, String status, String tags, String category, String imageUrl, Author author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.tags = tags;
        this.category = category;
        this.imageUrl = imageUrl;
        this.author = author;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title cannot be blank") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be blank") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Content cannot be blank") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") String content) {
        this.content = content;
    }

    public @NotNull(message = "createAt cannot be null") LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "createAt cannot be null") LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//    public Integer getAuthorId() {
//        return author_id;
//    }
//
//    public void setAuthorId(Integer authorId) {
//        this.author_id = authorId;
//    }
}
