package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.Belong;

import io.eventmanager.repository.BelongRepository;
import io.eventmanager.repository.search.BelongSearchRepository;
import io.eventmanager.web.rest.errors.BadRequestAlertException;
import io.eventmanager.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Belong.
 */
@RestController
@RequestMapping("/api")
public class BelongResource {

    private final Logger log = LoggerFactory.getLogger(BelongResource.class);

    private static final String ENTITY_NAME = "belong";

    private final BelongRepository belongRepository;

    private final BelongSearchRepository belongSearchRepository;

    public BelongResource(BelongRepository belongRepository, BelongSearchRepository belongSearchRepository) {
        this.belongRepository = belongRepository;
        this.belongSearchRepository = belongSearchRepository;
    }

    /**
     * POST  /belongs : Create a new belong.
     *
     * @param belong the belong to create
     * @return the ResponseEntity with status 201 (Created) and with body the new belong, or with status 400 (Bad Request) if the belong has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/belongs")
    @Timed
    public ResponseEntity<Belong> createBelong(@Valid @RequestBody Belong belong) throws URISyntaxException {
        log.debug("REST request to save Belong : {}", belong);
        if (belong.getId() != null) {
            throw new BadRequestAlertException("A new belong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Belong result = belongRepository.save(belong);
        belongSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/belongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /belongs : Updates an existing belong.
     *
     * @param belong the belong to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated belong,
     * or with status 400 (Bad Request) if the belong is not valid,
     * or with status 500 (Internal Server Error) if the belong couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/belongs")
    @Timed
    public ResponseEntity<Belong> updateBelong(@Valid @RequestBody Belong belong) throws URISyntaxException {
        log.debug("REST request to update Belong : {}", belong);
        if (belong.getId() == null) {
            return createBelong(belong);
        }
        Belong result = belongRepository.save(belong);
        belongSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, belong.getId().toString()))
            .body(result);
    }

    /**
     * GET  /belongs : get all the belongs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of belongs in body
     */
    @GetMapping("/belongs")
    @Timed
    public List<Belong> getAllBelongs() {
        log.debug("REST request to get all Belongs");
        return belongRepository.findAll();
        }

    /**
     * GET  /belongs/:id : get the "id" belong.
     *
     * @param id the id of the belong to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the belong, or with status 404 (Not Found)
     */
    @GetMapping("/belongs/{id}")
    @Timed
    public ResponseEntity<Belong> getBelong(@PathVariable Long id) {
        log.debug("REST request to get Belong : {}", id);
        Belong belong = belongRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(belong));
    }

    /**
     * DELETE  /belongs/:id : delete the "id" belong.
     *
     * @param id the id of the belong to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/belongs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBelong(@PathVariable Long id) {
        log.debug("REST request to delete Belong : {}", id);
        belongRepository.delete(id);
        belongSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/belongs?query=:query : search for the belong corresponding
     * to the query.
     *
     * @param query the query of the belong search
     * @return the result of the search
     */
    @GetMapping("/_search/belongs")
    @Timed
    public List<Belong> searchBelongs(@RequestParam String query) {
        log.debug("REST request to search Belongs for query {}", query);
        return StreamSupport
            .stream(belongSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
