package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.UserParticipate;
import io.eventmanager.service.UserParticipateService;
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
 * REST controller for managing UserParticipate.
 */
@RestController
@RequestMapping("/api")
public class UserParticipateResource {

    private final Logger log = LoggerFactory.getLogger(UserParticipateResource.class);

    private static final String ENTITY_NAME = "userParticipate";

    private final UserParticipateService userParticipateService;

    public UserParticipateResource(UserParticipateService userParticipateService) {
        this.userParticipateService = userParticipateService;
    }

    /**
     * POST  /user-participates : Create a new userParticipate.
     *
     * @param userParticipate the userParticipate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userParticipate, or with status 400 (Bad Request) if the userParticipate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-participates")
    @Timed
    public ResponseEntity<UserParticipate> createUserParticipate(@Valid @RequestBody UserParticipate userParticipate) throws URISyntaxException {
        log.debug("REST request to save UserParticipate : {}", userParticipate);
        if (userParticipate.getId() != null) {
            throw new BadRequestAlertException("A new userParticipate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserParticipate result = userParticipateService.save(userParticipate);
        return ResponseEntity.created(new URI("/api/user-participates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-participates : Updates an existing userParticipate.
     *
     * @param userParticipate the userParticipate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userParticipate,
     * or with status 400 (Bad Request) if the userParticipate is not valid,
     * or with status 500 (Internal Server Error) if the userParticipate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-participates")
    @Timed
    public ResponseEntity<UserParticipate> updateUserParticipate(@Valid @RequestBody UserParticipate userParticipate) throws URISyntaxException {
        log.debug("REST request to update UserParticipate : {}", userParticipate);
        if (userParticipate.getId() == null) {
            return createUserParticipate(userParticipate);
        }
        UserParticipate result = userParticipateService.save(userParticipate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userParticipate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-participates : get all the userParticipates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userParticipates in body
     */
    @GetMapping("/user-participates")
    @Timed
    public ResponseEntity<List<UserParticipate>> getAllUserParticipates(Pageable pageable) {
        log.debug("REST request to get a page of UserParticipates");
        Page<UserParticipate> page = userParticipateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-participates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-participates/:id : get the "id" userParticipate.
     *
     * @param id the id of the userParticipate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userParticipate, or with status 404 (Not Found)
     */
    @GetMapping("/user-participates/{id}")
    @Timed
    public ResponseEntity<UserParticipate> getUserParticipate(@PathVariable Long id) {
        log.debug("REST request to get UserParticipate : {}", id);
        UserParticipate userParticipate = userParticipateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userParticipate));
    }

    /**
     * DELETE  /user-participates/:id : delete the "id" userParticipate.
     *
     * @param id the id of the userParticipate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-participates/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserParticipate(@PathVariable Long id) {
        log.debug("REST request to delete UserParticipate : {}", id);
        userParticipateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-participates?query=:query : search for the userParticipate corresponding
     * to the query.
     *
     * @param query the query of the userParticipate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-participates")
    @Timed
    public ResponseEntity<List<UserParticipate>> searchUserParticipates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserParticipates for query {}", query);
        Page<UserParticipate> page = userParticipateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-participates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
