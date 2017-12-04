package io.eventmanager.service.impl;

import io.eventmanager.service.CommunityService;
import io.eventmanager.domain.Community;
import io.eventmanager.repository.CommunityRepository;
import io.eventmanager.repository.search.CommunitySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Community.
 */
@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{

    private final Logger log = LoggerFactory.getLogger(CommunityServiceImpl.class);

    private final CommunityRepository communityRepository;

    private final CommunitySearchRepository communitySearchRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunitySearchRepository communitySearchRepository) {
        this.communityRepository = communityRepository;
        this.communitySearchRepository = communitySearchRepository;
    }

    /**
     * Save a community.
     *
     * @param community the entity to save
     * @return the persisted entity
     */
    @Override
    public Community save(Community community) {
        log.debug("Request to save Community : {}", community);
        Community result = communityRepository.save(community);
        communitySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the communities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Community> findAll(Pageable pageable) {
        log.debug("Request to get all Communities");
        return communityRepository.findAll(pageable);
    }

    /**
     * Get one community by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Community findOne(Long id) {
        log.debug("Request to get Community : {}", id);
        return communityRepository.findOne(id);
    }

    /**
     * Delete the community by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Community : {}", id);
        communityRepository.delete(id);
        communitySearchRepository.delete(id);
    }

    /**
     * Search for the community corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Community> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Communities for query {}", query);
        Page<Community> result = communitySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
