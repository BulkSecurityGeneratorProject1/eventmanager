package io.eventmanager.service.impl;

import io.eventmanager.service.UserParticipateService;
import io.eventmanager.domain.UserParticipate;
import io.eventmanager.repository.UserParticipateRepository;
import io.eventmanager.repository.search.UserParticipateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserParticipate.
 */
@Service
@Transactional
public class UserParticipateServiceImpl implements UserParticipateService{

    private final Logger log = LoggerFactory.getLogger(UserParticipateServiceImpl.class);

    private final UserParticipateRepository userParticipateRepository;

    private final UserParticipateSearchRepository userParticipateSearchRepository;

    public UserParticipateServiceImpl(UserParticipateRepository userParticipateRepository, UserParticipateSearchRepository userParticipateSearchRepository) {
        this.userParticipateRepository = userParticipateRepository;
        this.userParticipateSearchRepository = userParticipateSearchRepository;
    }

    /**
     * Save a userParticipate.
     *
     * @param userParticipate the entity to save
     * @return the persisted entity
     */
    @Override
    public UserParticipate save(UserParticipate userParticipate) {
        log.debug("Request to save UserParticipate : {}", userParticipate);
        UserParticipate result = userParticipateRepository.save(userParticipate);
        userParticipateSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userParticipates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserParticipate> findAll(Pageable pageable) {
        log.debug("Request to get all UserParticipates");
        return userParticipateRepository.findAll(pageable);
    }

    /**
     * Get one userParticipate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserParticipate findOne(Long id) {
        log.debug("Request to get UserParticipate : {}", id);
        return userParticipateRepository.findOne(id);
    }

    /**
     * Delete the userParticipate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserParticipate : {}", id);
        userParticipateRepository.delete(id);
        userParticipateSearchRepository.delete(id);
    }

    /**
     * Search for the userParticipate corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserParticipate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserParticipates for query {}", query);
        Page<UserParticipate> result = userParticipateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
