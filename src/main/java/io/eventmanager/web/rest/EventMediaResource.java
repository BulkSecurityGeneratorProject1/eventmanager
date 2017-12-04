package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.EventMedia;

import io.eventmanager.repository.EventMediaRepository;
import io.eventmanager.repository.search.EventMediaSearchRepository;
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
 * REST controller for managing EventMedia.
 */
@RestController
@RequestMapping("/api")
public class EventMediaResource {

    private final Logger log = LoggerFactory.getLogger(EventMediaResource.class);

    private static final String ENTITY_NAME = "eventMedia";

    private final EventMediaRepository eventMediaRepository;

    private final EventMediaSearchRepository eventMediaSearchRepository;

    public EventMediaResource(EventMediaRepository eventMediaRepository, EventMediaSearchRepository eventMediaSearchRepository) {
        this.eventMediaRepository = eventMediaRepository;
        this.eventMediaSearchRepository = eventMediaSearchRepository;
    }

    /**
     * POST  /event-medias : Create a new eventMedia.
     *
     * @param eventMedia the eventMedia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventMedia, or with status 400 (Bad Request) if the eventMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-medias")
    @Timed
    public ResponseEntity<EventMedia> createEventMedia(@RequestBody EventMedia eventMedia) throws URISyntaxException {
        log.debug("REST request to save EventMedia : {}", eventMedia);
        if (eventMedia.getId() != null) {
            throw new BadRequestAlertException("A new eventMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventMedia result = eventMediaRepository.save(eventMedia);
        eventMediaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/event-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-medias : Updates an existing eventMedia.
     *
     * @param eventMedia the eventMedia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventMedia,
     * or with status 400 (Bad Request) if the eventMedia is not valid,
     * or with status 500 (Internal Server Error) if the eventMedia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-medias")
    @Timed
    public ResponseEntity<EventMedia> updateEventMedia(@RequestBody EventMedia eventMedia) throws URISyntaxException {
        log.debug("REST request to update EventMedia : {}", eventMedia);
        if (eventMedia.getId() == null) {
            return createEventMedia(eventMedia);
        }
        EventMedia result = eventMediaRepository.save(eventMedia);
        eventMediaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventMedia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-medias : get all the eventMedias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eventMedias in body
     */
    @GetMapping("/event-medias")
    @Timed
    public List<EventMedia> getAllEventMedias() {
        log.debug("REST request to get all EventMedias");
        return eventMediaRepository.findAll();
        }

    /**
     * GET  /event-medias/:id : get the "id" eventMedia.
     *
     * @param id the id of the eventMedia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventMedia, or with status 404 (Not Found)
     */
    @GetMapping("/event-medias/{id}")
    @Timed
    public ResponseEntity<EventMedia> getEventMedia(@PathVariable Long id) {
        log.debug("REST request to get EventMedia : {}", id);
        EventMedia eventMedia = eventMediaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventMedia));
    }

    /**
     * DELETE  /event-medias/:id : delete the "id" eventMedia.
     *
     * @param id the id of the eventMedia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-medias/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventMedia(@PathVariable Long id) {
        log.debug("REST request to delete EventMedia : {}", id);
        eventMediaRepository.delete(id);
        eventMediaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/event-medias?query=:query : search for the eventMedia corresponding
     * to the query.
     *
     * @param query the query of the eventMedia search
     * @return the result of the search
     */
    @GetMapping("/_search/event-medias")
    @Timed
    public List<EventMedia> searchEventMedias(@RequestParam String query) {
        log.debug("REST request to search EventMedias for query {}", query);
        return StreamSupport
            .stream(eventMediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
