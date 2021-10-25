package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DocumentSortie;
import com.ashergadiel.manage.repository.DocumentSortieRepository;
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
 * Integration tests for the {@link DocumentSortieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentSortieResourceIT {

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

    private static final String ENTITY_API_URL = "/api/document-sorties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentSortieRepository documentSortieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentSortieMockMvc;

    private DocumentSortie documentSortie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentSortie createEntity(EntityManager em) {
        DocumentSortie documentSortie = new DocumentSortie()
            .dateSaisie(DEFAULT_DATE_SAISIE)
            .taxe(DEFAULT_TAXE)
            .observation(DEFAULT_OBSERVATION)
            .montantht(DEFAULT_MONTANTHT)
            .montantttc(DEFAULT_MONTANTTTC);
        return documentSortie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentSortie createUpdatedEntity(EntityManager em) {
        DocumentSortie documentSortie = new DocumentSortie()
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);
        return documentSortie;
    }

    @BeforeEach
    public void initTest() {
        documentSortie = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentSortie() throws Exception {
        int databaseSizeBeforeCreate = documentSortieRepository.findAll().size();
        // Create the DocumentSortie
        restDocumentSortieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isCreated());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentSortie testDocumentSortie = documentSortieList.get(documentSortieList.size() - 1);
        assertThat(testDocumentSortie.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
        assertThat(testDocumentSortie.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testDocumentSortie.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentSortie.getMontantht()).isEqualTo(DEFAULT_MONTANTHT);
        assertThat(testDocumentSortie.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void createDocumentSortieWithExistingId() throws Exception {
        // Create the DocumentSortie with an existing ID
        documentSortie.setId(1L);

        int databaseSizeBeforeCreate = documentSortieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentSortieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentSorties() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        // Get all the documentSortieList
        restDocumentSortieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentSortie.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateSaisie").value(hasItem(DEFAULT_DATE_SAISIE.toString())))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE.doubleValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].montantht").value(hasItem(DEFAULT_MONTANTHT.doubleValue())))
            .andExpect(jsonPath("$.[*].montantttc").value(hasItem(DEFAULT_MONTANTTTC.doubleValue())));
    }

    @Test
    @Transactional
    void getDocumentSortie() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        // Get the documentSortie
        restDocumentSortieMockMvc
            .perform(get(ENTITY_API_URL_ID, documentSortie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentSortie.getId().intValue()))
            .andExpect(jsonPath("$.dateSaisie").value(DEFAULT_DATE_SAISIE.toString()))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE.doubleValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.montantht").value(DEFAULT_MONTANTHT.doubleValue()))
            .andExpect(jsonPath("$.montantttc").value(DEFAULT_MONTANTTTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentSortie() throws Exception {
        // Get the documentSortie
        restDocumentSortieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumentSortie() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();

        // Update the documentSortie
        DocumentSortie updatedDocumentSortie = documentSortieRepository.findById(documentSortie.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentSortie are not directly saved in db
        em.detach(updatedDocumentSortie);
        updatedDocumentSortie
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentSortieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentSortie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentSortie))
            )
            .andExpect(status().isOk());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
        DocumentSortie testDocumentSortie = documentSortieList.get(documentSortieList.size() - 1);
        assertThat(testDocumentSortie.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentSortie.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentSortie.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentSortie.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentSortie.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void putNonExistingDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentSortie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentSortie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentSortieWithPatch() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();

        // Update the documentSortie using partial update
        DocumentSortie partialUpdatedDocumentSortie = new DocumentSortie();
        partialUpdatedDocumentSortie.setId(documentSortie.getId());

        partialUpdatedDocumentSortie.dateSaisie(UPDATED_DATE_SAISIE).taxe(UPDATED_TAXE).montantht(UPDATED_MONTANTHT);

        restDocumentSortieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentSortie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentSortie))
            )
            .andExpect(status().isOk());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
        DocumentSortie testDocumentSortie = documentSortieList.get(documentSortieList.size() - 1);
        assertThat(testDocumentSortie.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentSortie.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentSortie.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentSortie.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentSortie.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void fullUpdateDocumentSortieWithPatch() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();

        // Update the documentSortie using partial update
        DocumentSortie partialUpdatedDocumentSortie = new DocumentSortie();
        partialUpdatedDocumentSortie.setId(documentSortie.getId());

        partialUpdatedDocumentSortie
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentSortieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentSortie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentSortie))
            )
            .andExpect(status().isOk());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
        DocumentSortie testDocumentSortie = documentSortieList.get(documentSortieList.size() - 1);
        assertThat(testDocumentSortie.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentSortie.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentSortie.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentSortie.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentSortie.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentSortie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentSortie() throws Exception {
        int databaseSizeBeforeUpdate = documentSortieRepository.findAll().size();
        documentSortie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentSortieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentSortie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentSortie in the database
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentSortie() throws Exception {
        // Initialize the database
        documentSortieRepository.saveAndFlush(documentSortie);

        int databaseSizeBeforeDelete = documentSortieRepository.findAll().size();

        // Delete the documentSortie
        restDocumentSortieMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentSortie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentSortie> documentSortieList = documentSortieRepository.findAll();
        assertThat(documentSortieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
