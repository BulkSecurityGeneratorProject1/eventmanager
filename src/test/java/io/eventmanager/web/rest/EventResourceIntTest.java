package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.Event;
import io.eventmanager.repository.EventRepository;
import io.eventmanager.service.EventService;
import io.eventmanager.repository.search.EventSearchRepository;
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
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class EventResourceIntTest {

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_PLACES = 1;
    private static final Integer UPDATED_NUMBER_OF_PLACES = 2;

    private static final Integer DEFAULT_NUMBER_OF_PLACES_REMAINING = 1;
    private static final Integer UPDATED_NUMBER_OF_PLACES_REMAINING = 2;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final ZonedDateTime DEFAULT_START_EVENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_EVENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_EVENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_EVENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIVATE_EVENT = false;
    private static final Boolean UPDATED_PRIVATE_EVENT = true;

    private static final Boolean DEFAULT_STATUS_EVENT = false;
    private static final Boolean UPDATED_STATUS_EVENT = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventSearchRepository eventSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .event(DEFAULT_EVENT)
            .description(DEFAULT_DESCRIPTION)
            .numberOfPlaces(DEFAULT_NUMBER_OF_PLACES)
            .numberOfPlacesRemaining(DEFAULT_NUMBER_OF_PLACES_REMAINING)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .startEvent(DEFAULT_START_EVENT)
            .endEvent(DEFAULT_END_EVENT)
            .imageUrl(DEFAULT_IMAGE_URL)
            .others(DEFAULT_OTHERS)
            .privateEvent(DEFAULT_PRIVATE_EVENT)
            .statusEvent(DEFAULT_STATUS_EVENT)
            .created(DEFAULT_CREATED);
        return event;
    }

    @Before
    public void initTest() {
        eventSearchRepository.deleteAll();
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getNumberOfPlaces()).isEqualTo(DEFAULT_NUMBER_OF_PLACES);
        assertThat(testEvent.getNumberOfPlacesRemaining()).isEqualTo(DEFAULT_NUMBER_OF_PLACES_REMAINING);
        assertThat(testEvent.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEvent.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEvent.getStartEvent()).isEqualTo(DEFAULT_START_EVENT);
        assertThat(testEvent.getEndEvent()).isEqualTo(DEFAULT_END_EVENT);
        assertThat(testEvent.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testEvent.getOthers()).isEqualTo(DEFAULT_OTHERS);
        assertThat(testEvent.isPrivateEvent()).isEqualTo(DEFAULT_PRIVATE_EVENT);
        assertThat(testEvent.isStatusEvent()).isEqualTo(DEFAULT_STATUS_EVENT);
        assertThat(testEvent.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Event in Elasticsearch
        Event eventEs = eventSearchRepository.findOne(testEvent.getId());
        assertThat(eventEs).isEqualToComparingFieldByField(testEvent);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEvent(null);

        // Create the Event, which fails.

        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numberOfPlaces").value(hasItem(DEFAULT_NUMBER_OF_PLACES)))
            .andExpect(jsonPath("$.[*].numberOfPlacesRemaining").value(hasItem(DEFAULT_NUMBER_OF_PLACES_REMAINING)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].startEvent").value(hasItem(sameInstant(DEFAULT_START_EVENT))))
            .andExpect(jsonPath("$.[*].endEvent").value(hasItem(sameInstant(DEFAULT_END_EVENT))))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].privateEvent").value(hasItem(DEFAULT_PRIVATE_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].statusEvent").value(hasItem(DEFAULT_STATUS_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.numberOfPlaces").value(DEFAULT_NUMBER_OF_PLACES))
            .andExpect(jsonPath("$.numberOfPlacesRemaining").value(DEFAULT_NUMBER_OF_PLACES_REMAINING))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.startEvent").value(sameInstant(DEFAULT_START_EVENT)))
            .andExpect(jsonPath("$.endEvent").value(sameInstant(DEFAULT_END_EVENT)))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS.toString()))
            .andExpect(jsonPath("$.privateEvent").value(DEFAULT_PRIVATE_EVENT.booleanValue()))
            .andExpect(jsonPath("$.statusEvent").value(DEFAULT_STATUS_EVENT.booleanValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventService.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findOne(event.getId());
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .event(UPDATED_EVENT)
            .description(UPDATED_DESCRIPTION)
            .numberOfPlaces(UPDATED_NUMBER_OF_PLACES)
            .numberOfPlacesRemaining(UPDATED_NUMBER_OF_PLACES_REMAINING)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .startEvent(UPDATED_START_EVENT)
            .endEvent(UPDATED_END_EVENT)
            .imageUrl(UPDATED_IMAGE_URL)
            .others(UPDATED_OTHERS)
            .privateEvent(UPDATED_PRIVATE_EVENT)
            .statusEvent(UPDATED_STATUS_EVENT)
            .created(UPDATED_CREATED);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getNumberOfPlaces()).isEqualTo(UPDATED_NUMBER_OF_PLACES);
        assertThat(testEvent.getNumberOfPlacesRemaining()).isEqualTo(UPDATED_NUMBER_OF_PLACES_REMAINING);
        assertThat(testEvent.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEvent.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEvent.getStartEvent()).isEqualTo(UPDATED_START_EVENT);
        assertThat(testEvent.getEndEvent()).isEqualTo(UPDATED_END_EVENT);
        assertThat(testEvent.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testEvent.getOthers()).isEqualTo(UPDATED_OTHERS);
        assertThat(testEvent.isPrivateEvent()).isEqualTo(UPDATED_PRIVATE_EVENT);
        assertThat(testEvent.isStatusEvent()).isEqualTo(UPDATED_STATUS_EVENT);
        assertThat(testEvent.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Event in Elasticsearch
        Event eventEs = eventSearchRepository.findOne(testEvent.getId());
        assertThat(eventEs).isEqualToComparingFieldByField(testEvent);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventService.save(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eventExistsInEs = eventSearchRepository.exists(event.getId());
        assertThat(eventExistsInEs).isFalse();

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEvent() throws Exception {
        // Initialize the database
        eventService.save(event);

        // Search the event
        restEventMockMvc.perform(get("/api/_search/events?query=id:" + event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numberOfPlaces").value(hasItem(DEFAULT_NUMBER_OF_PLACES)))
            .andExpect(jsonPath("$.[*].numberOfPlacesRemaining").value(hasItem(DEFAULT_NUMBER_OF_PLACES_REMAINING)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].startEvent").value(hasItem(sameInstant(DEFAULT_START_EVENT))))
            .andExpect(jsonPath("$.[*].endEvent").value(hasItem(sameInstant(DEFAULT_END_EVENT))))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].privateEvent").value(hasItem(DEFAULT_PRIVATE_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].statusEvent").value(hasItem(DEFAULT_STATUS_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
