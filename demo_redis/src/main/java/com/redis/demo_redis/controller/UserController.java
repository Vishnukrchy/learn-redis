package com.redis.demo_redis.controller;

import com.redis.demo_redis.dao.UserDao;
import com.redis.demo_redis.model.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("test/redis")
    public void testRedis() {
        userDao.testRedis();

    }

    // Define endpoints for user operations here
    // For example, you can create methods to handle CRUD operations for users
    @PostMapping("/create")
    public String createUser(@RequestBody User userJson) {
         userJson.setId(UUID.randomUUID().toString());

        return userDao.createUser(userJson);
    }
    @PostMapping("/create/all")
    public String createAllUsers(@RequestBody List<User> users) {
        for (User user : users) {
            user.setId(UUID.randomUUID().toString());
            userDao.createUser(user);
        }
        return "All users created successfully";
    }
    // read
    @PostMapping("/get/{userId}")
    public User getUser(@PathVariable String userId) {
        return userDao.getUser(userId);
    }
    // update
    @PutMapping("/update")
    public String updateUser(@RequestBody User userJson) {
        return userDao.updateUser(userJson);
    }
    // delete
    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return userDao.deleteUser(userId);
    }
    @GetMapping("/get/alll")
    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }

    @GetMapping("/get/keys")
    public List<String> getAllKeys() {
        return userDao.getAllKeys();
    }
    @GetMapping("/get/values")
    public List<Object> getAllValues() {
        return userDao.getAllValues();
    }
}
