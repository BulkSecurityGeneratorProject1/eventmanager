package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.MediaType;
import io.eventmanager.repository.MediaTypeRepository;
import io.eventmanager.repository.search.MediaTypeSearchRepository;
import io.eventmanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.eventmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MediaTypeResource REST controller.
 *
 * @see MediaTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class MediaTypeResourceIntTest {

    private static final String DEFAULT_MEDIA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private MediaTypeRepository mediaTypeRepository;

    @Autowired
    private MediaTypeSearchRepository mediaTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaTypeMockMvc;

    private MediaType mediaType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaTypeResource mediaTypeResource = new MediaTypeResource(mediaTypeRepository, mediaTypeSearchRepository);
        this.restMediaTypeMockMvc = MockMvcBuilders.standaloneSetup(mediaTypeResource)
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
    public static MediaType createEntity(EntityManager em) {
        MediaType mediaType = new MediaType()
            .mediaType(DEFAULT_MEDIA_TYPE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return mediaType;
    }

    @Before
    public void initTest() {
        mediaTypeSearchRepository.deleteAll();
        mediaType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaType() throws Exception {
        int databaseSizeBeforeCreate = mediaTypeRepository.findAll().size();

        // Create the MediaType
        restMediaTypeMockMvc.perform(post("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isCreated());

        // Validate the MediaType in the database
        List<io.eventmanager.domain.MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        io.eventmanager.domain.MediaType testMediaType = mediaTypeList.get(mediaTypeList.size() - 1);
        assertThat(testMediaType.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testMediaType.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);

        // Validate the MediaType in Elasticsearch
        io.eventmanager.domain.MediaType mediaTypeEs = mediaTypeSearchRepository.findOne(testMediaType.getId());
        assertThat(mediaTypeEs).isEqualToComparingFieldByField(testMediaType);
    }

    @Test
    @Transactional
    public void createMediaTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaTypeRepository.findAll().size();

        // Create the MediaType with an existing ID
        mediaType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaTypeMockMvc.perform(post("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isBadRequest());

        // Validate the MediaType in the database
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMediaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaTypeRepository.findAll().size();
        // set the field null
        mediaType.setMediaType(null);

        // Create the MediaType, which fails.

        restMediaTypeMockMvc.perform(post("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isBadRequest());

        List<io.eventmanager.domain.MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMediaTypes() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);

        // Get all the mediaTypeList
        restMediaTypeMockMvc.perform(get("/api/media-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getMediaType() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);

        // Get the mediaType
        restMediaTypeMockMvc.perform(get("/api/media-types/{id}", mediaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mediaType.getId().intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaType() throws Exception {
        // Get the mediaType
        restMediaTypeMockMvc.perform(get("/api/media-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaType() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);
        mediaTypeSearchRepository.save(mediaType);
        int databaseSizeBeforeUpdate = mediaTypeRepository.findAll().size();

        // Update the mediaType
        io.eventmanager.domain.MediaType updatedMediaType = mediaTypeRepository.findOne(mediaType.getId());
        // Disconnect from session so that the updates on updatedMediaType are not directly saved in db
        em.detach(updatedMediaType);
        updatedMediaType
            .mediaType(UPDATED_MEDIA_TYPE)
            .imageUrl(UPDATED_IMAGE_URL);

        restMediaTypeMockMvc.perform(put("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMediaType)))
            .andExpect(status().isOk());

        // Validate the MediaType in the database
        List<io.eventmanager.domain.MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeUpdate);
        io.eventmanager.domain.MediaType testMediaType = mediaTypeList.get(mediaTypeList.size() - 1);
        assertThat(testMediaType.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testMediaType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);

        // Validate the MediaType in Elasticsearch
        io.eventmanager.domain.MediaType mediaTypeEs = mediaTypeSearchRepository.findOne(testMediaType.getId());
        assertThat(mediaTypeEs).isEqualToComparingFieldByField(testMediaType);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaType() throws Exception {
        int databaseSizeBeforeUpdate = mediaTypeRepository.findAll().size();

        // Create the MediaType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaTypeMockMvc.perform(put("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isCreated());

        // Validate the MediaType in the database
        List<io.eventmanager.domain.MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMediaType() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);
        mediaTypeSearchRepository.save(mediaType);
        int databaseSizeBeforeDelete = mediaTypeRepository.findAll().size();

        // Get the mediaType
        restMediaTypeMockMvc.perform(delete("/api/media-types/{id}", mediaType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mediaTypeExistsInEs = mediaTypeSearchRepository.exists(mediaType.getId());
        assertThat(mediaTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<io.eventmanager.domain.MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMediaType() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);
        mediaTypeSearchRepository.save(mediaType);

        // Search the mediaType
        restMediaTypeMockMvc.perform(get("/api/_search/media-types?query=id:" + mediaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaType.class);
        MediaType mediaType1 = new MediaType();
        mediaType1.setId(1L);
        MediaType mediaType2 = new MediaType();
        mediaType2.setId(mediaType1.getId());
        assertThat(mediaType1).isEqualTo(mediaType2);
        mediaType2.setId(2L);
        assertThat(mediaType1).isNotEqualTo(mediaType2);
        mediaType1.setId(null);
        assertThat(mediaType1).isNotEqualTo(mediaType2);
    }
}
