package com.ashergadiel.manage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ashergadiel.manage.IntegrationTest;
import com.ashergadiel.manage.domain.Fournisseur;
import com.ashergadiel.manage.repository.FournisseurRepository;
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
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FournisseurResourceIT {

    private static final String DEFAULT_CODE_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ADRFOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_ADRFOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CEL_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_CEL_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_TEL_FOURNISSEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .codeFournisseur(DEFAULT_CODE_FOURNISSEUR)
            .raisonFournisseur(DEFAULT_RAISON_FOURNISSEUR)
            .adrfournisseur(DEFAULT_ADRFOURNISSEUR)
            .paysFournisseur(DEFAULT_PAYS_FOURNISSEUR)
            .villeFournisseur(DEFAULT_VILLE_FOURNISSEUR)
            .mailFournisseur(DEFAULT_MAIL_FOURNISSEUR)
            .celFournisseur(DEFAULT_CEL_FOURNISSEUR)
            .telFournisseur(DEFAULT_TEL_FOURNISSEUR);
        return fournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .raisonFournisseur(UPDATED_RAISON_FOURNISSEUR)
            .adrfournisseur(UPDATED_ADRFOURNISSEUR)
            .paysFournisseur(UPDATED_PAYS_FOURNISSEUR)
            .villeFournisseur(UPDATED_VILLE_FOURNISSEUR)
            .mailFournisseur(UPDATED_MAIL_FOURNISSEUR)
            .celFournisseur(UPDATED_CEL_FOURNISSEUR)
            .telFournisseur(UPDATED_TEL_FOURNISSEUR);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();
        // Create the Fournisseur
        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getCodeFournisseur()).isEqualTo(DEFAULT_CODE_FOURNISSEUR);
        assertThat(testFournisseur.getRaisonFournisseur()).isEqualTo(DEFAULT_RAISON_FOURNISSEUR);
        assertThat(testFournisseur.getAdrfournisseur()).isEqualTo(DEFAULT_ADRFOURNISSEUR);
        assertThat(testFournisseur.getPaysFournisseur()).isEqualTo(DEFAULT_PAYS_FOURNISSEUR);
        assertThat(testFournisseur.getVilleFournisseur()).isEqualTo(DEFAULT_VILLE_FOURNISSEUR);
        assertThat(testFournisseur.getMailFournisseur()).isEqualTo(DEFAULT_MAIL_FOURNISSEUR);
        assertThat(testFournisseur.getCelFournisseur()).isEqualTo(DEFAULT_CEL_FOURNISSEUR);
        assertThat(testFournisseur.getTelFournisseur()).isEqualTo(DEFAULT_TEL_FOURNISSEUR);
    }

    @Test
    @Transactional
    void createFournisseurWithExistingId() throws Exception {
        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeFournisseurIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setCodeFournisseur(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRaisonFournisseurIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setRaisonFournisseur(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVilleFournisseurIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setVilleFournisseur(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCelFournisseurIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setCelFournisseur(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeFournisseur").value(hasItem(DEFAULT_CODE_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].raisonFournisseur").value(hasItem(DEFAULT_RAISON_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].adrfournisseur").value(hasItem(DEFAULT_ADRFOURNISSEUR)))
            .andExpect(jsonPath("$.[*].paysFournisseur").value(hasItem(DEFAULT_PAYS_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].villeFournisseur").value(hasItem(DEFAULT_VILLE_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].mailFournisseur").value(hasItem(DEFAULT_MAIL_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].celFournisseur").value(hasItem(DEFAULT_CEL_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].telFournisseur").value(hasItem(DEFAULT_TEL_FOURNISSEUR)));
    }

    @Test
    @Transactional
    void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.codeFournisseur").value(DEFAULT_CODE_FOURNISSEUR))
            .andExpect(jsonPath("$.raisonFournisseur").value(DEFAULT_RAISON_FOURNISSEUR))
            .andExpect(jsonPath("$.adrfournisseur").value(DEFAULT_ADRFOURNISSEUR))
            .andExpect(jsonPath("$.paysFournisseur").value(DEFAULT_PAYS_FOURNISSEUR))
            .andExpect(jsonPath("$.villeFournisseur").value(DEFAULT_VILLE_FOURNISSEUR))
            .andExpect(jsonPath("$.mailFournisseur").value(DEFAULT_MAIL_FOURNISSEUR))
            .andExpect(jsonPath("$.celFournisseur").value(DEFAULT_CEL_FOURNISSEUR))
            .andExpect(jsonPath("$.telFournisseur").value(DEFAULT_TEL_FOURNISSEUR));
    }

    @Test
    @Transactional
    void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .raisonFournisseur(UPDATED_RAISON_FOURNISSEUR)
            .adrfournisseur(UPDATED_ADRFOURNISSEUR)
            .paysFournisseur(UPDATED_PAYS_FOURNISSEUR)
            .villeFournisseur(UPDATED_VILLE_FOURNISSEUR)
            .mailFournisseur(UPDATED_MAIL_FOURNISSEUR)
            .celFournisseur(UPDATED_CEL_FOURNISSEUR)
            .telFournisseur(UPDATED_TEL_FOURNISSEUR);

        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getCodeFournisseur()).isEqualTo(UPDATED_CODE_FOURNISSEUR);
        assertThat(testFournisseur.getRaisonFournisseur()).isEqualTo(UPDATED_RAISON_FOURNISSEUR);
        assertThat(testFournisseur.getAdrfournisseur()).isEqualTo(UPDATED_ADRFOURNISSEUR);
        assertThat(testFournisseur.getPaysFournisseur()).isEqualTo(UPDATED_PAYS_FOURNISSEUR);
        assertThat(testFournisseur.getVilleFournisseur()).isEqualTo(UPDATED_VILLE_FOURNISSEUR);
        assertThat(testFournisseur.getMailFournisseur()).isEqualTo(UPDATED_MAIL_FOURNISSEUR);
        assertThat(testFournisseur.getCelFournisseur()).isEqualTo(UPDATED_CEL_FOURNISSEUR);
        assertThat(testFournisseur.getTelFournisseur()).isEqualTo(UPDATED_TEL_FOURNISSEUR);
    }

    @Test
    @Transactional
    void putNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .raisonFournisseur(UPDATED_RAISON_FOURNISSEUR)
            .adrfournisseur(UPDATED_ADRFOURNISSEUR)
            .paysFournisseur(UPDATED_PAYS_FOURNISSEUR);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getCodeFournisseur()).isEqualTo(DEFAULT_CODE_FOURNISSEUR);
        assertThat(testFournisseur.getRaisonFournisseur()).isEqualTo(UPDATED_RAISON_FOURNISSEUR);
        assertThat(testFournisseur.getAdrfournisseur()).isEqualTo(UPDATED_ADRFOURNISSEUR);
        assertThat(testFournisseur.getPaysFournisseur()).isEqualTo(UPDATED_PAYS_FOURNISSEUR);
        assertThat(testFournisseur.getVilleFournisseur()).isEqualTo(DEFAULT_VILLE_FOURNISSEUR);
        assertThat(testFournisseur.getMailFournisseur()).isEqualTo(DEFAULT_MAIL_FOURNISSEUR);
        assertThat(testFournisseur.getCelFournisseur()).isEqualTo(DEFAULT_CEL_FOURNISSEUR);
        assertThat(testFournisseur.getTelFournisseur()).isEqualTo(DEFAULT_TEL_FOURNISSEUR);
    }

    @Test
    @Transactional
    void fullUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .raisonFournisseur(UPDATED_RAISON_FOURNISSEUR)
            .adrfournisseur(UPDATED_ADRFOURNISSEUR)
            .paysFournisseur(UPDATED_PAYS_FOURNISSEUR)
            .villeFournisseur(UPDATED_VILLE_FOURNISSEUR)
            .mailFournisseur(UPDATED_MAIL_FOURNISSEUR)
            .celFournisseur(UPDATED_CEL_FOURNISSEUR)
            .telFournisseur(UPDATED_TEL_FOURNISSEUR);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getCodeFournisseur()).isEqualTo(UPDATED_CODE_FOURNISSEUR);
        assertThat(testFournisseur.getRaisonFournisseur()).isEqualTo(UPDATED_RAISON_FOURNISSEUR);
        assertThat(testFournisseur.getAdrfournisseur()).isEqualTo(UPDATED_ADRFOURNISSEUR);
        assertThat(testFournisseur.getPaysFournisseur()).isEqualTo(UPDATED_PAYS_FOURNISSEUR);
        assertThat(testFournisseur.getVilleFournisseur()).isEqualTo(UPDATED_VILLE_FOURNISSEUR);
        assertThat(testFournisseur.getMailFournisseur()).isEqualTo(UPDATED_MAIL_FOURNISSEUR);
        assertThat(testFournisseur.getCelFournisseur()).isEqualTo(UPDATED_CEL_FOURNISSEUR);
        assertThat(testFournisseur.getTelFournisseur()).isEqualTo(UPDATED_TEL_FOURNISSEUR);
    }

    @Test
    @Transactional
    void patchNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fournisseur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, fournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
