package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DetailDocVte;
import com.ashergadiel.manage.repository.DetailDocVteRepository;
import com.ashergadiel.manage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ashergadiel.manage.domain.DetailDocVte}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailDocVteResource {

    private final Logger log = LoggerFactory.getLogger(DetailDocVteResource.class);

    private static final String ENTITY_NAME = "detailDocVte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailDocVteRepository detailDocVteRepository;

    public DetailDocVteResource(DetailDocVteRepository detailDocVteRepository) {
        this.detailDocVteRepository = detailDocVteRepository;
    }

    /**
     * {@code POST  /detail-doc-vtes} : Create a new detailDocVte.
     *
     * @param detailDocVte the detailDocVte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailDocVte, or with status {@code 400 (Bad Request)} if the detailDocVte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-doc-vtes")
    public ResponseEntity<DetailDocVte> createDetailDocVte(@Valid @RequestBody DetailDocVte detailDocVte) throws URISyntaxException {
        log.debug("REST request to save DetailDocVte : {}", detailDocVte);
        if (detailDocVte.getId() != null) {
            throw new BadRequestAlertException("A new detailDocVte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailDocVte result = detailDocVteRepository.save(detailDocVte);
        return ResponseEntity
            .created(new URI("/api/detail-doc-vtes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-doc-vtes/:id} : Updates an existing detailDocVte.
     *
     * @param id the id of the detailDocVte to save.
     * @param detailDocVte the detailDocVte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDocVte,
     * or with status {@code 400 (Bad Request)} if the detailDocVte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailDocVte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-doc-vtes/{id}")
    public ResponseEntity<DetailDocVte> updateDetailDocVte(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailDocVte detailDocVte
    ) throws URISyntaxException {
        log.debug("REST request to update DetailDocVte : {}, {}", id, detailDocVte);
        if (detailDocVte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDocVte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDocVteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailDocVte result = detailDocVteRepository.save(detailDocVte);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDocVte.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-doc-vtes/:id} : Partial updates given fields of an existing detailDocVte, field will ignore if it is null
     *
     * @param id the id of the detailDocVte to save.
     * @param detailDocVte the detailDocVte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDocVte,
     * or with status {@code 400 (Bad Request)} if the detailDocVte is not valid,
     * or with status {@code 404 (Not Found)} if the detailDocVte is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailDocVte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-doc-vtes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailDocVte> partialUpdateDetailDocVte(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailDocVte detailDocVte
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailDocVte partially : {}, {}", id, detailDocVte);
        if (detailDocVte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDocVte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDocVteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailDocVte> result = detailDocVteRepository
            .findById(detailDocVte.getId())
            .map(existingDetailDocVte -> {
                if (detailDocVte.getPrixUnit() != null) {
                    existingDetailDocVte.setPrixUnit(detailDocVte.getPrixUnit());
                }
                if (detailDocVte.getPrixunitnet() != null) {
                    existingDetailDocVte.setPrixunitnet(detailDocVte.getPrixunitnet());
                }
                if (detailDocVte.getMontligne() != null) {
                    existingDetailDocVte.setMontligne(detailDocVte.getMontligne());
                }
                if (detailDocVte.getQteUnit() != null) {
                    existingDetailDocVte.setQteUnit(detailDocVte.getQteUnit());
                }
                if (detailDocVte.getRemise() != null) {
                    existingDetailDocVte.setRemise(detailDocVte.getRemise());
                }
                if (detailDocVte.getQuantitecolis() != null) {
                    existingDetailDocVte.setQuantitecolis(detailDocVte.getQuantitecolis());
                }
                if (detailDocVte.getDesignation() != null) {
                    existingDetailDocVte.setDesignation(detailDocVte.getDesignation());
                }

                return existingDetailDocVte;
            })
            .map(detailDocVteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDocVte.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-doc-vtes} : get all the detailDocVtes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailDocVtes in body.
     */
    @GetMapping("/detail-doc-vtes")
    public List<DetailDocVte> getAllDetailDocVtes() {
        log.debug("REST request to get all DetailDocVtes");
        return detailDocVteRepository.findAll();
    }

    /**
     * {@code GET  /detail-doc-vtes/:id} : get the "id" detailDocVte.
     *
     * @param id the id of the detailDocVte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailDocVte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-doc-vtes/{id}")
    public ResponseEntity<DetailDocVte> getDetailDocVte(@PathVariable Long id) {
        log.debug("REST request to get DetailDocVte : {}", id);
        Optional<DetailDocVte> detailDocVte = detailDocVteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailDocVte);
    }

    /**
     * {@code DELETE  /detail-doc-vtes/:id} : delete the "id" detailDocVte.
     *
     * @param id the id of the detailDocVte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-doc-vtes/{id}")
    public ResponseEntity<Void> deleteDetailDocVte(@PathVariable Long id) {
        log.debug("REST request to delete DetailDocVte : {}", id);
        detailDocVteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
