package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.EventMedia;
import io.eventmanager.repository.EventMediaRepository;
import io.eventmanager.repository.search.EventMediaSearchRepository;
import io.eventmanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.eventmanager.web.rest.TestUtil.sameInstant;
import static io.eventmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventMediaResource REST controller.
 *
 * @see EventMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class EventMediaResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EventMediaRepository eventMediaRepository;

    @Autowired
    private EventMediaSearchRepository eventMediaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventMediaMockMvc;

    private EventMedia eventMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventMediaResource eventMediaResource = new EventMediaResource(eventMediaRepository, eventMediaSearchRepository);
        this.restEventMediaMockMvc = MockMvcBuilders.standaloneSetup(eventMediaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventMedia createEntity(EntityManager em) {
        EventMedia eventMedia = new EventMedia()
            .created(DEFAULT_CREATED);
        return eventMedia;
    }

    @Before
    public void initTest() {
        eventMediaSearchRepository.deleteAll();
        eventMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventMedia() throws Exception {
        int databaseSizeBeforeCreate = eventMediaRepository.findAll().size();

        // Create the EventMedia
        restEventMediaMockMvc.perform(post("/api/event-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMedia)))
            .andExpect(status().isCreated());

        // Validate the EventMedia in the database
        List<EventMedia> eventMediaList = eventMediaRepository.findAll();
        assertThat(eventMediaList).hasSize(databaseSizeBeforeCreate + 1);
        EventMedia testEventMedia = eventMediaList.get(eventMediaList.size() - 1);
        assertThat(testEventMedia.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the EventMedia in Elasticsearch
        EventMedia eventMediaEs = eventMediaSearchRepository.findOne(testEventMedia.getId());
        assertThat(eventMediaEs).isEqualToComparingFieldByField(testEventMedia);
    }

    @Test
    @Transactional
    public void createEventMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventMediaRepository.findAll().size();

        // Create the EventMedia with an existing ID
        eventMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMediaMockMvc.perform(post("/api/event-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMedia)))
            .andExpect(status().isBadRequest());

        // Validate the EventMedia in the database
        List<EventMedia> eventMediaList = eventMediaRepository.findAll();
        assertThat(eventMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEventMedias() throws Exception {
        // Initialize the database
        eventMediaRepository.saveAndFlush(eventMedia);

        // Get all the eventMediaList
        restEventMediaMockMvc.perform(get("/api/event-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getEventMedia() throws Exception {
        // Initialize the database
        eventMediaRepository.saveAndFlush(eventMedia);

        // Get the eventMedia
        restEventMediaMockMvc.perform(get("/api/event-medias/{id}", eventMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventMedia.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingEventMedia() throws Exception {
        // Get the eventMedia
        restEventMediaMockMvc.perform(get("/api/event-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventMedia() throws Exception {
        // Initialize the database
        eventMediaRepository.saveAndFlush(eventMedia);
        eventMediaSearchRepository.save(eventMedia);
        int databaseSizeBeforeUpdate = eventMediaRepository.findAll().size();

        // Update the eventMedia
        EventMedia updatedEventMedia = eventMediaRepository.findOne(eventMedia.getId());
        // Disconnect from session so that the updates on updatedEventMedia are not directly saved in db
        em.detach(updatedEventMedia);
        updatedEventMedia
            .created(UPDATED_CREATED);

        restEventMediaMockMvc.perform(put("/api/event-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventMedia)))
            .andExpect(status().isOk());

        // Validate the EventMedia in the database
        List<EventMedia> eventMediaList = eventMediaRepository.findAll();
        assertThat(eventMediaList).hasSize(databaseSizeBeforeUpdate);
        EventMedia testEventMedia = eventMediaList.get(eventMediaList.size() - 1);
        assertThat(testEventMedia.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the EventMedia in Elasticsearch
        EventMedia eventMediaEs = eventMediaSearchRepository.findOne(testEventMedia.getId());
        assertThat(eventMediaEs).isEqualToComparingFieldByField(testEventMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingEventMedia() throws Exception {
        int databaseSizeBeforeUpdate = eventMediaRepository.findAll().size();

        // Create the EventMedia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventMediaMockMvc.perform(put("/api/event-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMedia)))
            .andExpect(status().isCreated());

        // Validate the EventMedia in the database
        List<EventMedia> eventMediaList = eventMediaRepository.findAll();
        assertThat(eventMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEventMedia() throws Exception {
        // Initialize the database
        eventMediaRepository.saveAndFlush(eventMedia);
        eventMediaSearchRepository.save(eventMedia);
        int databaseSizeBeforeDelete = eventMediaRepository.findAll().size();

        // Get the eventMedia
        restEventMediaMockMvc.perform(delete("/api/event-medias/{id}", eventMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eventMediaExistsInEs = eventMediaSearchRepository.exists(eventMedia.getId());
        assertThat(eventMediaExistsInEs).isFalse();

        // Validate the database is empty
        List<EventMedia> eventMediaList = eventMediaRepository.findAll();
        assertThat(eventMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEventMedia() throws Exception {
        // Initialize the database
        eventMediaRepository.saveAndFlush(eventMedia);
        eventMediaSearchRepository.save(eventMedia);

        // Search the eventMedia
        restEventMediaMockMvc.perform(get("/api/_search/event-medias?query=id:" + eventMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventMedia.class);
        EventMedia eventMedia1 = new EventMedia();
        eventMedia1.setId(1L);
        EventMedia eventMedia2 = new EventMedia();
        eventMedia2.setId(eventMedia1.getId());
        assertThat(eventMedia1).isEqualTo(eventMedia2);
        eventMedia2.setId(2L);
        assertThat(eventMedia1).isNotEqualTo(eventMedia2);
        eventMedia1.setId(null);
        assertThat(eventMedia1).isNotEqualTo(eventMedia2);
    }
}
