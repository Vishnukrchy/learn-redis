package com.redis.demo_redis.model;

import lombok.*;

import java.io.Serializable;

// to implement redis operations, we need to create a model class that represents the data we want to store in Redis.
// we need to serialize and deserialize the data to and from JSON format.
/**
 * User class represents a user entity with fields such as id, name, email, phone, and address.
 * It implements Serializable to allow serialization of User objects for storage in Redis.
 */
public class User   implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public User() {
        // Default constructor
    }

    public User(String id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
