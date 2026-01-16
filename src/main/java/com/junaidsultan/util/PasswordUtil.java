package com.junaidsultan.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and verification.
 * Uses SHA-256 with salt for secure password storage.
 * Follows Single Responsibility Principle (SRP).
 */
public class PasswordUtil {
    
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private static final String SEPARATOR = ":";
    
    /**
     * Hash a password with a randomly generated salt.
     * @param plainPassword The plain text password
     * @return The hashed password with salt (format: salt:hash)
     */
    public static String hashPassword(String plainPassword) {
        try {
            // Generate random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Hash password with salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            
            // Encode salt and hash as Base64
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);
            
            // Return combined format: salt:hash
            return saltBase64 + SEPARATOR + hashBase64;
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing algorithm not found", e);
        }
    }
    
    /**
     * Verify a plain password against a stored hash.
     * @param plainPassword The plain text password to verify
     * @param storedHash The stored hash (format: salt:hash)
     * @return true if the password matches
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        try {
            // Split the stored hash into salt and hash
            String[] parts = storedHash.split(SEPARATOR);
            if (parts.length != 2) {
                return false;
            }
            
            // Decode salt from Base64
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            String storedHashBase64 = parts[1];
            
            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            String inputHashBase64 = Base64.getEncoder().encodeToString(hashedPassword);
            
            // Compare the hashes
            return storedHashBase64.equals(inputHashBase64);
            
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            return false;
        }
    }
}
