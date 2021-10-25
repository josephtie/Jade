package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DocumentVente;
import com.ashergadiel.manage.repository.DocumentVenteRepository;
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
 * Integration tests for the {@link DocumentVenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentVenteResourceIT {

    private static final LocalDate DEFAULT_DATE_SAISIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SAISIE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TAXE = 1D;
    private static final Double UPDATED_TAXE = 2D;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANTHT = 1D;
    private static final Double UPDATED_MONTANTHT = 2D;

    private static final Double DEFAULT_MONTANTTTC = 1D;
    private static final Double UPDATED_MONTANTTTC = 2D;

    private static final String ENTITY_API_URL = "/api/document-ventes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentVenteRepository documentVenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentVenteMockMvc;

    private DocumentVente documentVente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentVente createEntity(EntityManager em) {
        DocumentVente documentVente = new DocumentVente()
            .dateSaisie(DEFAULT_DATE_SAISIE)
            .taxe(DEFAULT_TAXE)
            .observation(DEFAULT_OBSERVATION)
            .montantht(DEFAULT_MONTANTHT)
            .montantttc(DEFAULT_MONTANTTTC);
        return documentVente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentVente createUpdatedEntity(EntityManager em) {
        DocumentVente documentVente = new DocumentVente()
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);
        return documentVente;
    }

    @BeforeEach
    public void initTest() {
        documentVente = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentVente() throws Exception {
        int databaseSizeBeforeCreate = documentVenteRepository.findAll().size();
        // Create the DocumentVente
        restDocumentVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentVente)))
            .andExpect(status().isCreated());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentVente testDocumentVente = documentVenteList.get(documentVenteList.size() - 1);
        assertThat(testDocumentVente.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
        assertThat(testDocumentVente.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testDocumentVente.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentVente.getMontantht()).isEqualTo(DEFAULT_MONTANTHT);
        assertThat(testDocumentVente.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void createDocumentVenteWithExistingId() throws Exception {
        // Create the DocumentVente with an existing ID
        documentVente.setId(1L);

        int databaseSizeBeforeCreate = documentVenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentVente)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentVentes() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        // Get all the documentVenteList
        restDocumentVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateSaisie").value(hasItem(DEFAULT_DATE_SAISIE.toString())))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE.doubleValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].montantht").value(hasItem(DEFAULT_MONTANTHT.doubleValue())))
            .andExpect(jsonPath("$.[*].montantttc").value(hasItem(DEFAULT_MONTANTTTC.doubleValue())));
    }

    @Test
    @Transactional
    void getDocumentVente() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        // Get the documentVente
        restDocumentVenteMockMvc
            .perform(get(ENTITY_API_URL_ID, documentVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentVente.getId().intValue()))
            .andExpect(jsonPath("$.dateSaisie").value(DEFAULT_DATE_SAISIE.toString()))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE.doubleValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.montantht").value(DEFAULT_MONTANTHT.doubleValue()))
            .andExpect(jsonPath("$.montantttc").value(DEFAULT_MONTANTTTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentVente() throws Exception {
        // Get the documentVente
        restDocumentVenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumentVente() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();

        // Update the documentVente
        DocumentVente updatedDocumentVente = documentVenteRepository.findById(documentVente.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentVente are not directly saved in db
        em.detach(updatedDocumentVente);
        updatedDocumentVente
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentVente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentVente))
            )
            .andExpect(status().isOk());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
        DocumentVente testDocumentVente = documentVenteList.get(documentVenteList.size() - 1);
        assertThat(testDocumentVente.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentVente.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentVente.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentVente.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentVente.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void putNonExistingDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentVente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentVente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentVenteWithPatch() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();

        // Update the documentVente using partial update
        DocumentVente partialUpdatedDocumentVente = new DocumentVente();
        partialUpdatedDocumentVente.setId(documentVente.getId());

        partialUpdatedDocumentVente.dateSaisie(UPDATED_DATE_SAISIE).montantht(UPDATED_MONTANTHT);

        restDocumentVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentVente))
            )
            .andExpect(status().isOk());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
        DocumentVente testDocumentVente = documentVenteList.get(documentVenteList.size() - 1);
        assertThat(testDocumentVente.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentVente.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testDocumentVente.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentVente.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentVente.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void fullUpdateDocumentVenteWithPatch() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();

        // Update the documentVente using partial update
        DocumentVente partialUpdatedDocumentVente = new DocumentVente();
        partialUpdatedDocumentVente.setId(documentVente.getId());

        partialUpdatedDocumentVente
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentVente))
            )
            .andExpect(status().isOk());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
        DocumentVente testDocumentVente = documentVenteList.get(documentVenteList.size() - 1);
        assertThat(testDocumentVente.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentVente.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentVente.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentVente.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentVente.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentVente))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentVente() throws Exception {
        int databaseSizeBeforeUpdate = documentVenteRepository.findAll().size();
        documentVente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentVenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentVente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentVente in the database
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentVente() throws Exception {
        // Initialize the database
        documentVenteRepository.saveAndFlush(documentVente);

        int databaseSizeBeforeDelete = documentVenteRepository.findAll().size();

        // Delete the documentVente
        restDocumentVenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentVente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentVente> documentVenteList = documentVenteRepository.findAll();
        assertThat(documentVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
