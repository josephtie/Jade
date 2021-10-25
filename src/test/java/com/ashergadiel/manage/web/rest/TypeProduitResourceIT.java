package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.TypeProduit;
import com.ashergadiel.manage.repository.TypeProduitRepository;
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
 * Integration tests for the {@link TypeProduitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeProduitResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeProduitRepository typeProduitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeProduitMockMvc;

    private TypeProduit typeProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeProduit createEntity(EntityManager em) {
        TypeProduit typeProduit = new TypeProduit().libelle(DEFAULT_LIBELLE).description(DEFAULT_DESCRIPTION);
        return typeProduit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeProduit createUpdatedEntity(EntityManager em) {
        TypeProduit typeProduit = new TypeProduit().libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);
        return typeProduit;
    }

    @BeforeEach
    public void initTest() {
        typeProduit = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeProduit() throws Exception {
        int databaseSizeBeforeCreate = typeProduitRepository.findAll().size();
        // Create the TypeProduit
        restTypeProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeProduit)))
            .andExpect(status().isCreated());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeCreate + 1);
        TypeProduit testTypeProduit = typeProduitList.get(typeProduitList.size() - 1);
        assertThat(testTypeProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeProduit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTypeProduitWithExistingId() throws Exception {
        // Create the TypeProduit with an existing ID
        typeProduit.setId(1L);

        int databaseSizeBeforeCreate = typeProduitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeProduit)))
            .andExpect(status().isBadRequest());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeProduitRepository.findAll().size();
        // set the field null
        typeProduit.setLibelle(null);

        // Create the TypeProduit, which fails.

        restTypeProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeProduit)))
            .andExpect(status().isBadRequest());

        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeProduits() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        // Get all the typeProduitList
        restTypeProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeProduit() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        // Get the typeProduit
        restTypeProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, typeProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeProduit.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeProduit() throws Exception {
        // Get the typeProduit
        restTypeProduitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeProduit() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();

        // Update the typeProduit
        TypeProduit updatedTypeProduit = typeProduitRepository.findById(typeProduit.getId()).get();
        // Disconnect from session so that the updates on updatedTypeProduit are not directly saved in db
        em.detach(updatedTypeProduit);
        updatedTypeProduit.libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);

        restTypeProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeProduit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeProduit))
            )
            .andExpect(status().isOk());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
        TypeProduit testTypeProduit = typeProduitList.get(typeProduitList.size() - 1);
        assertThat(testTypeProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeProduit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeProduit))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeProduit))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeProduit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeProduitWithPatch() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();

        // Update the typeProduit using partial update
        TypeProduit partialUpdatedTypeProduit = new TypeProduit();
        partialUpdatedTypeProduit.setId(typeProduit.getId());

        partialUpdatedTypeProduit.description(UPDATED_DESCRIPTION);

        restTypeProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeProduit))
            )
            .andExpect(status().isOk());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
        TypeProduit testTypeProduit = typeProduitList.get(typeProduitList.size() - 1);
        assertThat(testTypeProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTypeProduitWithPatch() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();

        // Update the typeProduit using partial update
        TypeProduit partialUpdatedTypeProduit = new TypeProduit();
        partialUpdatedTypeProduit.setId(typeProduit.getId());

        partialUpdatedTypeProduit.libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);

        restTypeProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeProduit))
            )
            .andExpect(status().isOk());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
        TypeProduit testTypeProduit = typeProduitList.get(typeProduitList.size() - 1);
        assertThat(testTypeProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeProduit))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeProduit))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeProduit() throws Exception {
        int databaseSizeBeforeUpdate = typeProduitRepository.findAll().size();
        typeProduit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeProduitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeProduit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeProduit in the database
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeProduit() throws Exception {
        // Initialize the database
        typeProduitRepository.saveAndFlush(typeProduit);

        int databaseSizeBeforeDelete = typeProduitRepository.findAll().size();

        // Delete the typeProduit
        restTypeProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeProduit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeProduit> typeProduitList = typeProduitRepository.findAll();
        assertThat(typeProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
