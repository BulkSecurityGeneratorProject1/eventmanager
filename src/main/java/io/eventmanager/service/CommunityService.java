package io.eventmanager.service;

import io.eventmanager.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Community.
 */
public interface CommunityService {

    /**
     * Save a community.
     *
     * @param community the entity to save
     * @return the persisted entity
     */
    Community save(Community community);

    /**
     * Get all the communities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Community> findAll(Pageable pageable);

    /**
     * Get the "id" community.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Community findOne(Long id);

    /**
     * Delete the "id" community.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the community corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Community> search(String query, Pageable pageable);
}
