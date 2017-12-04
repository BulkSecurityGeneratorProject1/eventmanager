package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.Community;
import io.eventmanager.service.CommunityService;
import io.eventmanager.web.rest.errors.BadRequestAlertException;
import io.eventmanager.web.rest.util.HeaderUtil;
import io.eventmanager.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Community.
 */
@RestController
@RequestMapping("/api")
public class CommunityResource {

    private final Logger log = LoggerFactory.getLogger(CommunityResource.class);

    private static final String ENTITY_NAME = "community";

    private final CommunityService communityService;

    public CommunityResource(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * POST  /communities : Create a new community.
     *
     * @param community the community to create
     * @return the ResponseEntity with status 201 (Created) and with body the new community, or with status 400 (Bad Request) if the community has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communities")
    @Timed
    public ResponseEntity<Community> createCommunity(@Valid @RequestBody Community community) throws URISyntaxException {
        log.debug("REST request to save Community : {}", community);
        if (community.getId() != null) {
            throw new BadRequestAlertException("A new community cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Community result = communityService.save(community);
        return ResponseEntity.created(new URI("/api/communities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communities : Updates an existing community.
     *
     * @param community the community to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated community,
     * or with status 400 (Bad Request) if the community is not valid,
     * or with status 500 (Internal Server Error) if the community couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communities")
    @Timed
    public ResponseEntity<Community> updateCommunity(@Valid @RequestBody Community community) throws URISyntaxException {
        log.debug("REST request to update Community : {}", community);
        if (community.getId() == null) {
            return createCommunity(community);
        }
        Community result = communityService.save(community);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, community.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communities : get all the communities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of communities in body
     */
    @GetMapping("/communities")
    @Timed
    public ResponseEntity<List<Community>> getAllCommunities(Pageable pageable) {
        log.debug("REST request to get a page of Communities");
        Page<Community> page = communityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/communities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /communities/:id : get the "id" community.
     *
     * @param id the id of the community to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the community, or with status 404 (Not Found)
     */
    @GetMapping("/communities/{id}")
    @Timed
    public ResponseEntity<Community> getCommunity(@PathVariable Long id) {
        log.debug("REST request to get Community : {}", id);
        Community community = communityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(community));
    }

    /**
     * DELETE  /communities/:id : delete the "id" community.
     *
     * @param id the id of the community to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        log.debug("REST request to delete Community : {}", id);
        communityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/communities?query=:query : search for the community corresponding
     * to the query.
     *
     * @param query the query of the community search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/communities")
    @Timed
    public ResponseEntity<List<Community>> searchCommunities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Communities for query {}", query);
        Page<Community> page = communityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/communities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
