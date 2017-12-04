package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.Belong;
import io.eventmanager.repository.BelongRepository;
import io.eventmanager.repository.search.BelongSearchRepository;
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
 * Test class for the BelongResource REST controller.
 *
 * @see BelongResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class BelongResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BelongRepository belongRepository;

    @Autowired
    private BelongSearchRepository belongSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBelongMockMvc;

    private Belong belong;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BelongResource belongResource = new BelongResource(belongRepository, belongSearchRepository);
        this.restBelongMockMvc = MockMvcBuilders.standaloneSetup(belongResource)
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
    public static Belong createEntity(EntityManager em) {
        Belong belong = new Belong()
            .userId(DEFAULT_USER_ID)
            .created(DEFAULT_CREATED);
        return belong;
    }

    @Before
    public void initTest() {
        belongSearchRepository.deleteAll();
        belong = createEntity(em);
    }

    @Test
    @Transactional
    public void createBelong() throws Exception {
        int databaseSizeBeforeCreate = belongRepository.findAll().size();

        // Create the Belong
        restBelongMockMvc.perform(post("/api/belongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belong)))
            .andExpect(status().isCreated());

        // Validate the Belong in the database
        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeCreate + 1);
        Belong testBelong = belongList.get(belongList.size() - 1);
        assertThat(testBelong.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBelong.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Belong in Elasticsearch
        Belong belongEs = belongSearchRepository.findOne(testBelong.getId());
        assertThat(belongEs).isEqualToComparingFieldByField(testBelong);
    }

    @Test
    @Transactional
    public void createBelongWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = belongRepository.findAll().size();

        // Create the Belong with an existing ID
        belong.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelongMockMvc.perform(post("/api/belongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belong)))
            .andExpect(status().isBadRequest());

        // Validate the Belong in the database
        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = belongRepository.findAll().size();
        // set the field null
        belong.setUserId(null);

        // Create the Belong, which fails.

        restBelongMockMvc.perform(post("/api/belongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belong)))
            .andExpect(status().isBadRequest());

        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBelongs() throws Exception {
        // Initialize the database
        belongRepository.saveAndFlush(belong);

        // Get all the belongList
        restBelongMockMvc.perform(get("/api/belongs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belong.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getBelong() throws Exception {
        // Initialize the database
        belongRepository.saveAndFlush(belong);

        // Get the belong
        restBelongMockMvc.perform(get("/api/belongs/{id}", belong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(belong.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingBelong() throws Exception {
        // Get the belong
        restBelongMockMvc.perform(get("/api/belongs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBelong() throws Exception {
        // Initialize the database
        belongRepository.saveAndFlush(belong);
        belongSearchRepository.save(belong);
        int databaseSizeBeforeUpdate = belongRepository.findAll().size();

        // Update the belong
        Belong updatedBelong = belongRepository.findOne(belong.getId());
        // Disconnect from session so that the updates on updatedBelong are not directly saved in db
        em.detach(updatedBelong);
        updatedBelong
            .userId(UPDATED_USER_ID)
            .created(UPDATED_CREATED);

        restBelongMockMvc.perform(put("/api/belongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBelong)))
            .andExpect(status().isOk());

        // Validate the Belong in the database
        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeUpdate);
        Belong testBelong = belongList.get(belongList.size() - 1);
        assertThat(testBelong.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBelong.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Belong in Elasticsearch
        Belong belongEs = belongSearchRepository.findOne(testBelong.getId());
        assertThat(belongEs).isEqualToComparingFieldByField(testBelong);
    }

    @Test
    @Transactional
    public void updateNonExistingBelong() throws Exception {
        int databaseSizeBeforeUpdate = belongRepository.findAll().size();

        // Create the Belong

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBelongMockMvc.perform(put("/api/belongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(belong)))
            .andExpect(status().isCreated());

        // Validate the Belong in the database
        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBelong() throws Exception {
        // Initialize the database
        belongRepository.saveAndFlush(belong);
        belongSearchRepository.save(belong);
        int databaseSizeBeforeDelete = belongRepository.findAll().size();

        // Get the belong
        restBelongMockMvc.perform(delete("/api/belongs/{id}", belong.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean belongExistsInEs = belongSearchRepository.exists(belong.getId());
        assertThat(belongExistsInEs).isFalse();

        // Validate the database is empty
        List<Belong> belongList = belongRepository.findAll();
        assertThat(belongList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBelong() throws Exception {
        // Initialize the database
        belongRepository.saveAndFlush(belong);
        belongSearchRepository.save(belong);

        // Search the belong
        restBelongMockMvc.perform(get("/api/_search/belongs?query=id:" + belong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belong.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belong.class);
        Belong belong1 = new Belong();
        belong1.setId(1L);
        Belong belong2 = new Belong();
        belong2.setId(belong1.getId());
        assertThat(belong1).isEqualTo(belong2);
        belong2.setId(2L);
        assertThat(belong1).isNotEqualTo(belong2);
        belong1.setId(null);
        assertThat(belong1).isNotEqualTo(belong2);
    }
}
