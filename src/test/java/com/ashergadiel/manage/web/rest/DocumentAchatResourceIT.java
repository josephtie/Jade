package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.DocumentAchat;
import com.ashergadiel.manage.repository.DocumentAchatRepository;
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
 * Integration tests for the {@link DocumentAchatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentAchatResourceIT {

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

    private static final String ENTITY_API_URL = "/api/document-achats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentAchatRepository documentAchatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentAchatMockMvc;

    private DocumentAchat documentAchat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentAchat createEntity(EntityManager em) {
        DocumentAchat documentAchat = new DocumentAchat()
            .dateSaisie(DEFAULT_DATE_SAISIE)
            .taxe(DEFAULT_TAXE)
            .observation(DEFAULT_OBSERVATION)
            .montantht(DEFAULT_MONTANTHT)
            .montantttc(DEFAULT_MONTANTTTC);
        return documentAchat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentAchat createUpdatedEntity(EntityManager em) {
        DocumentAchat documentAchat = new DocumentAchat()
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);
        return documentAchat;
    }

    @BeforeEach
    public void initTest() {
        documentAchat = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentAchat() throws Exception {
        int databaseSizeBeforeCreate = documentAchatRepository.findAll().size();
        // Create the DocumentAchat
        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isCreated());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentAchat testDocumentAchat = documentAchatList.get(documentAchatList.size() - 1);
        assertThat(testDocumentAchat.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
        assertThat(testDocumentAchat.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testDocumentAchat.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentAchat.getMontantht()).isEqualTo(DEFAULT_MONTANTHT);
        assertThat(testDocumentAchat.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void createDocumentAchatWithExistingId() throws Exception {
        // Create the DocumentAchat with an existing ID
        documentAchat.setId(1L);

        int databaseSizeBeforeCreate = documentAchatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateSaisieIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentAchatRepository.findAll().size();
        // set the field null
        documentAchat.setDateSaisie(null);

        // Create the DocumentAchat, which fails.

        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isBadRequest());

        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentAchatRepository.findAll().size();
        // set the field null
        documentAchat.setTaxe(null);

        // Create the DocumentAchat, which fails.

        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isBadRequest());

        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontanthtIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentAchatRepository.findAll().size();
        // set the field null
        documentAchat.setMontantht(null);

        // Create the DocumentAchat, which fails.

        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isBadRequest());

        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantttcIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentAchatRepository.findAll().size();
        // set the field null
        documentAchat.setMontantttc(null);

        // Create the DocumentAchat, which fails.

        restDocumentAchatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isBadRequest());

        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentAchats() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        // Get all the documentAchatList
        restDocumentAchatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateSaisie").value(hasItem(DEFAULT_DATE_SAISIE.toString())))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE.doubleValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].montantht").value(hasItem(DEFAULT_MONTANTHT.doubleValue())))
            .andExpect(jsonPath("$.[*].montantttc").value(hasItem(DEFAULT_MONTANTTTC.doubleValue())));
    }

    @Test
    @Transactional
    void getDocumentAchat() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        // Get the documentAchat
        restDocumentAchatMockMvc
            .perform(get(ENTITY_API_URL_ID, documentAchat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentAchat.getId().intValue()))
            .andExpect(jsonPath("$.dateSaisie").value(DEFAULT_DATE_SAISIE.toString()))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE.doubleValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.montantht").value(DEFAULT_MONTANTHT.doubleValue()))
            .andExpect(jsonPath("$.montantttc").value(DEFAULT_MONTANTTTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentAchat() throws Exception {
        // Get the documentAchat
        restDocumentAchatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumentAchat() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();

        // Update the documentAchat
        DocumentAchat updatedDocumentAchat = documentAchatRepository.findById(documentAchat.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentAchat are not directly saved in db
        em.detach(updatedDocumentAchat);
        updatedDocumentAchat
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentAchat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentAchat))
            )
            .andExpect(status().isOk());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
        DocumentAchat testDocumentAchat = documentAchatList.get(documentAchatList.size() - 1);
        assertThat(testDocumentAchat.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentAchat.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentAchat.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentAchat.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentAchat.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void putNonExistingDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentAchat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentAchat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentAchatWithPatch() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();

        // Update the documentAchat using partial update
        DocumentAchat partialUpdatedDocumentAchat = new DocumentAchat();
        partialUpdatedDocumentAchat.setId(documentAchat.getId());

        partialUpdatedDocumentAchat.dateSaisie(UPDATED_DATE_SAISIE).taxe(UPDATED_TAXE).montantht(UPDATED_MONTANTHT);

        restDocumentAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentAchat))
            )
            .andExpect(status().isOk());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
        DocumentAchat testDocumentAchat = documentAchatList.get(documentAchatList.size() - 1);
        assertThat(testDocumentAchat.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentAchat.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentAchat.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDocumentAchat.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentAchat.getMontantttc()).isEqualTo(DEFAULT_MONTANTTTC);
    }

    @Test
    @Transactional
    void fullUpdateDocumentAchatWithPatch() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();

        // Update the documentAchat using partial update
        DocumentAchat partialUpdatedDocumentAchat = new DocumentAchat();
        partialUpdatedDocumentAchat.setId(documentAchat.getId());

        partialUpdatedDocumentAchat
            .dateSaisie(UPDATED_DATE_SAISIE)
            .taxe(UPDATED_TAXE)
            .observation(UPDATED_OBSERVATION)
            .montantht(UPDATED_MONTANTHT)
            .montantttc(UPDATED_MONTANTTTC);

        restDocumentAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentAchat))
            )
            .andExpect(status().isOk());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
        DocumentAchat testDocumentAchat = documentAchatList.get(documentAchatList.size() - 1);
        assertThat(testDocumentAchat.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
        assertThat(testDocumentAchat.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testDocumentAchat.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDocumentAchat.getMontantht()).isEqualTo(UPDATED_MONTANTHT);
        assertThat(testDocumentAchat.getMontantttc()).isEqualTo(UPDATED_MONTANTTTC);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentAchat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentAchat))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentAchat() throws Exception {
        int databaseSizeBeforeUpdate = documentAchatRepository.findAll().size();
        documentAchat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentAchatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentAchat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentAchat in the database
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentAchat() throws Exception {
        // Initialize the database
        documentAchatRepository.saveAndFlush(documentAchat);

        int databaseSizeBeforeDelete = documentAchatRepository.findAll().size();

        // Delete the documentAchat
        restDocumentAchatMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentAchat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentAchat> documentAchatList = documentAchatRepository.findAll();
        assertThat(documentAchatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
