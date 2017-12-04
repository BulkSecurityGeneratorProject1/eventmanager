package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.Community;
import io.eventmanager.repository.CommunityRepository;
import io.eventmanager.service.CommunityService;
import io.eventmanager.repository.search.CommunitySearchRepository;
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
 * Test class for the CommunityResource REST controller.
 *
 * @see CommunityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class CommunityResourceIntTest {

    private static final String DEFAULT_COMMUNITY = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIVATE_GROUP = false;
    private static final Boolean UPDATED_PRIVATE_GROUP = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunitySearchRepository communitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommunityMockMvc;

    private Community community;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommunityResource communityResource = new CommunityResource(communityService);
        this.restCommunityMockMvc = MockMvcBuilders.standaloneSetup(communityResource)
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
    public static Community createEntity(EntityManager em) {
        Community community = new Community()
            .community(DEFAULT_COMMUNITY)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .privateGroup(DEFAULT_PRIVATE_GROUP)
            .created(DEFAULT_CREATED);
        return community;
    }

    @Before
    public void initTest() {
        communitySearchRepository.deleteAll();
        community = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunity() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(community)))
            .andExpect(status().isCreated());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate + 1);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCommunity()).isEqualTo(DEFAULT_COMMUNITY);
        assertThat(testCommunity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommunity.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCommunity.isPrivateGroup()).isEqualTo(DEFAULT_PRIVATE_GROUP);
        assertThat(testCommunity.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Community in Elasticsearch
        Community communityEs = communitySearchRepository.findOne(testCommunity.getId());
        assertThat(communityEs).isEqualToComparingFieldByField(testCommunity);
    }

    @Test
    @Transactional
    public void createCommunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community with an existing ID
        community.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(community)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommunityIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCommunity(null);

        // Create the Community, which fails.

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(community)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunities() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].community").value(hasItem(DEFAULT_COMMUNITY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].privateGroup").value(hasItem(DEFAULT_PRIVATE_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", community.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(community.getId().intValue()))
            .andExpect(jsonPath("$.community").value(DEFAULT_COMMUNITY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.privateGroup").value(DEFAULT_PRIVATE_GROUP.booleanValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingCommunity() throws Exception {
        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunity() throws Exception {
        // Initialize the database
        communityService.save(community);

        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Update the community
        Community updatedCommunity = communityRepository.findOne(community.getId());
        // Disconnect from session so that the updates on updatedCommunity are not directly saved in db
        em.detach(updatedCommunity);
        updatedCommunity
            .community(UPDATED_COMMUNITY)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .privateGroup(UPDATED_PRIVATE_GROUP)
            .created(UPDATED_CREATED);

        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunity)))
            .andExpect(status().isOk());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCommunity()).isEqualTo(UPDATED_COMMUNITY);
        assertThat(testCommunity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommunity.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCommunity.isPrivateGroup()).isEqualTo(UPDATED_PRIVATE_GROUP);
        assertThat(testCommunity.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Community in Elasticsearch
        Community communityEs = communitySearchRepository.findOne(testCommunity.getId());
        assertThat(communityEs).isEqualToComparingFieldByField(testCommunity);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunity() throws Exception {
        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Create the Community

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(community)))
            .andExpect(status().isCreated());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommunity() throws Exception {
        // Initialize the database
        communityService.save(community);

        int databaseSizeBeforeDelete = communityRepository.findAll().size();

        // Get the community
        restCommunityMockMvc.perform(delete("/api/communities/{id}", community.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean communityExistsInEs = communitySearchRepository.exists(community.getId());
        assertThat(communityExistsInEs).isFalse();

        // Validate the database is empty
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCommunity() throws Exception {
        // Initialize the database
        communityService.save(community);

        // Search the community
        restCommunityMockMvc.perform(get("/api/_search/communities?query=id:" + community.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].community").value(hasItem(DEFAULT_COMMUNITY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].privateGroup").value(hasItem(DEFAULT_PRIVATE_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Community.class);
        Community community1 = new Community();
        community1.setId(1L);
        Community community2 = new Community();
        community2.setId(community1.getId());
        assertThat(community1).isEqualTo(community2);
        community2.setId(2L);
        assertThat(community1).isNotEqualTo(community2);
        community1.setId(null);
        assertThat(community1).isNotEqualTo(community2);
    }
}
