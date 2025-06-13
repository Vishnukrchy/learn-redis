package com.redis.demo_redis.dao;

import com.redis.demo_redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Repository
public class UserDao {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private  final String USER_KEY_PREFIX = "user:";
    // Method to create a user in Redis
    // This method takes a User object as input and saves it to Redis


    public String createUser(User userJson) {
        // Generate a unique key for the user using the user ID
        String userKey = USER_KEY_PREFIX + userJson.getId();

        // Save the user object to Redis using the RedisTemplate
        redisTemplate.opsForValue().set(userKey, userJson);

        // Return a success message or the user ID
        return "User created with ID: " + userJson.getId();
    }
    // You can add more methods for other CRUD operations (read, update, delete) as needed

    //read
//    public User getUser(String userId) {
//        // Generate the key for the user using the user ID
//        String userKey = USER_KEY_PREFIX + userId;
//        // Check if the user exists in Redis and return the User object
//        return (User) redisTemplate.opsForValue().get(userKey);
//    }
    //update
    public String updateUser(User userJson) {
        // Generate the key for the user using the user ID
        String userKey = USER_KEY_PREFIX + userJson.getId();
        // Check if the user exists in Redis and update the user object
        if (redisTemplate.hasKey(userKey)) {
            redisTemplate.opsForValue().set(userKey, userJson);
            return "User updated with ID: " + userJson.getId();
        } else {
            return "User not found with ID: " + userJson.getId();
        }
    }
    //delete
    public String deleteUser(String userId) {
        String userKey = USER_KEY_PREFIX + userId;
        if (redisTemplate.hasKey(userKey)) {
            redisTemplate.delete(userKey);
            return "User deleted with ID: " + userId;
        } else {
            return "User not found with ID: " + userId;
        }
    }
    //findAllUsers
    public List<User> findAllUsers() {
        // This method can be implemented to retrieve all users from Redis
        // You can use RedisTemplate to scan through keys with the USER_KEY_PREFIX
        // and collect User objects into a list
        List<User> users = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(USER_KEY_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                User user = (User) redisTemplate.opsForValue().get(key);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    public List<String> getAllKeys() {
        // This method retrieves all keys from Redis that match the USER_KEY_PREFIX
        Set<String> keys = redisTemplate.keys(USER_KEY_PREFIX + "*");
        return keys != null ? new ArrayList<>(keys) : new ArrayList<>();
    }

    public List<Object> getAllValues() {
        // This method retrieves all values from Redis that match the USER_KEY_PREFIX
        Set<String> keys = redisTemplate.keys(USER_KEY_PREFIX + "*");
        List<Object> values = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                Object value = redisTemplate.opsForValue().get(key);
                if (value != null) {
                    values.add(value);
                }
            }
        }
        return values;
    }

    public void testRedis() {
        String testKey = "test:key";
        String testValue = "Hello, Redis!";

        redisTemplate.opsForValue().set(testKey, testValue);
        String retrievedValue = (String) redisTemplate.opsForValue().get(testKey);

        System.out.println("Test Key: " + testKey + ", Test Value: " + retrievedValue);
    }
    public User getUser(String userId) {
        String userKey = USER_KEY_PREFIX + userId;
        try {
            if (redisTemplate.hasKey(userKey)) {
                Object value = redisTemplate.opsForValue().get(userKey);
                if (value instanceof User) {
                    return (User) value;
                } else {
                    throw new IllegalArgumentException("Data for key " + userKey + " is not a valid User object.");
                }
            } else {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving user with ID " + userId + ": " + e.getMessage());
            return null; // Or throw a custom exception
        }
    }
}
