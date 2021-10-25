package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DetailsStock;
import com.ashergadiel.manage.repository.DetailsStockRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DetailsStockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsStockResourceIT {

    private static final Double DEFAULT_STK_QT_EENTRANT = 1D;
    private static final Double UPDATED_STK_QT_EENTRANT = 2D;

    private static final Double DEFAULT_STK_QT_EINITIAL = 1D;
    private static final Double UPDATED_STK_QT_EINITIAL = 2D;

    private static final Double DEFAULT_STK_QT_EREEL = 1D;
    private static final Double UPDATED_STK_QT_EREEL = 2D;

    private static final Long DEFAULT_ID_COMMANDE = 1L;
    private static final Long UPDATED_ID_COMMANDE = 2L;

    private static final Long DEFAULT_ID_VENTE = 1L;
    private static final Long UPDATED_ID_VENTE = 2L;

    private static final Long DEFAULT_ID_SORTIE = 1L;
    private static final Long UPDATED_ID_SORTIE = 2L;

    private static final LocalDate DEFAULT_DATE_SAISIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SAISIE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTUNITAIRE_OP = 1D;
    private static final Double UPDATED_MONTUNITAIRE_OP = 2D;

    private static final String ENTITY_API_URL = "/api/details-stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailsStockRepository detailsStockRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailsStockMockMvc;

    private DetailsStock detailsStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsStock createEntity(EntityManager em) {
        DetailsStock detailsStock = new DetailsStock()
            .stkQTEentrant(DEFAULT_STK_QT_EENTRANT)
            .stkQTEinitial(DEFAULT_STK_QT_EINITIAL)
            .stkQTEreel(DEFAULT_STK_QT_EREEL)
            .idCommande(DEFAULT_ID_COMMANDE)
            .idVente(DEFAULT_ID_VENTE)
            .idSortie(DEFAULT_ID_SORTIE)
            .dateSaisie(DEFAULT_DATE_SAISIE)
            .montunitaireOP(DEFAULT_MONTUNITAIRE_OP);
        return detailsStock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsStock createUpdatedEntity(EntityManager em) {
        DetailsStock detailsStock = new DetailsStock()
            .stkQTEentrant(UPDATED_STK_QT_EENTRANT)
            .stkQTEinitial(UPDATED_STK_QT_EINITIAL)
            .stkQTEreel(UPDATED_STK_QT_EREEL)
            .idCommande(UPDATED_ID_COMMANDE)
            .idVente(UPDATED_ID_VENTE)
            .idSortie(UPDATED_ID_SORTIE)
            .dateSaisie(UPDATED_DATE_SAISIE)
            .montunitaireOP(UPDATED_MONTUNITAIRE_OP);
        return detailsStock;
    }

    @BeforeEach
    public void initTest() {
        detailsStock = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailsStock() throws Exception {
        int databaseSizeBeforeCreate = detailsStockRepository.findAll().size();
        // Create the DetailsStock
        restDetailsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isCreated());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsStock testDetailsStock = detailsStockList.get(detailsStockList.size() - 1);
        assertThat(testDetailsStock.getStkQTEentrant()).isEqualTo(DEFAULT_STK_QT_EENTRANT);
        assertThat(testDetailsStock.getStkQTEinitial()).isEqualTo(DEFAULT_STK_QT_EINITIAL);
        assertThat(testDetailsStock.getStkQTEreel()).isEqualTo(DEFAULT_STK_QT_EREEL);
        assertThat(testDetailsStock.getIdCommande()).isEqualTo(DEFAULT_ID_COMMANDE);
        assertThat(testDetailsStock.getIdVente()).isEqualTo(DEFAULT_ID_VENTE);
        assertThat(testDetailsStock.getIdSortie()).isEqualTo(DEFAULT_ID_SORTIE);
        assertThat(testDetailsStock.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
        assertThat(testDetailsStock.getMontunitaireOP()).isEqualTo(DEFAULT_MONTUNITAIRE_OP);
    }

    @Test
    @Transactional
    void createDetailsStockWithExistingId() throws Exception {
        // Create the DetailsStock with an existing ID
        detailsStock.setId(1L);

        int databaseSizeBeforeCreate = detailsStockRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isBadRequest());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStkQTEentrantIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsStockRepository.findAll().size();
        // set the field null
        detailsStock.setStkQTEentrant(null);

        // Create the DetailsStock, which fails.

        restDetailsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isBadRequest());

        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStkQTEinitialIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsStockRepository.findAll().size();
        // set the field null
        detailsStock.setStkQTEinitial(null);

        // Create the DetailsStock, which fails.

        restDetailsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isBadRequest());

        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStkQTEreelIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsStockRepository.findAll().size();
        // set the field null
        detailsStock.setStkQTEreel(null);

        // Create the DetailsStock, which fails.

        restDetailsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isBadRequest());

        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailsStocks() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        // Get all the detailsStockList
        restDetailsStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stkQTEentrant").value(hasItem(DEFAULT_STK_QT_EENTRANT.doubleValue())))
            .andExpect(jsonPath("$.[*].stkQTEinitial").value(hasItem(DEFAULT_STK_QT_EINITIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].stkQTEreel").value(hasItem(DEFAULT_STK_QT_EREEL.doubleValue())))
            .andExpect(jsonPath("$.[*].idCommande").value(hasItem(DEFAULT_ID_COMMANDE.intValue())))
            .andExpect(jsonPath("$.[*].idVente").value(hasItem(DEFAULT_ID_VENTE.intValue())))
            .andExpect(jsonPath("$.[*].idSortie").value(hasItem(DEFAULT_ID_SORTIE.intValue())))
            .andExpect(jsonPath("$.[*].dateSaisie").value(hasItem(DEFAULT_DATE_SAISIE.toString())))
            .andExpect(jsonPath("$.[*].montunitaireOP").value(hasItem(DEFAULT_MONTUNITAIRE_OP.doubleValue())));
    }

    @Test
    @Transactional
    void getDetailsStock() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        // Get the detailsStock
        restDetailsStockMockMvc
            .perform(get(ENTITY_API_URL_ID, detailsStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailsStock.getId().intValue()))
            .andExpect(jsonPath("$.stkQTEentrant").value(DEFAULT_STK_QT_EENTRANT.doubleValue()))
            .andExpect(jsonPath("$.stkQTEinitial").value(DEFAULT_STK_QT_EINITIAL.doubleValue()))
            .andExpect(jsonPath("$.stkQTEreel").value(DEFAULT_STK_QT_EREEL.doubleValue()))
            .andExpect(jsonPath("$.idCommande").value(DEFAULT_ID_COMMANDE.intValue()))
            .andExpect(jsonPath("$.idVente").value(DEFAULT_ID_VENTE.intValue()))
            .andExpect(jsonPath("$.idSortie").value(DEFAULT_ID_SORTIE.intValue()))
            .andExpect(jsonPath("$.dateSaisie").value(DEFAULT_DATE_SAISIE.toString()))
            .andExpect(jsonPath("$.montunitaireOP").value(DEFAULT_MONTUNITAIRE_OP.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDetailsStock() throws Exception {
        // Get the detailsStock
        restDetailsStockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailsStock() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();

        // Update the detailsStock
        DetailsStock updatedDetailsStock = detailsStockRepository.findById(detailsStock.getId()).get();
        // Disconnect from session so that the updates on updatedDetailsStock are not directly saved in db
        em.detach(updatedDetailsStock);
        updatedDetailsStock
            .stkQTEentrant(UPDATED_STK_QT_EENTRANT)
            .stkQTEinitial(UPDATED_STK_QT_EINITIAL)
            .stkQTEreel(UPDATED_STK_QT_EREEL)
            .idCommande(UPDATED_ID_COMMANDE)
            .idVente(UPDATED_ID_VENTE)
            .idSortie(UPDATED_ID_SORTIE)
            .dateSaisie(UPDATED_DATE_SAISIE)
            .montunitaireOP(UPDATED_MONTUNITAIRE_OP);

        restDetailsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailsStock.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailsStock))
            )
            .andExpect(status().isOk());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
        DetailsStock testDetailsStock = detailsStockList.get(detailsStockList.size() - 1);
        assertThat(testDetailsStock.getStkQTEentrant()).isEqualTo(UPDATED_STK_QT_EENTRANT);
        assertThat(testDetailsStock.getStkQTEinitial()).isEqualTo(UPDATED_STK_QT_EINITIAL);
        assertThat(testDetailsStock.getStkQTEreel()).isEqualTo(UPDATED_STK_QT_EREEL);
        assertThat(testDetailsStock.getIdCommande()).isEqualTo(UPDATED_ID_COMMANDE);
        assertThat(testDetailsStock.getIdVente()).isEqualTo(UPDATED_ID_VENTE);
        assertThat(testDetailsStock.getIdSortie()).isEqualTo(UPDATED_ID_SORTIE);
        assertThat(testDetailsStock.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDetailsStock.getMontunitaireOP()).isEqualTo(UPDATED_MONTUNITAIRE_OP);
    }

    @Test
    @Transactional
    void putNonExistingDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailsStock.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsStock)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailsStockWithPatch() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();

        // Update the detailsStock using partial update
        DetailsStock partialUpdatedDetailsStock = new DetailsStock();
        partialUpdatedDetailsStock.setId(detailsStock.getId());

        partialUpdatedDetailsStock.stkQTEreel(UPDATED_STK_QT_EREEL).idSortie(UPDATED_ID_SORTIE);

        restDetailsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsStock))
            )
            .andExpect(status().isOk());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
        DetailsStock testDetailsStock = detailsStockList.get(detailsStockList.size() - 1);
        assertThat(testDetailsStock.getStkQTEentrant()).isEqualTo(DEFAULT_STK_QT_EENTRANT);
        assertThat(testDetailsStock.getStkQTEinitial()).isEqualTo(DEFAULT_STK_QT_EINITIAL);
        assertThat(testDetailsStock.getStkQTEreel()).isEqualTo(UPDATED_STK_QT_EREEL);
        assertThat(testDetailsStock.getIdCommande()).isEqualTo(DEFAULT_ID_COMMANDE);
        assertThat(testDetailsStock.getIdVente()).isEqualTo(DEFAULT_ID_VENTE);
        assertThat(testDetailsStock.getIdSortie()).isEqualTo(UPDATED_ID_SORTIE);
        assertThat(testDetailsStock.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
        assertThat(testDetailsStock.getMontunitaireOP()).isEqualTo(DEFAULT_MONTUNITAIRE_OP);
    }

    @Test
    @Transactional
    void fullUpdateDetailsStockWithPatch() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();

        // Update the detailsStock using partial update
        DetailsStock partialUpdatedDetailsStock = new DetailsStock();
        partialUpdatedDetailsStock.setId(detailsStock.getId());

        partialUpdatedDetailsStock
            .stkQTEentrant(UPDATED_STK_QT_EENTRANT)
            .stkQTEinitial(UPDATED_STK_QT_EINITIAL)
            .stkQTEreel(UPDATED_STK_QT_EREEL)
            .idCommande(UPDATED_ID_COMMANDE)
            .idVente(UPDATED_ID_VENTE)
            .idSortie(UPDATED_ID_SORTIE)
            .dateSaisie(UPDATED_DATE_SAISIE)
            .montunitaireOP(UPDATED_MONTUNITAIRE_OP);

        restDetailsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsStock))
            )
            .andExpect(status().isOk());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
        DetailsStock testDetailsStock = detailsStockList.get(detailsStockList.size() - 1);
        assertThat(testDetailsStock.getStkQTEentrant()).isEqualTo(UPDATED_STK_QT_EENTRANT);
        assertThat(testDetailsStock.getStkQTEinitial()).isEqualTo(UPDATED_STK_QT_EINITIAL);
        assertThat(testDetailsStock.getStkQTEreel()).isEqualTo(UPDATED_STK_QT_EREEL);
        assertThat(testDetailsStock.getIdCommande()).isEqualTo(UPDATED_ID_COMMANDE);
        assertThat(testDetailsStock.getIdVente()).isEqualTo(UPDATED_ID_VENTE);
        assertThat(testDetailsStock.getIdSortie()).isEqualTo(UPDATED_ID_SORTIE);
        assertThat(testDetailsStock.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDetailsStock.getMontunitaireOP()).isEqualTo(UPDATED_MONTUNITAIRE_OP);
    }

    @Test
    @Transactional
    void patchNonExistingDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailsStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailsStock() throws Exception {
        int databaseSizeBeforeUpdate = detailsStockRepository.findAll().size();
        detailsStock.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsStockMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailsStock))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsStock in the database
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailsStock() throws Exception {
        // Initialize the database
        detailsStockRepository.saveAndFlush(detailsStock);

        int databaseSizeBeforeDelete = detailsStockRepository.findAll().size();

        // Delete the detailsStock
        restDetailsStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailsStock.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailsStock> detailsStockList = detailsStockRepository.findAll();
        assertThat(detailsStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
