package io.eventmanager.service;

import io.eventmanager.domain.UserParticipate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserParticipate.
 */
public interface UserParticipateService {

    /**
     * Save a userParticipate.
     *
     * @param userParticipate the entity to save
     * @return the persisted entity
     */
    UserParticipate save(UserParticipate userParticipate);

    /**
     * Get all the userParticipates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserParticipate> findAll(Pageable pageable);

    /**
     * Get the "id" userParticipate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserParticipate findOne(Long id);

    /**
     * Delete the "id" userParticipate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userParticipate corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserParticipate> search(String query, Pageable pageable);
}
