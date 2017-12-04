package io.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.eventmanager.domain.MediaType;

import io.eventmanager.repository.MediaTypeRepository;
import io.eventmanager.repository.search.MediaTypeSearchRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MediaType.
 */
@RestController
@RequestMapping("/api")
public class MediaTypeResource {

    private final Logger log = LoggerFactory.getLogger(MediaTypeResource.class);

    private static final String ENTITY_NAME = "mediaType";

    private final MediaTypeRepository mediaTypeRepository;

    private final MediaTypeSearchRepository mediaTypeSearchRepository;

    public MediaTypeResource(MediaTypeRepository mediaTypeRepository, MediaTypeSearchRepository mediaTypeSearchRepository) {
        this.mediaTypeRepository = mediaTypeRepository;
        this.mediaTypeSearchRepository = mediaTypeSearchRepository;
    }

    /**
     * POST  /media-types : Create a new mediaType.
     *
     * @param mediaType the mediaType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaType, or with status 400 (Bad Request) if the mediaType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media-types")
    @Timed
    public ResponseEntity<MediaType> createMediaType(@Valid @RequestBody MediaType mediaType) throws URISyntaxException {
        log.debug("REST request to save MediaType : {}", mediaType);
        if (mediaType.getId() != null) {
            throw new BadRequestAlertException("A new mediaType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaType result = mediaTypeRepository.save(mediaType);
        mediaTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/media-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media-types : Updates an existing mediaType.
     *
     * @param mediaType the mediaType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaType,
     * or with status 400 (Bad Request) if the mediaType is not valid,
     * or with status 500 (Internal Server Error) if the mediaType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media-types")
    @Timed
    public ResponseEntity<MediaType> updateMediaType(@Valid @RequestBody MediaType mediaType) throws URISyntaxException {
        log.debug("REST request to update MediaType : {}", mediaType);
        if (mediaType.getId() == null) {
            return createMediaType(mediaType);
        }
        MediaType result = mediaTypeRepository.save(mediaType);
        mediaTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media-types : get all the mediaTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mediaTypes in body
     */
    @GetMapping("/media-types")
    @Timed
    public ResponseEntity<List<MediaType>> getAllMediaTypes(Pageable pageable) {
        log.debug("REST request to get a page of MediaTypes");
        Page<MediaType> page = mediaTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /media-types/:id : get the "id" mediaType.
     *
     * @param id the id of the mediaType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaType, or with status 404 (Not Found)
     */
    @GetMapping("/media-types/{id}")
    @Timed
    public ResponseEntity<MediaType> getMediaType(@PathVariable Long id) {
        log.debug("REST request to get MediaType : {}", id);
        MediaType mediaType = mediaTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mediaType));
    }

    /**
     * DELETE  /media-types/:id : delete the "id" mediaType.
     *
     * @param id the id of the mediaType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMediaType(@PathVariable Long id) {
        log.debug("REST request to delete MediaType : {}", id);
        mediaTypeRepository.delete(id);
        mediaTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/media-types?query=:query : search for the mediaType corresponding
     * to the query.
     *
     * @param query the query of the mediaType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/media-types")
    @Timed
    public ResponseEntity<List<MediaType>> searchMediaTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MediaTypes for query {}", query);
        Page<MediaType> page = mediaTypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/media-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
