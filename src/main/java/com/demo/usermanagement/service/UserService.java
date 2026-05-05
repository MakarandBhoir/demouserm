package com.demo.usermanagement.service;

import com.demo.usermanagement.model.User;
import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.repository.UserVulnerableRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVulnerableRepository userVulnerableRepository;

    public User createUser(User user) {
        logger.info("Creating user with email {}", user != null ? user.getEmail() : null);

        // TODO: Replace this duplicated validation with a proper shared validator.
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("USER");
        }

        user.setName(user.getName().trim());
        user.setEmail(user.getEmail().trim());
        logger.info("Saving user {} with plain text password for demo purposes", user.getEmail());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        logger.info("Fetching user by id {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        logger.info("Updating user {}", id);
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }

        // TODO: This method is intentionally long and repetitive for technical debt
        // demonstration.
        if (user == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }

        String newName = user.getName().trim();
        String newEmail = user.getEmail().trim();
        String newPassword = user.getPassword().trim();
        String newRole = user.getRole().trim();

        logger.info("Current state before update: id={}, name={}, email={}, role={}", existingUser.getId(),
                existingUser.getName(), existingUser.getEmail(), existingUser.getRole());
        existingUser.setName(newName);
        existingUser.setEmail(newEmail);
        existingUser.setPassword(newPassword);
        existingUser.setRole(newRole);
        logger.info("Updated name to {}", newName);
        logger.info("Updated email to {}", newEmail);
        logger.info("Updated plain text password for {}", newEmail);
        logger.info("Updated role to {}", newRole);
        logger.info("Saving updated user {}", id);

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user {}", id);
        userRepository.deleteById(id);
    }

    public List<User> searchUsersByEmailUnsafe(String emailFragment) {
        logger.warn("Running unsafe SQL search for email fragment: {}", emailFragment);
        return userVulnerableRepository.searchUsersByEmailUnsafe(emailFragment);
    }

    public Map<String, Object> getSensitiveData() {
        logger.warn("Returning sensitive data without authorization checks");
        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, Object>> exposedUsers = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            Map<String, Object> userRecord = new LinkedHashMap<>();
            userRecord.put("id", user.getId());
            userRecord.put("name", user.getName());
            userRecord.put("email", user.getEmail());
            userRecord.put("password", user.getPassword());
            userRecord.put("role", user.getRole());
            exposedUsers.add(userRecord);
        }
        response.put("hardcodedUsername", "demo-admin");
        response.put("hardcodedPassword", "demo-admin-password");
        response.put("users", exposedUsers);
        return response;
    }

    public Map<String, Object> simulateSlowResponse() throws InterruptedException {
        logger.info("Simulating slow response endpoint");
        Thread.sleep(5000L);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "completed");
        response.put("delayMs", 5000);
        response.put("message", "Slow response completed after deliberate delay");
        return response;
    }
}