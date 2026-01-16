package com.junaidsultan.service;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for User-related business logic.
 * Follows Interface Segregation Principle (ISP).
 */
public interface IUserService {
    
    /**
     * Authenticate a user with username and password.
     * @param username The username
     * @param password The plain text password
     * @return Optional containing the user if authentication successful
     */
    Optional<User> login(String username, String password);
    
    /**
     * Register a new student user.
     * @param username The username
     * @param email The email
     * @param name The full name
     * @param password The plain text password
     * @return The created user, or null if registration failed
     */
    User registerStudent(String username, String email, String name, String password);
    
    /**
     * Register a new staff user (Admin only).
     * @param username The username
     * @param email The email
     * @param name The full name
     * @param password The plain text password
     * @return The created user, or null if registration failed
     */
    User registerStaff(String username, String email, String name, String password);
    
    /**
     * Reset a user's password.
     * @param email The user's email
     * @param newPassword The new plain text password
     * @return true if password was reset successfully
     */
    boolean resetPassword(String email, String newPassword);
    
    /**
     * Find a user by their ID.
     * @param userId The user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(String userId);
    
    /**
     * Find a user by their username.
     * @param username The username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Get all users with a specific role.
     * @param role The role to filter by
     * @return List of users with that role
     */
    List<User> findByRole(Role role);
    
    /**
     * Get all students.
     * @return List of all students
     */
    List<User> getAllStudents();
    
    /**
     * Get all staff members.
     * @return List of all staff
     */
    List<User> getAllStaff();
    
    /**
     * Update user details.
     * @param user The user with updated details
     * @return The updated user, or null if update failed
     */
    User updateUser(User user);
    
    /**
     * Delete a user by their ID.
     * @param userId The user ID
     * @return true if deleted successfully
     */
    boolean deleteUser(String userId);
    
    /**
     * Check if username is available.
     * @param username The username to check
     * @return true if available
     */
    boolean isUsernameAvailable(String username);
    
    /**
     * Check if email is available.
     * @param email The email to check
     * @return true if available
     */
    boolean isEmailAvailable(String email);
}
