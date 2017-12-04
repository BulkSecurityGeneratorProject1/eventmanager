package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.CommunityMedia;

import io.eventmanager.repository.CommunityMediaRepository;
import io.eventmanager.repository.search.CommunityMediaSearchRepository;
import io.eventmanager.web.rest.errors.BadRequestAlertException;
import io.eventmanager.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CommunityMedia.
 */
@RestController
@RequestMapping("/api")
public class CommunityMediaResource {

    private final Logger log = LoggerFactory.getLogger(CommunityMediaResource.class);

    private static final String ENTITY_NAME = "communityMedia";

    private final CommunityMediaRepository communityMediaRepository;

    private final CommunityMediaSearchRepository communityMediaSearchRepository;

    public CommunityMediaResource(CommunityMediaRepository communityMediaRepository, CommunityMediaSearchRepository communityMediaSearchRepository) {
        this.communityMediaRepository = communityMediaRepository;
        this.communityMediaSearchRepository = communityMediaSearchRepository;
    }

    /**
     * POST  /community-medias : Create a new communityMedia.
     *
     * @param communityMedia the communityMedia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communityMedia, or with status 400 (Bad Request) if the communityMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/community-medias")
    @Timed
    public ResponseEntity<CommunityMedia> createCommunityMedia(@RequestBody CommunityMedia communityMedia) throws URISyntaxException {
        log.debug("REST request to save CommunityMedia : {}", communityMedia);
        if (communityMedia.getId() != null) {
            throw new BadRequestAlertException("A new communityMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunityMedia result = communityMediaRepository.save(communityMedia);
        communityMediaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/community-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /community-medias : Updates an existing communityMedia.
     *
     * @param communityMedia the communityMedia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communityMedia,
     * or with status 400 (Bad Request) if the communityMedia is not valid,
     * or with status 500 (Internal Server Error) if the communityMedia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/community-medias")
    @Timed
    public ResponseEntity<CommunityMedia> updateCommunityMedia(@RequestBody CommunityMedia communityMedia) throws URISyntaxException {
        log.debug("REST request to update CommunityMedia : {}", communityMedia);
        if (communityMedia.getId() == null) {
            return createCommunityMedia(communityMedia);
        }
        CommunityMedia result = communityMediaRepository.save(communityMedia);
        communityMediaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communityMedia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /community-medias : get all the communityMedias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communityMedias in body
     */
    @GetMapping("/community-medias")
    @Timed
    public List<CommunityMedia> getAllCommunityMedias() {
        log.debug("REST request to get all CommunityMedias");
        return communityMediaRepository.findAll();
        }

    /**
     * GET  /community-medias/:id : get the "id" communityMedia.
     *
     * @param id the id of the communityMedia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communityMedia, or with status 404 (Not Found)
     */
    @GetMapping("/community-medias/{id}")
    @Timed
    public ResponseEntity<CommunityMedia> getCommunityMedia(@PathVariable Long id) {
        log.debug("REST request to get CommunityMedia : {}", id);
        CommunityMedia communityMedia = communityMediaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(communityMedia));
    }

    /**
     * DELETE  /community-medias/:id : delete the "id" communityMedia.
     *
     * @param id the id of the communityMedia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/community-medias/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommunityMedia(@PathVariable Long id) {
        log.debug("REST request to delete CommunityMedia : {}", id);
        communityMediaRepository.delete(id);
        communityMediaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/community-medias?query=:query : search for the communityMedia corresponding
     * to the query.
     *
     * @param query the query of the communityMedia search
     * @return the result of the search
     */
    @GetMapping("/_search/community-medias")
    @Timed
    public List<CommunityMedia> searchCommunityMedias(@RequestParam String query) {
        log.debug("REST request to search CommunityMedias for query {}", query);
        return StreamSupport
            .stream(communityMediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
