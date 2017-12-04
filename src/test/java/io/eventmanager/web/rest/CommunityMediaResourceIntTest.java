package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.CommunityMedia;
import io.eventmanager.repository.CommunityMediaRepository;
import io.eventmanager.repository.search.CommunityMediaSearchRepository;
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
 * Test class for the CommunityMediaResource REST controller.
 *
 * @see CommunityMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class CommunityMediaResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommunityMediaRepository communityMediaRepository;

    @Autowired
    private CommunityMediaSearchRepository communityMediaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommunityMediaMockMvc;

    private CommunityMedia communityMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommunityMediaResource communityMediaResource = new CommunityMediaResource(communityMediaRepository, communityMediaSearchRepository);
        this.restCommunityMediaMockMvc = MockMvcBuilders.standaloneSetup(communityMediaResource)
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
    public static CommunityMedia createEntity(EntityManager em) {
        CommunityMedia communityMedia = new CommunityMedia()
            .created(DEFAULT_CREATED);
        return communityMedia;
    }

    @Before
    public void initTest() {
        communityMediaSearchRepository.deleteAll();
        communityMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunityMedia() throws Exception {
        int databaseSizeBeforeCreate = communityMediaRepository.findAll().size();

        // Create the CommunityMedia
        restCommunityMediaMockMvc.perform(post("/api/community-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityMedia)))
            .andExpect(status().isCreated());

        // Validate the CommunityMedia in the database
        List<CommunityMedia> communityMediaList = communityMediaRepository.findAll();
        assertThat(communityMediaList).hasSize(databaseSizeBeforeCreate + 1);
        CommunityMedia testCommunityMedia = communityMediaList.get(communityMediaList.size() - 1);
        assertThat(testCommunityMedia.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the CommunityMedia in Elasticsearch
        CommunityMedia communityMediaEs = communityMediaSearchRepository.findOne(testCommunityMedia.getId());
        assertThat(communityMediaEs).isEqualToComparingFieldByField(testCommunityMedia);
    }

    @Test
    @Transactional
    public void createCommunityMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communityMediaRepository.findAll().size();

        // Create the CommunityMedia with an existing ID
        communityMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMediaMockMvc.perform(post("/api/community-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityMedia)))
            .andExpect(status().isBadRequest());

        // Validate the CommunityMedia in the database
        List<CommunityMedia> communityMediaList = communityMediaRepository.findAll();
        assertThat(communityMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommunityMedias() throws Exception {
        // Initialize the database
        communityMediaRepository.saveAndFlush(communityMedia);

        // Get all the communityMediaList
        restCommunityMediaMockMvc.perform(get("/api/community-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communityMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getCommunityMedia() throws Exception {
        // Initialize the database
        communityMediaRepository.saveAndFlush(communityMedia);

        // Get the communityMedia
        restCommunityMediaMockMvc.perform(get("/api/community-medias/{id}", communityMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(communityMedia.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingCommunityMedia() throws Exception {
        // Get the communityMedia
        restCommunityMediaMockMvc.perform(get("/api/community-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunityMedia() throws Exception {
        // Initialize the database
        communityMediaRepository.saveAndFlush(communityMedia);
        communityMediaSearchRepository.save(communityMedia);
        int databaseSizeBeforeUpdate = communityMediaRepository.findAll().size();

        // Update the communityMedia
        CommunityMedia updatedCommunityMedia = communityMediaRepository.findOne(communityMedia.getId());
        // Disconnect from session so that the updates on updatedCommunityMedia are not directly saved in db
        em.detach(updatedCommunityMedia);
        updatedCommunityMedia
            .created(UPDATED_CREATED);

        restCommunityMediaMockMvc.perform(put("/api/community-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunityMedia)))
            .andExpect(status().isOk());

        // Validate the CommunityMedia in the database
        List<CommunityMedia> communityMediaList = communityMediaRepository.findAll();
        assertThat(communityMediaList).hasSize(databaseSizeBeforeUpdate);
        CommunityMedia testCommunityMedia = communityMediaList.get(communityMediaList.size() - 1);
        assertThat(testCommunityMedia.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the CommunityMedia in Elasticsearch
        CommunityMedia communityMediaEs = communityMediaSearchRepository.findOne(testCommunityMedia.getId());
        assertThat(communityMediaEs).isEqualToComparingFieldByField(testCommunityMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunityMedia() throws Exception {
        int databaseSizeBeforeUpdate = communityMediaRepository.findAll().size();

        // Create the CommunityMedia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommunityMediaMockMvc.perform(put("/api/community-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityMedia)))
            .andExpect(status().isCreated());

        // Validate the CommunityMedia in the database
        List<CommunityMedia> communityMediaList = communityMediaRepository.findAll();
        assertThat(communityMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommunityMedia() throws Exception {
        // Initialize the database
        communityMediaRepository.saveAndFlush(communityMedia);
        communityMediaSearchRepository.save(communityMedia);
        int databaseSizeBeforeDelete = communityMediaRepository.findAll().size();

        // Get the communityMedia
        restCommunityMediaMockMvc.perform(delete("/api/community-medias/{id}", communityMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean communityMediaExistsInEs = communityMediaSearchRepository.exists(communityMedia.getId());
        assertThat(communityMediaExistsInEs).isFalse();

        // Validate the database is empty
        List<CommunityMedia> communityMediaList = communityMediaRepository.findAll();
        assertThat(communityMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCommunityMedia() throws Exception {
        // Initialize the database
        communityMediaRepository.saveAndFlush(communityMedia);
        communityMediaSearchRepository.save(communityMedia);

        // Search the communityMedia
        restCommunityMediaMockMvc.perform(get("/api/_search/community-medias?query=id:" + communityMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communityMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityMedia.class);
        CommunityMedia communityMedia1 = new CommunityMedia();
        communityMedia1.setId(1L);
        CommunityMedia communityMedia2 = new CommunityMedia();
        communityMedia2.setId(communityMedia1.getId());
        assertThat(communityMedia1).isEqualTo(communityMedia2);
        communityMedia2.setId(2L);
        assertThat(communityMedia1).isNotEqualTo(communityMedia2);
        communityMedia1.setId(null);
        assertThat(communityMedia1).isNotEqualTo(communityMedia2);
    }
}
