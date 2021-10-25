package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.Societe;
import com.ashergadiel.manage.repository.SocieteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SocieteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocieteResourceIT {

    private static final String DEFAULT_RAISONSOC = "AAAAAAAAAA";
    private static final String UPDATED_RAISONSOC = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLE = "AAAAAAAAAA";
    private static final String UPDATED_SIGLE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITEPP = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITEPP = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSGEO = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSGEO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMJURI = "AAAAAAAAAA";
    private static final String UPDATED_FORMJURI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BP = "AAAAAAAAAA";
    private static final String UPDATED_BP = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_CCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_CCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final byte[] DEFAULT_FILE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_URL_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_URL_LOGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/societes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocieteMockMvc;

    private Societe societe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createEntity(EntityManager em) {
        Societe societe = new Societe()
            .raisonsoc(DEFAULT_RAISONSOC)
            .sigle(DEFAULT_SIGLE)
            .activitepp(DEFAULT_ACTIVITEPP)
            .adressgeo(DEFAULT_ADRESSGEO)
            .formjuri(DEFAULT_FORMJURI)
            .telephone(DEFAULT_TELEPHONE)
            .bp(DEFAULT_BP)
            .registreCce(DEFAULT_REGISTRE_CCE)
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .commune(DEFAULT_COMMUNE)
            .email(DEFAULT_EMAIL)
            .actif(DEFAULT_ACTIF)
            .fileData(DEFAULT_FILE_DATA)
            .fileDataContentType(DEFAULT_FILE_DATA_CONTENT_TYPE)
            .urlLogo(DEFAULT_URL_LOGO);
        return societe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createUpdatedEntity(EntityManager em) {
        Societe societe = new Societe()
            .raisonsoc(UPDATED_RAISONSOC)
            .sigle(UPDATED_SIGLE)
            .activitepp(UPDATED_ACTIVITEPP)
            .adressgeo(UPDATED_ADRESSGEO)
            .formjuri(UPDATED_FORMJURI)
            .telephone(UPDATED_TELEPHONE)
            .bp(UPDATED_BP)
            .registreCce(UPDATED_REGISTRE_CCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .commune(UPDATED_COMMUNE)
            .email(UPDATED_EMAIL)
            .actif(UPDATED_ACTIF)
            .fileData(UPDATED_FILE_DATA)
            .fileDataContentType(UPDATED_FILE_DATA_CONTENT_TYPE)
            .urlLogo(UPDATED_URL_LOGO);
        return societe;
    }

    @BeforeEach
    public void initTest() {
        societe = createEntity(em);
    }

    @Test
    @Transactional
    void createSociete() throws Exception {
        int databaseSizeBeforeCreate = societeRepository.findAll().size();
        // Create the Societe
        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isCreated());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate + 1);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getRaisonsoc()).isEqualTo(DEFAULT_RAISONSOC);
        assertThat(testSociete.getSigle()).isEqualTo(DEFAULT_SIGLE);
        assertThat(testSociete.getActivitepp()).isEqualTo(DEFAULT_ACTIVITEPP);
        assertThat(testSociete.getAdressgeo()).isEqualTo(DEFAULT_ADRESSGEO);
        assertThat(testSociete.getFormjuri()).isEqualTo(DEFAULT_FORMJURI);
        assertThat(testSociete.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSociete.getBp()).isEqualTo(DEFAULT_BP);
        assertThat(testSociete.getRegistreCce()).isEqualTo(DEFAULT_REGISTRE_CCE);
        assertThat(testSociete.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testSociete.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testSociete.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testSociete.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSociete.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testSociete.getFileData()).isEqualTo(DEFAULT_FILE_DATA);
        assertThat(testSociete.getFileDataContentType()).isEqualTo(DEFAULT_FILE_DATA_CONTENT_TYPE);
        assertThat(testSociete.getUrlLogo()).isEqualTo(DEFAULT_URL_LOGO);
    }

    @Test
    @Transactional
    void createSocieteWithExistingId() throws Exception {
        // Create the Societe with an existing ID
        societe.setId(1L);

        int databaseSizeBeforeCreate = societeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRaisonsocIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setRaisonsoc(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSigleIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setSigle(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiviteppIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setActivitepp(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setTelephone(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setPays(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setVille(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setCommune(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setEmail(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocietes() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId().intValue())))
            .andExpect(jsonPath("$.[*].raisonsoc").value(hasItem(DEFAULT_RAISONSOC)))
            .andExpect(jsonPath("$.[*].sigle").value(hasItem(DEFAULT_SIGLE)))
            .andExpect(jsonPath("$.[*].activitepp").value(hasItem(DEFAULT_ACTIVITEPP)))
            .andExpect(jsonPath("$.[*].adressgeo").value(hasItem(DEFAULT_ADRESSGEO)))
            .andExpect(jsonPath("$.[*].formjuri").value(hasItem(DEFAULT_FORMJURI)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP)))
            .andExpect(jsonPath("$.[*].registreCce").value(hasItem(DEFAULT_REGISTRE_CCE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].fileDataContentType").value(hasItem(DEFAULT_FILE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileData").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_DATA))))
            .andExpect(jsonPath("$.[*].urlLogo").value(hasItem(DEFAULT_URL_LOGO)));
    }

    @Test
    @Transactional
    void getSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get the societe
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL_ID, societe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societe.getId().intValue()))
            .andExpect(jsonPath("$.raisonsoc").value(DEFAULT_RAISONSOC))
            .andExpect(jsonPath("$.sigle").value(DEFAULT_SIGLE))
            .andExpect(jsonPath("$.activitepp").value(DEFAULT_ACTIVITEPP))
            .andExpect(jsonPath("$.adressgeo").value(DEFAULT_ADRESSGEO))
            .andExpect(jsonPath("$.formjuri").value(DEFAULT_FORMJURI))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.bp").value(DEFAULT_BP))
            .andExpect(jsonPath("$.registreCce").value(DEFAULT_REGISTRE_CCE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.fileDataContentType").value(DEFAULT_FILE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileData").value(Base64Utils.encodeToString(DEFAULT_FILE_DATA)))
            .andExpect(jsonPath("$.urlLogo").value(DEFAULT_URL_LOGO));
    }

    @Test
    @Transactional
    void getNonExistingSociete() throws Exception {
        // Get the societe
        restSocieteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Update the societe
        Societe updatedSociete = societeRepository.findById(societe.getId()).get();
        // Disconnect from session so that the updates on updatedSociete are not directly saved in db
        em.detach(updatedSociete);
        updatedSociete
            .raisonsoc(UPDATED_RAISONSOC)
            .sigle(UPDATED_SIGLE)
            .activitepp(UPDATED_ACTIVITEPP)
            .adressgeo(UPDATED_ADRESSGEO)
            .formjuri(UPDATED_FORMJURI)
            .telephone(UPDATED_TELEPHONE)
            .bp(UPDATED_BP)
            .registreCce(UPDATED_REGISTRE_CCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .commune(UPDATED_COMMUNE)
            .email(UPDATED_EMAIL)
            .actif(UPDATED_ACTIF)
            .fileData(UPDATED_FILE_DATA)
            .fileDataContentType(UPDATED_FILE_DATA_CONTENT_TYPE)
            .urlLogo(UPDATED_URL_LOGO);

        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSociete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getRaisonsoc()).isEqualTo(UPDATED_RAISONSOC);
        assertThat(testSociete.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testSociete.getActivitepp()).isEqualTo(UPDATED_ACTIVITEPP);
        assertThat(testSociete.getAdressgeo()).isEqualTo(UPDATED_ADRESSGEO);
        assertThat(testSociete.getFormjuri()).isEqualTo(UPDATED_FORMJURI);
        assertThat(testSociete.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSociete.getBp()).isEqualTo(UPDATED_BP);
        assertThat(testSociete.getRegistreCce()).isEqualTo(UPDATED_REGISTRE_CCE);
        assertThat(testSociete.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testSociete.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testSociete.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testSociete.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSociete.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testSociete.getFileData()).isEqualTo(UPDATED_FILE_DATA);
        assertThat(testSociete.getFileDataContentType()).isEqualTo(UPDATED_FILE_DATA_CONTENT_TYPE);
        assertThat(testSociete.getUrlLogo()).isEqualTo(UPDATED_URL_LOGO);
    }

    @Test
    @Transactional
    void putNonExistingSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete
            .raisonsoc(UPDATED_RAISONSOC)
            .sigle(UPDATED_SIGLE)
            .activitepp(UPDATED_ACTIVITEPP)
            .formjuri(UPDATED_FORMJURI)
            .telephone(UPDATED_TELEPHONE)
            .commune(UPDATED_COMMUNE)
            .actif(UPDATED_ACTIF);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getRaisonsoc()).isEqualTo(UPDATED_RAISONSOC);
        assertThat(testSociete.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testSociete.getActivitepp()).isEqualTo(UPDATED_ACTIVITEPP);
        assertThat(testSociete.getAdressgeo()).isEqualTo(DEFAULT_ADRESSGEO);
        assertThat(testSociete.getFormjuri()).isEqualTo(UPDATED_FORMJURI);
        assertThat(testSociete.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSociete.getBp()).isEqualTo(DEFAULT_BP);
        assertThat(testSociete.getRegistreCce()).isEqualTo(DEFAULT_REGISTRE_CCE);
        assertThat(testSociete.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testSociete.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testSociete.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testSociete.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSociete.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testSociete.getFileData()).isEqualTo(DEFAULT_FILE_DATA);
        assertThat(testSociete.getFileDataContentType()).isEqualTo(DEFAULT_FILE_DATA_CONTENT_TYPE);
        assertThat(testSociete.getUrlLogo()).isEqualTo(DEFAULT_URL_LOGO);
    }

    @Test
    @Transactional
    void fullUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete
            .raisonsoc(UPDATED_RAISONSOC)
            .sigle(UPDATED_SIGLE)
            .activitepp(UPDATED_ACTIVITEPP)
            .adressgeo(UPDATED_ADRESSGEO)
            .formjuri(UPDATED_FORMJURI)
            .telephone(UPDATED_TELEPHONE)
            .bp(UPDATED_BP)
            .registreCce(UPDATED_REGISTRE_CCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .commune(UPDATED_COMMUNE)
            .email(UPDATED_EMAIL)
            .actif(UPDATED_ACTIF)
            .fileData(UPDATED_FILE_DATA)
            .fileDataContentType(UPDATED_FILE_DATA_CONTENT_TYPE)
            .urlLogo(UPDATED_URL_LOGO);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getRaisonsoc()).isEqualTo(UPDATED_RAISONSOC);
        assertThat(testSociete.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testSociete.getActivitepp()).isEqualTo(UPDATED_ACTIVITEPP);
        assertThat(testSociete.getAdressgeo()).isEqualTo(UPDATED_ADRESSGEO);
        assertThat(testSociete.getFormjuri()).isEqualTo(UPDATED_FORMJURI);
        assertThat(testSociete.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSociete.getBp()).isEqualTo(UPDATED_BP);
        assertThat(testSociete.getRegistreCce()).isEqualTo(UPDATED_REGISTRE_CCE);
        assertThat(testSociete.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testSociete.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testSociete.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testSociete.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSociete.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testSociete.getFileData()).isEqualTo(UPDATED_FILE_DATA);
        assertThat(testSociete.getFileDataContentType()).isEqualTo(UPDATED_FILE_DATA_CONTENT_TYPE);
        assertThat(testSociete.getUrlLogo()).isEqualTo(UPDATED_URL_LOGO);
    }

    @Test
    @Transactional
    void patchNonExistingSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();
        societe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeDelete = societeRepository.findAll().size();

        // Delete the societe
        restSocieteMockMvc
            .perform(delete(ENTITY_API_URL_ID, societe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
