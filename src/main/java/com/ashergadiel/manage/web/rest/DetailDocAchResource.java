package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DetailDocAch;
import com.ashergadiel.manage.repository.DetailDocAchRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.DetailDocAch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailDocAchResource {

    private final Logger log = LoggerFactory.getLogger(DetailDocAchResource.class);

    private static final String ENTITY_NAME = "detailDocAch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailDocAchRepository detailDocAchRepository;

    public DetailDocAchResource(DetailDocAchRepository detailDocAchRepository) {
        this.detailDocAchRepository = detailDocAchRepository;
    }

    /**
     * {@code POST  /detail-doc-aches} : Create a new detailDocAch.
     *
     * @param detailDocAch the detailDocAch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailDocAch, or with status {@code 400 (Bad Request)} if the detailDocAch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-doc-aches")
    public ResponseEntity<DetailDocAch> createDetailDocAch(@Valid @RequestBody DetailDocAch detailDocAch) throws URISyntaxException {
        log.debug("REST request to save DetailDocAch : {}", detailDocAch);
        if (detailDocAch.getId() != null) {
            throw new BadRequestAlertException("A new detailDocAch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailDocAch result = detailDocAchRepository.save(detailDocAch);
        return ResponseEntity
            .created(new URI("/api/detail-doc-aches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-doc-aches/:id} : Updates an existing detailDocAch.
     *
     * @param id the id of the detailDocAch to save.
     * @param detailDocAch the detailDocAch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDocAch,
     * or with status {@code 400 (Bad Request)} if the detailDocAch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailDocAch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-doc-aches/{id}")
    public ResponseEntity<DetailDocAch> updateDetailDocAch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailDocAch detailDocAch
    ) throws URISyntaxException {
        log.debug("REST request to update DetailDocAch : {}, {}", id, detailDocAch);
        if (detailDocAch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDocAch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDocAchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailDocAch result = detailDocAchRepository.save(detailDocAch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDocAch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-doc-aches/:id} : Partial updates given fields of an existing detailDocAch, field will ignore if it is null
     *
     * @param id the id of the detailDocAch to save.
     * @param detailDocAch the detailDocAch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDocAch,
     * or with status {@code 400 (Bad Request)} if the detailDocAch is not valid,
     * or with status {@code 404 (Not Found)} if the detailDocAch is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailDocAch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-doc-aches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailDocAch> partialUpdateDetailDocAch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailDocAch detailDocAch
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailDocAch partially : {}, {}", id, detailDocAch);
        if (detailDocAch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDocAch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDocAchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailDocAch> result = detailDocAchRepository
            .findById(detailDocAch.getId())
            .map(existingDetailDocAch -> {
                if (detailDocAch.getPrixUnit() != null) {
                    existingDetailDocAch.setPrixUnit(detailDocAch.getPrixUnit());
                }
                if (detailDocAch.getPrixunitnet() != null) {
                    existingDetailDocAch.setPrixunitnet(detailDocAch.getPrixunitnet());
                }
                if (detailDocAch.getMontligne() != null) {
                    existingDetailDocAch.setMontligne(detailDocAch.getMontligne());
                }
                if (detailDocAch.getQteUnit() != null) {
                    existingDetailDocAch.setQteUnit(detailDocAch.getQteUnit());
                }
                if (detailDocAch.getRemise() != null) {
                    existingDetailDocAch.setRemise(detailDocAch.getRemise());
                }
                if (detailDocAch.getQuantitecolis() != null) {
                    existingDetailDocAch.setQuantitecolis(detailDocAch.getQuantitecolis());
                }
                if (detailDocAch.getDesignation() != null) {
                    existingDetailDocAch.setDesignation(detailDocAch.getDesignation());
                }

                return existingDetailDocAch;
            })
            .map(detailDocAchRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDocAch.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-doc-aches} : get all the detailDocAches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailDocAches in body.
     */
    @GetMapping("/detail-doc-aches")
    public List<DetailDocAch> getAllDetailDocAches() {
        log.debug("REST request to get all DetailDocAches");
        return detailDocAchRepository.findAll();
    }

    /**
     * {@code GET  /detail-doc-aches/:id} : get the "id" detailDocAch.
     *
     * @param id the id of the detailDocAch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailDocAch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-doc-aches/{id}")
    public ResponseEntity<DetailDocAch> getDetailDocAch(@PathVariable Long id) {
        log.debug("REST request to get DetailDocAch : {}", id);
        Optional<DetailDocAch> detailDocAch = detailDocAchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailDocAch);
    }

    /**
     * {@code DELETE  /detail-doc-aches/:id} : delete the "id" detailDocAch.
     *
     * @param id the id of the detailDocAch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-doc-aches/{id}")
    public ResponseEntity<Void> deleteDetailDocAch(@PathVariable Long id) {
        log.debug("REST request to delete DetailDocAch : {}", id);
        detailDocAchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
