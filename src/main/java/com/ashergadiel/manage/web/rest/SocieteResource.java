package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.Societe;
import com.ashergadiel.manage.repository.SocieteRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.Societe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SocieteResource {

    private final Logger log = LoggerFactory.getLogger(SocieteResource.class);

    private static final String ENTITY_NAME = "societe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteRepository societeRepository;

    public SocieteResource(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * {@code POST  /societes} : Create a new societe.
     *
     * @param societe the societe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societe, or with status {@code 400 (Bad Request)} if the societe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/societes")
    public ResponseEntity<Societe> createSociete(@Valid @RequestBody Societe societe) throws URISyntaxException {
        log.debug("REST request to save Societe : {}", societe);
        if (societe.getId() != null) {
            throw new BadRequestAlertException("A new societe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Societe result = societeRepository.save(societe);
        return ResponseEntity
            .created(new URI("/api/societes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /societes/:id} : Updates an existing societe.
     *
     * @param id the id of the societe to save.
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/societes/{id}")
    public ResponseEntity<Societe> updateSociete(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Societe societe
    ) throws URISyntaxException {
        log.debug("REST request to update Societe : {}, {}", id, societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Societe result = societeRepository.save(societe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /societes/:id} : Partial updates given fields of an existing societe, field will ignore if it is null
     *
     * @param id the id of the societe to save.
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 404 (Not Found)} if the societe is not found,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/societes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Societe> partialUpdateSociete(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Societe societe
    ) throws URISyntaxException {
        log.debug("REST request to partial update Societe partially : {}, {}", id, societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Societe> result = societeRepository
            .findById(societe.getId())
            .map(existingSociete -> {
                if (societe.getRaisonsoc() != null) {
                    existingSociete.setRaisonsoc(societe.getRaisonsoc());
                }
                if (societe.getSigle() != null) {
                    existingSociete.setSigle(societe.getSigle());
                }
                if (societe.getActivitepp() != null) {
                    existingSociete.setActivitepp(societe.getActivitepp());
                }
                if (societe.getAdressgeo() != null) {
                    existingSociete.setAdressgeo(societe.getAdressgeo());
                }
                if (societe.getFormjuri() != null) {
                    existingSociete.setFormjuri(societe.getFormjuri());
                }
                if (societe.getTelephone() != null) {
                    existingSociete.setTelephone(societe.getTelephone());
                }
                if (societe.getBp() != null) {
                    existingSociete.setBp(societe.getBp());
                }
                if (societe.getRegistreCce() != null) {
                    existingSociete.setRegistreCce(societe.getRegistreCce());
                }
                if (societe.getPays() != null) {
                    existingSociete.setPays(societe.getPays());
                }
                if (societe.getVille() != null) {
                    existingSociete.setVille(societe.getVille());
                }
                if (societe.getCommune() != null) {
                    existingSociete.setCommune(societe.getCommune());
                }
                if (societe.getEmail() != null) {
                    existingSociete.setEmail(societe.getEmail());
                }
                if (societe.getActif() != null) {
                    existingSociete.setActif(societe.getActif());
                }
                if (societe.getFileData() != null) {
                    existingSociete.setFileData(societe.getFileData());
                }
                if (societe.getFileDataContentType() != null) {
                    existingSociete.setFileDataContentType(societe.getFileDataContentType());
                }
                if (societe.getUrlLogo() != null) {
                    existingSociete.setUrlLogo(societe.getUrlLogo());
                }

                return existingSociete;
            })
            .map(societeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societe.getId().toString())
        );
    }

    /**
     * {@code GET  /societes} : get all the societes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societes in body.
     */
    @GetMapping("/societes")
    public List<Societe> getAllSocietes() {
        log.debug("REST request to get all Societes");
        return societeRepository.findAll();
    }

    /**
     * {@code GET  /societes/:id} : get the "id" societe.
     *
     * @param id the id of the societe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/societes/{id}")
    public ResponseEntity<Societe> getSociete(@PathVariable Long id) {
        log.debug("REST request to get Societe : {}", id);
        Optional<Societe> societe = societeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(societe);
    }

    /**
     * {@code DELETE  /societes/:id} : delete the "id" societe.
     *
     * @param id the id of the societe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/societes/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable Long id) {
        log.debug("REST request to delete Societe : {}", id);
        societeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
