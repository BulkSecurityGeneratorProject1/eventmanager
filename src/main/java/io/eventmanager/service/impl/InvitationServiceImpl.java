package io.eventmanager.service.impl;

import io.eventmanager.service.InvitationService;
import io.eventmanager.domain.Invitation;
import io.eventmanager.repository.InvitationRepository;
import io.eventmanager.repository.search.InvitationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Invitation.
 */
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService{

    private final Logger log = LoggerFactory.getLogger(InvitationServiceImpl.class);

    private final InvitationRepository invitationRepository;

    private final InvitationSearchRepository invitationSearchRepository;

    public InvitationServiceImpl(InvitationRepository invitationRepository, InvitationSearchRepository invitationSearchRepository) {
        this.invitationRepository = invitationRepository;
        this.invitationSearchRepository = invitationSearchRepository;
    }

    /**
     * Save a invitation.
     *
     * @param invitation the entity to save
     * @return the persisted entity
     */
    @Override
    public Invitation save(Invitation invitation) {
        log.debug("Request to save Invitation : {}", invitation);
        Invitation result = invitationRepository.save(invitation);
        invitationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invitations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Invitation> findAll(Pageable pageable) {
        log.debug("Request to get all Invitations");
        return invitationRepository.findAll(pageable);
    }

    /**
     * Get one invitation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Invitation findOne(Long id) {
        log.debug("Request to get Invitation : {}", id);
        return invitationRepository.findOne(id);
    }

    /**
     * Delete the invitation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invitation : {}", id);
        invitationRepository.delete(id);
        invitationSearchRepository.delete(id);
    }

    /**
     * Search for the invitation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Invitation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Invitations for query {}", query);
        Page<Invitation> result = invitationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
