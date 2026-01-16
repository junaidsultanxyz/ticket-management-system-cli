package com.junaidsultan.service;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;
import com.junaidsultan.repository.IUserRepository;
import com.junaidsultan.repository.UserRepository;
import com.junaidsultan.util.PasswordUtil;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of IUserService.
 * Contains business logic for user operations.
 * Follows Dependency Inversion Principle (DIP) - depends on abstractions.
 */
public class UserService implements IUserService {
    
    private final IUserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    // Constructor for dependency injection (useful for testing)
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
    
    @Override
    public User registerStudent(String username, String email, String name, String password) {
        // Validation
        if (!isUsernameAvailable(username)) {
            System.out.println("Username already exists.");
            return null;
        }
        if (!isEmailAvailable(email)) {
            System.out.println("Email already exists.");
            return null;
        }
        
        // Create user with hashed password
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(username, email, name, hashedPassword, Role.STUDENT);
        
        return userRepository.save(user);
    }
    
    @Override
    public User registerStaff(String username, String email, String name, String password) {
        // Validation
        if (!isUsernameAvailable(username)) {
            System.out.println("Username already exists.");
            return null;
        }
        if (!isEmailAvailable(email)) {
            System.out.println("Email already exists.");
            return null;
        }
        
        // Create staff user with hashed password
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(username, email, name, hashedPassword, Role.STAFF);
        
        return userRepository.save(user);
    }
    
    @Override
    public boolean resetPassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Only students can reset password
            if (user.getRole() != Role.STUDENT) {
                System.out.println("Password reset is only available for students.");
                return false;
            }
            
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            return userRepository.updatePassword(user.getId(), hashedPassword);
        }
        
        System.out.println("User not found with this email.");
        return false;
    }
    
    @Override
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    @Override
    public List<User> getAllStudents() {
        return userRepository.findByRole(Role.STUDENT);
    }
    
    @Override
    public List<User> getAllStaff() {
        return userRepository.findByRole(Role.STAFF);
    }
    
    @Override
    public User updateUser(User user) {
        return userRepository.update(user);
    }
    
    @Override
    public boolean deleteUser(String userId) {
        return userRepository.deleteById(userId);
    }
    
    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
    
    @Override
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }
}
