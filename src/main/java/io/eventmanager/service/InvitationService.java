package io.eventmanager.service;

import io.eventmanager.domain.Invitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Invitation.
 */
public interface InvitationService {

    /**
     * Save a invitation.
     *
     * @param invitation the entity to save
     * @return the persisted entity
     */
    Invitation save(Invitation invitation);

    /**
     * Get all the invitations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Invitation> findAll(Pageable pageable);

    /**
     * Get the "id" invitation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Invitation findOne(Long id);

    /**
     * Delete the "id" invitation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the invitation corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Invitation> search(String query, Pageable pageable);
}
