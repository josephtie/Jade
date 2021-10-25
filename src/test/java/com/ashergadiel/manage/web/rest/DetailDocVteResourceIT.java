package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DetailDocVte;
import com.ashergadiel.manage.repository.DetailDocVteRepository;
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
 * Integration tests for the {@link DetailDocVteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailDocVteResourceIT {

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

    private static final String ENTITY_API_URL = "/api/detail-doc-vtes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailDocVteRepository detailDocVteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailDocVteMockMvc;

    private DetailDocVte detailDocVte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDocVte createEntity(EntityManager em) {
        DetailDocVte detailDocVte = new DetailDocVte()
            .prixUnit(DEFAULT_PRIX_UNIT)
            .prixunitnet(DEFAULT_PRIXUNITNET)
            .montligne(DEFAULT_MONTLIGNE)
            .qteUnit(DEFAULT_QTE_UNIT)
            .remise(DEFAULT_REMISE)
            .quantitecolis(DEFAULT_QUANTITECOLIS)
            .designation(DEFAULT_DESIGNATION);
        return detailDocVte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDocVte createUpdatedEntity(EntityManager em) {
        DetailDocVte detailDocVte = new DetailDocVte()
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);
        return detailDocVte;
    }

    @BeforeEach
    public void initTest() {
        detailDocVte = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailDocVte() throws Exception {
        int databaseSizeBeforeCreate = detailDocVteRepository.findAll().size();
        // Create the DetailDocVte
        restDetailDocVteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isCreated());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeCreate + 1);
        DetailDocVte testDetailDocVte = detailDocVteList.get(detailDocVteList.size() - 1);
        assertThat(testDetailDocVte.getPrixUnit()).isEqualTo(DEFAULT_PRIX_UNIT);
        assertThat(testDetailDocVte.getPrixunitnet()).isEqualTo(DEFAULT_PRIXUNITNET);
        assertThat(testDetailDocVte.getMontligne()).isEqualTo(DEFAULT_MONTLIGNE);
        assertThat(testDetailDocVte.getQteUnit()).isEqualTo(DEFAULT_QTE_UNIT);
        assertThat(testDetailDocVte.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testDetailDocVte.getQuantitecolis()).isEqualTo(DEFAULT_QUANTITECOLIS);
        assertThat(testDetailDocVte.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createDetailDocVteWithExistingId() throws Exception {
        // Create the DetailDocVte with an existing ID
        detailDocVte.setId(1L);

        int databaseSizeBeforeCreate = detailDocVteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailDocVteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isBadRequest());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrixUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocVteRepository.findAll().size();
        // set the field null
        detailDocVte.setPrixUnit(null);

        // Create the DetailDocVte, which fails.

        restDetailDocVteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isBadRequest());

        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontligneIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocVteRepository.findAll().size();
        // set the field null
        detailDocVte.setMontligne(null);

        // Create the DetailDocVte, which fails.

        restDetailDocVteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isBadRequest());

        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQteUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDocVteRepository.findAll().size();
        // set the field null
        detailDocVte.setQteUnit(null);

        // Create the DetailDocVte, which fails.

        restDetailDocVteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isBadRequest());

        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailDocVtes() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        // Get all the detailDocVteList
        restDetailDocVteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailDocVte.getId().intValue())))
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
    void getDetailDocVte() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        // Get the detailDocVte
        restDetailDocVteMockMvc
            .perform(get(ENTITY_API_URL_ID, detailDocVte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailDocVte.getId().intValue()))
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
    void getNonExistingDetailDocVte() throws Exception {
        // Get the detailDocVte
        restDetailDocVteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailDocVte() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();

        // Update the detailDocVte
        DetailDocVte updatedDetailDocVte = detailDocVteRepository.findById(detailDocVte.getId()).get();
        // Disconnect from session so that the updates on updatedDetailDocVte are not directly saved in db
        em.detach(updatedDetailDocVte);
        updatedDetailDocVte
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);

        restDetailDocVteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailDocVte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailDocVte))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
        DetailDocVte testDetailDocVte = detailDocVteList.get(detailDocVteList.size() - 1);
        assertThat(testDetailDocVte.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailDocVte.getPrixunitnet()).isEqualTo(UPDATED_PRIXUNITNET);
        assertThat(testDetailDocVte.getMontligne()).isEqualTo(UPDATED_MONTLIGNE);
        assertThat(testDetailDocVte.getQteUnit()).isEqualTo(UPDATED_QTE_UNIT);
        assertThat(testDetailDocVte.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testDetailDocVte.getQuantitecolis()).isEqualTo(UPDATED_QUANTITECOLIS);
        assertThat(testDetailDocVte.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailDocVte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDocVte))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDocVte))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDocVte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailDocVteWithPatch() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();

        // Update the detailDocVte using partial update
        DetailDocVte partialUpdatedDetailDocVte = new DetailDocVte();
        partialUpdatedDetailDocVte.setId(detailDocVte.getId());

        partialUpdatedDetailDocVte.remise(UPDATED_REMISE).designation(UPDATED_DESIGNATION);

        restDetailDocVteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDocVte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDocVte))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
        DetailDocVte testDetailDocVte = detailDocVteList.get(detailDocVteList.size() - 1);
        assertThat(testDetailDocVte.getPrixUnit()).isEqualTo(DEFAULT_PRIX_UNIT);
        assertThat(testDetailDocVte.getPrixunitnet()).isEqualTo(DEFAULT_PRIXUNITNET);
        assertThat(testDetailDocVte.getMontligne()).isEqualTo(DEFAULT_MONTLIGNE);
        assertThat(testDetailDocVte.getQteUnit()).isEqualTo(DEFAULT_QTE_UNIT);
        assertThat(testDetailDocVte.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testDetailDocVte.getQuantitecolis()).isEqualTo(DEFAULT_QUANTITECOLIS);
        assertThat(testDetailDocVte.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateDetailDocVteWithPatch() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();

        // Update the detailDocVte using partial update
        DetailDocVte partialUpdatedDetailDocVte = new DetailDocVte();
        partialUpdatedDetailDocVte.setId(detailDocVte.getId());

        partialUpdatedDetailDocVte
            .prixUnit(UPDATED_PRIX_UNIT)
            .prixunitnet(UPDATED_PRIXUNITNET)
            .montligne(UPDATED_MONTLIGNE)
            .qteUnit(UPDATED_QTE_UNIT)
            .remise(UPDATED_REMISE)
            .quantitecolis(UPDATED_QUANTITECOLIS)
            .designation(UPDATED_DESIGNATION);

        restDetailDocVteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDocVte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDocVte))
            )
            .andExpect(status().isOk());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
        DetailDocVte testDetailDocVte = detailDocVteList.get(detailDocVteList.size() - 1);
        assertThat(testDetailDocVte.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailDocVte.getPrixunitnet()).isEqualTo(UPDATED_PRIXUNITNET);
        assertThat(testDetailDocVte.getMontligne()).isEqualTo(UPDATED_MONTLIGNE);
        assertThat(testDetailDocVte.getQteUnit()).isEqualTo(UPDATED_QTE_UNIT);
        assertThat(testDetailDocVte.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testDetailDocVte.getQuantitecolis()).isEqualTo(UPDATED_QUANTITECOLIS);
        assertThat(testDetailDocVte.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailDocVte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDocVte))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDocVte))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailDocVte() throws Exception {
        int databaseSizeBeforeUpdate = detailDocVteRepository.findAll().size();
        detailDocVte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDocVteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailDocVte))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDocVte in the database
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailDocVte() throws Exception {
        // Initialize the database
        detailDocVteRepository.saveAndFlush(detailDocVte);

        int databaseSizeBeforeDelete = detailDocVteRepository.findAll().size();

        // Delete the detailDocVte
        restDetailDocVteMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailDocVte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailDocVte> detailDocVteList = detailDocVteRepository.findAll();
        assertThat(detailDocVteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
