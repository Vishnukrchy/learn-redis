package com.redis_chache.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    private String profilePictureUrl;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;
    private String bio;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;


    @JsonIgnore//for preventing infinite recursion during serialization
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdAt DESC") // Optional: to order notes by creation date
    List<Notes> notes=new ArrayList<>();

    /*
     CascadeType.ALL allows all operations (persist, merge, remove, refresh, detach) to be cascaded to the notes.
        FetchType.LAZY means that the notes will not be loaded from the database until they are explicitly accessed.
        * @return the list of notes associated with the author
        CascadeType.ALL is useful when you want to manage the lifecycle of the notes along with the author.

        like if you delete an author, all their notes will also be deleted.
        if you update an author, all their notes will also be updated.
        if you add a new note to an author, it will be automatically persisted.
        FetchType.LAZY is useful for performance reasons, as it avoids loading the notes until they are needed.

        manyToOne relationship is used to establish a many-to-one association between the Author and Notes entities.
        here, each note is associated with one author, but an author can have multiple notes.
        to achieve this, we use the @ManyToOne annotation on the Notes entity, which creates a foreign key relationship in the database.

        The @JoinColumn annotation specifies the foreign key column in the Notes table that references the Author table.
        In this case, the foreign key column is named "author_id", and it cannot be null, meaning that every note must be associated with an author.

        This relationship allows us to easily retrieve all notes associated with a specific author and manage the notes' lifecycle in relation to the author.


        mappedBy = "author" indicates that the notes are mapped by the author field in the Notes entity.
        if you want to establish a bidirectional relationship, you would also need to define the author field in the Notes entity.
        here total table will be created with the name "author" and the notes will be stored in a separate table with a foreign key reference to the author.

             */
    public Author() {

    }

    public Author(Long id, String name, String email, String profilePictureUrl, String phoneNumber, String bio, LocalDate createdAt, LocalDate updatedAt, List<Notes> notes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces") String getName() {
        return name;
    }

    public void setName(@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces") String name) {
        this.name = name;
    }

    public @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format") String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public @NotEmpty(message = "Phone number cannot be empty") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty(message = "Phone number cannot be empty") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }


    //    public List<Notes> getNotes() {
//        return notes;
//    }
//
//    public void setNotes(List<Notes> notes) {
//        this.notes = notes;
//    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }
}
