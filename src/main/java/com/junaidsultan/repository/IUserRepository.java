package com.junaidsultan.repository;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * User-specific repository interface extending the base repository.
 * Follows Interface Segregation Principle (ISP) - clients only depend on methods they use.
 */
public interface IUserRepository extends BaseRepository<User, String> {
    
    /**
     * Find a user by username.
     * @param username The username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email.
     * @param email The email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users with a specific role.
     * @param role The role to filter by
     * @return List of users with the specified role
     */
    List<User> findByRole(Role role);
    
    /**
     * Check if a username already exists.
     * @param username The username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email already exists.
     * @param email The email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Update user's password.
     * @param userId The user's ID
     * @param newPasswordHash The new password hash
     * @return true if updated successfully
     */
    boolean updatePassword(String userId, String newPasswordHash);
}
