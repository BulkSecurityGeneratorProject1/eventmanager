package io.eventmanager.web.rest;

import io.eventmanager.EventManagerApp;

import io.eventmanager.domain.Invitation;
import io.eventmanager.repository.InvitationRepository;
import io.eventmanager.service.InvitationService;
import io.eventmanager.repository.search.InvitationSearchRepository;
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
 * Test class for the InvitationResource REST controller.
 *
 * @see InvitationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagerApp.class)
public class InvitationResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_CORPORATE = "AAAAAAAAAA";
    private static final String UPDATED_CORPORATE = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_VOICE_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_VOICE_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEND_TO_EMAIL = false;
    private static final Boolean UPDATED_SEND_TO_EMAIL = true;

    private static final Boolean DEFAULT_SEND_TO_CALL_PHONE = false;
    private static final Boolean UPDATED_SEND_TO_CALL_PHONE = true;

    private static final Boolean DEFAULT_SEND_TO_VOICE_MOBILE_PHONE = false;
    private static final Boolean UPDATED_SEND_TO_VOICE_MOBILE_PHONE = true;

    private static final Boolean DEFAULT_SEND_TO_SMS_MOBILE_PHONE = false;
    private static final Boolean UPDATED_SEND_TO_SMS_MOBILE_PHONE = true;

    private static final ZonedDateTime DEFAULT_PERIOD_TO_SEND = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PERIOD_TO_SEND = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_STATUS_INVITATION = false;
    private static final Boolean UPDATED_STATUS_INVITATION = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private InvitationSearchRepository invitationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvitationMockMvc;

    private Invitation invitation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvitationResource invitationResource = new InvitationResource(invitationService);
        this.restInvitationMockMvc = MockMvcBuilders.standaloneSetup(invitationResource)
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
    public static Invitation createEntity(EntityManager em) {
        Invitation invitation = new Invitation()
            .userId(DEFAULT_USER_ID)
            .corporate(DEFAULT_CORPORATE)
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .message(DEFAULT_MESSAGE)
            .voiceMessage(DEFAULT_VOICE_MESSAGE)
            .sendToEmail(DEFAULT_SEND_TO_EMAIL)
            .sendToCallPhone(DEFAULT_SEND_TO_CALL_PHONE)
            .sendToVoiceMobilePhone(DEFAULT_SEND_TO_VOICE_MOBILE_PHONE)
            .sendToSMSMobilePhone(DEFAULT_SEND_TO_SMS_MOBILE_PHONE)
            .periodToSend(DEFAULT_PERIOD_TO_SEND)
            .statusInvitation(DEFAULT_STATUS_INVITATION)
            .created(DEFAULT_CREATED);
        return invitation;
    }

    @Before
    public void initTest() {
        invitationSearchRepository.deleteAll();
        invitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitation() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation
        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isCreated());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate + 1);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInvitation.getCorporate()).isEqualTo(DEFAULT_CORPORATE);
        assertThat(testInvitation.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testInvitation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInvitation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testInvitation.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testInvitation.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testInvitation.getVoiceMessage()).isEqualTo(DEFAULT_VOICE_MESSAGE);
        assertThat(testInvitation.isSendToEmail()).isEqualTo(DEFAULT_SEND_TO_EMAIL);
        assertThat(testInvitation.isSendToCallPhone()).isEqualTo(DEFAULT_SEND_TO_CALL_PHONE);
        assertThat(testInvitation.isSendToVoiceMobilePhone()).isEqualTo(DEFAULT_SEND_TO_VOICE_MOBILE_PHONE);
        assertThat(testInvitation.isSendToSMSMobilePhone()).isEqualTo(DEFAULT_SEND_TO_SMS_MOBILE_PHONE);
        assertThat(testInvitation.getPeriodToSend()).isEqualTo(DEFAULT_PERIOD_TO_SEND);
        assertThat(testInvitation.isStatusInvitation()).isEqualTo(DEFAULT_STATUS_INVITATION);
        assertThat(testInvitation.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Invitation in Elasticsearch
        Invitation invitationEs = invitationSearchRepository.findOne(testInvitation.getId());
        assertThat(invitationEs).isEqualToComparingFieldByField(testInvitation);
    }

    @Test
    @Transactional
    public void createInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation with an existing ID
        invitation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setUserId(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorporateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setCorporate(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setFullName(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setEmail(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setPhone(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobilePhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setMobilePhone(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setMessage(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoiceMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setVoiceMessage(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitations() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get all the invitationList
        restInvitationMockMvc.perform(get("/api/invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].corporate").value(hasItem(DEFAULT_CORPORATE.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].voiceMessage").value(hasItem(DEFAULT_VOICE_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].sendToEmail").value(hasItem(DEFAULT_SEND_TO_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToCallPhone").value(hasItem(DEFAULT_SEND_TO_CALL_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToVoiceMobilePhone").value(hasItem(DEFAULT_SEND_TO_VOICE_MOBILE_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToSMSMobilePhone").value(hasItem(DEFAULT_SEND_TO_SMS_MOBILE_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].periodToSend").value(hasItem(sameInstant(DEFAULT_PERIOD_TO_SEND))))
            .andExpect(jsonPath("$.[*].statusInvitation").value(hasItem(DEFAULT_STATUS_INVITATION.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitation.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.corporate").value(DEFAULT_CORPORATE.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.voiceMessage").value(DEFAULT_VOICE_MESSAGE.toString()))
            .andExpect(jsonPath("$.sendToEmail").value(DEFAULT_SEND_TO_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.sendToCallPhone").value(DEFAULT_SEND_TO_CALL_PHONE.booleanValue()))
            .andExpect(jsonPath("$.sendToVoiceMobilePhone").value(DEFAULT_SEND_TO_VOICE_MOBILE_PHONE.booleanValue()))
            .andExpect(jsonPath("$.sendToSMSMobilePhone").value(DEFAULT_SEND_TO_SMS_MOBILE_PHONE.booleanValue()))
            .andExpect(jsonPath("$.periodToSend").value(sameInstant(DEFAULT_PERIOD_TO_SEND)))
            .andExpect(jsonPath("$.statusInvitation").value(DEFAULT_STATUS_INVITATION.booleanValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingInvitation() throws Exception {
        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitation() throws Exception {
        // Initialize the database
        invitationService.save(invitation);

        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Update the invitation
        Invitation updatedInvitation = invitationRepository.findOne(invitation.getId());
        // Disconnect from session so that the updates on updatedInvitation are not directly saved in db
        em.detach(updatedInvitation);
        updatedInvitation
            .userId(UPDATED_USER_ID)
            .corporate(UPDATED_CORPORATE)
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .message(UPDATED_MESSAGE)
            .voiceMessage(UPDATED_VOICE_MESSAGE)
            .sendToEmail(UPDATED_SEND_TO_EMAIL)
            .sendToCallPhone(UPDATED_SEND_TO_CALL_PHONE)
            .sendToVoiceMobilePhone(UPDATED_SEND_TO_VOICE_MOBILE_PHONE)
            .sendToSMSMobilePhone(UPDATED_SEND_TO_SMS_MOBILE_PHONE)
            .periodToSend(UPDATED_PERIOD_TO_SEND)
            .statusInvitation(UPDATED_STATUS_INVITATION)
            .created(UPDATED_CREATED);

        restInvitationMockMvc.perform(put("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvitation)))
            .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInvitation.getCorporate()).isEqualTo(UPDATED_CORPORATE);
        assertThat(testInvitation.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testInvitation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvitation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testInvitation.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testInvitation.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testInvitation.getVoiceMessage()).isEqualTo(UPDATED_VOICE_MESSAGE);
        assertThat(testInvitation.isSendToEmail()).isEqualTo(UPDATED_SEND_TO_EMAIL);
        assertThat(testInvitation.isSendToCallPhone()).isEqualTo(UPDATED_SEND_TO_CALL_PHONE);
        assertThat(testInvitation.isSendToVoiceMobilePhone()).isEqualTo(UPDATED_SEND_TO_VOICE_MOBILE_PHONE);
        assertThat(testInvitation.isSendToSMSMobilePhone()).isEqualTo(UPDATED_SEND_TO_SMS_MOBILE_PHONE);
        assertThat(testInvitation.getPeriodToSend()).isEqualTo(UPDATED_PERIOD_TO_SEND);
        assertThat(testInvitation.isStatusInvitation()).isEqualTo(UPDATED_STATUS_INVITATION);
        assertThat(testInvitation.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Invitation in Elasticsearch
        Invitation invitationEs = invitationSearchRepository.findOne(testInvitation.getId());
        assertThat(invitationEs).isEqualToComparingFieldByField(testInvitation);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitation() throws Exception {
        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Create the Invitation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvitationMockMvc.perform(put("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitation)))
            .andExpect(status().isCreated());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvitation() throws Exception {
        // Initialize the database
        invitationService.save(invitation);

        int databaseSizeBeforeDelete = invitationRepository.findAll().size();

        // Get the invitation
        restInvitationMockMvc.perform(delete("/api/invitations/{id}", invitation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean invitationExistsInEs = invitationSearchRepository.exists(invitation.getId());
        assertThat(invitationExistsInEs).isFalse();

        // Validate the database is empty
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInvitation() throws Exception {
        // Initialize the database
        invitationService.save(invitation);

        // Search the invitation
        restInvitationMockMvc.perform(get("/api/_search/invitations?query=id:" + invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].corporate").value(hasItem(DEFAULT_CORPORATE.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].voiceMessage").value(hasItem(DEFAULT_VOICE_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].sendToEmail").value(hasItem(DEFAULT_SEND_TO_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToCallPhone").value(hasItem(DEFAULT_SEND_TO_CALL_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToVoiceMobilePhone").value(hasItem(DEFAULT_SEND_TO_VOICE_MOBILE_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].sendToSMSMobilePhone").value(hasItem(DEFAULT_SEND_TO_SMS_MOBILE_PHONE.booleanValue())))
            .andExpect(jsonPath("$.[*].periodToSend").value(hasItem(sameInstant(DEFAULT_PERIOD_TO_SEND))))
            .andExpect(jsonPath("$.[*].statusInvitation").value(hasItem(DEFAULT_STATUS_INVITATION.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invitation.class);
        Invitation invitation1 = new Invitation();
        invitation1.setId(1L);
        Invitation invitation2 = new Invitation();
        invitation2.setId(invitation1.getId());
        assertThat(invitation1).isEqualTo(invitation2);
        invitation2.setId(2L);
        assertThat(invitation1).isNotEqualTo(invitation2);
        invitation1.setId(null);
        assertThat(invitation1).isNotEqualTo(invitation2);
    }
}
