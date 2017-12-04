package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.UserParticipate;
import io.eventmanager.repository.UserParticipateRepository;
import io.eventmanager.service.UserParticipateService;
import io.eventmanager.repository.search.UserParticipateSearchRepository;
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
 * Test class for the UserParticipateResource REST controller.
 *
 * @see UserParticipateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class UserParticipateResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserParticipateRepository userParticipateRepository;

    @Autowired
    private UserParticipateService userParticipateService;

    @Autowired
    private UserParticipateSearchRepository userParticipateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserParticipateMockMvc;

    private UserParticipate userParticipate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserParticipateResource userParticipateResource = new UserParticipateResource(userParticipateService);
        this.restUserParticipateMockMvc = MockMvcBuilders.standaloneSetup(userParticipateResource)
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
    public static UserParticipate createEntity(EntityManager em) {
        UserParticipate userParticipate = new UserParticipate()
            .userId(DEFAULT_USER_ID)
            .created(DEFAULT_CREATED);
        return userParticipate;
    }

    @Before
    public void initTest() {
        userParticipateSearchRepository.deleteAll();
        userParticipate = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserParticipate() throws Exception {
        int databaseSizeBeforeCreate = userParticipateRepository.findAll().size();

        // Create the UserParticipate
        restUserParticipateMockMvc.perform(post("/api/user-participates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userParticipate)))
            .andExpect(status().isCreated());

        // Validate the UserParticipate in the database
        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeCreate + 1);
        UserParticipate testUserParticipate = userParticipateList.get(userParticipateList.size() - 1);
        assertThat(testUserParticipate.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserParticipate.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the UserParticipate in Elasticsearch
        UserParticipate userParticipateEs = userParticipateSearchRepository.findOne(testUserParticipate.getId());
        assertThat(userParticipateEs).isEqualToComparingFieldByField(testUserParticipate);
    }

    @Test
    @Transactional
    public void createUserParticipateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userParticipateRepository.findAll().size();

        // Create the UserParticipate with an existing ID
        userParticipate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserParticipateMockMvc.perform(post("/api/user-participates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userParticipate)))
            .andExpect(status().isBadRequest());

        // Validate the UserParticipate in the database
        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userParticipateRepository.findAll().size();
        // set the field null
        userParticipate.setUserId(null);

        // Create the UserParticipate, which fails.

        restUserParticipateMockMvc.perform(post("/api/user-participates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userParticipate)))
            .andExpect(status().isBadRequest());

        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserParticipates() throws Exception {
        // Initialize the database
        userParticipateRepository.saveAndFlush(userParticipate);

        // Get all the userParticipateList
        restUserParticipateMockMvc.perform(get("/api/user-participates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userParticipate.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getUserParticipate() throws Exception {
        // Initialize the database
        userParticipateRepository.saveAndFlush(userParticipate);

        // Get the userParticipate
        restUserParticipateMockMvc.perform(get("/api/user-participates/{id}", userParticipate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userParticipate.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingUserParticipate() throws Exception {
        // Get the userParticipate
        restUserParticipateMockMvc.perform(get("/api/user-participates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserParticipate() throws Exception {
        // Initialize the database
        userParticipateService.save(userParticipate);

        int databaseSizeBeforeUpdate = userParticipateRepository.findAll().size();

        // Update the userParticipate
        UserParticipate updatedUserParticipate = userParticipateRepository.findOne(userParticipate.getId());
        // Disconnect from session so that the updates on updatedUserParticipate are not directly saved in db
        em.detach(updatedUserParticipate);
        updatedUserParticipate
            .userId(UPDATED_USER_ID)
            .created(UPDATED_CREATED);

        restUserParticipateMockMvc.perform(put("/api/user-participates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserParticipate)))
            .andExpect(status().isOk());

        // Validate the UserParticipate in the database
        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeUpdate);
        UserParticipate testUserParticipate = userParticipateList.get(userParticipateList.size() - 1);
        assertThat(testUserParticipate.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserParticipate.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the UserParticipate in Elasticsearch
        UserParticipate userParticipateEs = userParticipateSearchRepository.findOne(testUserParticipate.getId());
        assertThat(userParticipateEs).isEqualToComparingFieldByField(testUserParticipate);
    }

    @Test
    @Transactional
    public void updateNonExistingUserParticipate() throws Exception {
        int databaseSizeBeforeUpdate = userParticipateRepository.findAll().size();

        // Create the UserParticipate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserParticipateMockMvc.perform(put("/api/user-participates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userParticipate)))
            .andExpect(status().isCreated());

        // Validate the UserParticipate in the database
        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserParticipate() throws Exception {
        // Initialize the database
        userParticipateService.save(userParticipate);

        int databaseSizeBeforeDelete = userParticipateRepository.findAll().size();

        // Get the userParticipate
        restUserParticipateMockMvc.perform(delete("/api/user-participates/{id}", userParticipate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userParticipateExistsInEs = userParticipateSearchRepository.exists(userParticipate.getId());
        assertThat(userParticipateExistsInEs).isFalse();

        // Validate the database is empty
        List<UserParticipate> userParticipateList = userParticipateRepository.findAll();
        assertThat(userParticipateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserParticipate() throws Exception {
        // Initialize the database
        userParticipateService.save(userParticipate);

        // Search the userParticipate
        restUserParticipateMockMvc.perform(get("/api/_search/user-participates?query=id:" + userParticipate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userParticipate.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserParticipate.class);
        UserParticipate userParticipate1 = new UserParticipate();
        userParticipate1.setId(1L);
        UserParticipate userParticipate2 = new UserParticipate();
        userParticipate2.setId(userParticipate1.getId());
        assertThat(userParticipate1).isEqualTo(userParticipate2);
        userParticipate2.setId(2L);
        assertThat(userParticipate1).isNotEqualTo(userParticipate2);
        userParticipate1.setId(null);
        assertThat(userParticipate1).isNotEqualTo(userParticipate2);
    }
}
