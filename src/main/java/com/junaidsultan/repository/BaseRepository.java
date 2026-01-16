package com.junaidsultan.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository Interface following the Repository Pattern.
 * This provides a clean abstraction over data access operations.
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public interface BaseRepository<T, ID> {
    
    /**
     * Save a new entity to the database.
     * @param entity The entity to save
     * @return The saved entity
     */
    T save(T entity);
    
    /**
     * Update an existing entity.
     * @param entity The entity to update
     * @return The updated entity
     */
    T update(T entity);
    
    /**
     * Find an entity by its ID.
     * @param id The primary key
     * @return Optional containing the entity if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Get all entities.
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Delete an entity by its ID.
     * @param id The primary key
     * @return true if deleted, false otherwise
     */
    boolean deleteById(ID id);
    
    /**
     * Check if an entity exists by its ID.
     * @param id The primary key
     * @return true if exists, false otherwise
     */
    boolean existsById(ID id);
}
