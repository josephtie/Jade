package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DetailDocAch;
import com.ashergadiel.manage.repository.DetailDocAchRepository;
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

/**
 * Integration tests for the {@link DetailDocAchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailDocAchResourceIT {

    private static final Double DEFAULT_PRIX_UNIT = 1D;
    private static final Double UPDATED_PRIX_UNIT = 2D;

    private static final Double DEFAULT_PRIXUNITNET = 1D;
    private static final Double UPDATED_PRIXUNITNET = 2D;

    private static final Double DEFAULT_MONTLIGNE = 1D;
    private static final Double UPDATED_MONTLIGNE = 2D;

    private static final Double DEFAULT_QTE_UNIT = 1D;
    private static final Double UPDATED_QTE_UNIT = 2D;

    private static final Double DEFAULT_REMISE = 1D;
    private static final Double UPDATED_REMISE = 2D;

    private static final Double DEFAULT_QUANTITECOLIS = 1D;
    private static final Double UPDATED_QUANTITECOLIS = 2D;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/detail-doc-aches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailDocAchRepository detailDocAchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailDocAchMockMvc;

    private DetailDocAch detailDocAch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDocAch createEntity(EntityManager em) {
        DetailDocAch detailDocAch = new DetailDocAch()
            .prixUnit(DEFAULT_PRIX_UNIT)
            .prixunitnet(DEFAULT_PRIXUNITNET)
            .montligne(DEFAULT_MONTLIGNE)
            .qteUnit(DEFAULT_QTE_UNIT)
            .remise(DEFAULT_REMISE)
            .quantitecolis(DEFAULT_QUANTITECOLIS)
            .designation(DEFAULT_DESIGNATION);
        return detailDocAch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDocAch createUpdatedEntity(EntityManager em) {
        DetailDocAch detailDocAch = new DetailDocAch()
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);
        return detailDocAch;
    }

    @BeforeEach
    public void initTest() {
        detailDocAch = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailDocAch() throws Exception {
        int databaseSizeBeforeCreate = detailDocAchRepository.findAll().size();
        // Create the DetailDocAch
        restDetailDocAchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isCreated());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeCreate + 1);
        DetailDocAch testDetailDocAch = detailDocAchList.get(detailDocAchList.size() - 1);
        assertThat(testDetailDocAch.getPrixUnit()).isEqualTo(DEFAULT_PRIX_UNIT);
        assertThat(testDetailDocAch.getPrixunitnet()).isEqualTo(DEFAULT_PRIXUNITNET);
        assertThat(testDetailDocAch.getMontligne()).isEqualTo(DEFAULT_MONTLIGNE);
        assertThat(testDetailDocAch.getQteUnit()).isEqualTo(DEFAULT_QTE_UNIT);
        assertThat(testDetailDocAch.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testDetailDocAch.getQuantitecolis()).isEqualTo(DEFAULT_QUANTITECOLIS);
        assertThat(testDetailDocAch.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createDetailDocAchWithExistingId() throws Exception {
        // Create the DetailDocAch with an existing ID
        detailDocAch.setId(1L);

        int databaseSizeBeforeCreate = detailDocAchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailDocAchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isBadRequest());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrixUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocAchRepository.findAll().size();
        // set the field null
        detailDocAch.setPrixUnit(null);

        // Create the DetailDocAch, which fails.

        restDetailDocAchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isBadRequest());

        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontligneIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocAchRepository.findAll().size();
        // set the field null
        detailDocAch.setMontligne(null);

        // Create the DetailDocAch, which fails.

        restDetailDocAchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isBadRequest());

        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQteUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocAchRepository.findAll().size();
        // set the field null
        detailDocAch.setQteUnit(null);

        // Create the DetailDocAch, which fails.

        restDetailDocAchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isBadRequest());

        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailDocAches() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        // Get all the detailDocAchList
        restDetailDocAchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailDocAch.getId().intValue())))
            .andExpect(jsonPath("$.[*].prixUnit").value(hasItem(DEFAULT_PRIX_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixunitnet").value(hasItem(DEFAULT_PRIXUNITNET.doubleValue())))
            .andExpect(jsonPath("$.[*].montligne").value(hasItem(DEFAULT_MONTLIGNE.doubleValue())))
            .andExpect(jsonPath("$.[*].qteUnit").value(hasItem(DEFAULT_QTE_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantitecolis").value(hasItem(DEFAULT_QUANTITECOLIS.doubleValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getDetailDocAch() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        // Get the detailDocAch
        restDetailDocAchMockMvc
            .perform(get(ENTITY_API_URL_ID, detailDocAch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailDocAch.getId().intValue()))
            .andExpect(jsonPath("$.prixUnit").value(DEFAULT_PRIX_UNIT.doubleValue()))
            .andExpect(jsonPath("$.prixunitnet").value(DEFAULT_PRIXUNITNET.doubleValue()))
            .andExpect(jsonPath("$.montligne").value(DEFAULT_MONTLIGNE.doubleValue()))
            .andExpect(jsonPath("$.qteUnit").value(DEFAULT_QTE_UNIT.doubleValue()))
            .andExpect(jsonPath("$.remise").value(DEFAULT_REMISE.doubleValue()))
            .andExpect(jsonPath("$.quantitecolis").value(DEFAULT_QUANTITECOLIS.doubleValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getNonExistingDetailDocAch() throws Exception {
        // Get the detailDocAch
        restDetailDocAchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailDocAch() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();

        // Update the detailDocAch
        DetailDocAch updatedDetailDocAch = detailDocAchRepository.findById(detailDocAch.getId()).get();
        // Disconnect from session so that the updates on updatedDetailDocAch are not directly saved in db
        em.detach(updatedDetailDocAch);
        updatedDetailDocAch
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);

        restDetailDocAchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailDocAch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailDocAch))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
        DetailDocAch testDetailDocAch = detailDocAchList.get(detailDocAchList.size() - 1);
        assertThat(testDetailDocAch.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailDocAch.getPrixunitnet()).isEqualTo(UPDATED_PRIXUNITNET);
        assertThat(testDetailDocAch.getMontligne()).isEqualTo(UPDATED_MONTLIGNE);
        assertThat(testDetailDocAch.getQteUnit()).isEqualTo(UPDATED_QTE_UNIT);
        assertThat(testDetailDocAch.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testDetailDocAch.getQuantitecolis()).isEqualTo(UPDATED_QUANTITECOLIS);
        assertThat(testDetailDocAch.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailDocAch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDocAch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDocAch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocAch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailDocAchWithPatch() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();

        // Update the detailDocAch using partial update
        DetailDocAch partialUpdatedDetailDocAch = new DetailDocAch();
        partialUpdatedDetailDocAch.setId(detailDocAch.getId());

        partialUpdatedDetailDocAch.prixUnit(UPDATED_PRIX_UNIT).quantitecolis(UPDATED_QUANTITECOLIS);

        restDetailDocAchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDocAch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDocAch))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
        DetailDocAch testDetailDocAch = detailDocAchList.get(detailDocAchList.size() - 1);
        assertThat(testDetailDocAch.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailDocAch.getPrixunitnet()).isEqualTo(DEFAULT_PRIXUNITNET);
        assertThat(testDetailDocAch.getMontligne()).isEqualTo(DEFAULT_MONTLIGNE);
        assertThat(testDetailDocAch.getQteUnit()).isEqualTo(DEFAULT_QTE_UNIT);
        assertThat(testDetailDocAch.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testDetailDocAch.getQuantitecolis()).isEqualTo(UPDATED_QUANTITECOLIS);
        assertThat(testDetailDocAch.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateDetailDocAchWithPatch() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();

        // Update the detailDocAch using partial update
        DetailDocAch partialUpdatedDetailDocAch = new DetailDocAch();
        partialUpdatedDetailDocAch.setId(detailDocAch.getId());

        partialUpdatedDetailDocAch
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);

        restDetailDocAchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDocAch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDocAch))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
        DetailDocAch testDetailDocAch = detailDocAchList.get(detailDocAchList.size() - 1);
        assertThat(testDetailDocAch.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailDocAch.getPrixunitnet()).isEqualTo(UPDATED_PRIXUNITNET);
        assertThat(testDetailDocAch.getMontligne()).isEqualTo(UPDATED_MONTLIGNE);
        assertThat(testDetailDocAch.getQteUnit()).isEqualTo(UPDATED_QTE_UNIT);
        assertThat(testDetailDocAch.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testDetailDocAch.getQuantitecolis()).isEqualTo(UPDATED_QUANTITECOLIS);
        assertThat(testDetailDocAch.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailDocAch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDocAch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDocAch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailDocAch() throws Exception {
        int databaseSizeBeforeUpdate = detailDocAchRepository.findAll().size();
        detailDocAch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocAchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailDocAch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDocAch in the database
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailDocAch() throws Exception {
        // Initialize the database
        detailDocAchRepository.saveAndFlush(detailDocAch);

        int databaseSizeBeforeDelete = detailDocAchRepository.findAll().size();

        // Delete the detailDocAch
        restDetailDocAchMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailDocAch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailDocAch> detailDocAchList = detailDocAchRepository.findAll();
        assertThat(detailDocAchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
