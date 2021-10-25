package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DocumentAchat;
import com.ashergadiel.manage.repository.DocumentAchatRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.DocumentAchat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentAchatResource {

    private final Logger log = LoggerFactory.getLogger(DocumentAchatResource.class);

    private static final String ENTITY_NAME = "documentAchat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentAchatRepository documentAchatRepository;

    public DocumentAchatResource(DocumentAchatRepository documentAchatRepository) {
        this.documentAchatRepository = documentAchatRepository;
    }

    /**
     * {@code POST  /document-achats} : Create a new documentAchat.
     *
     * @param documentAchat the documentAchat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentAchat, or with status {@code 400 (Bad Request)} if the documentAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-achats")
    public ResponseEntity<DocumentAchat> createDocumentAchat(@Valid @RequestBody DocumentAchat documentAchat) throws URISyntaxException {
        log.debug("REST request to save DocumentAchat : {}", documentAchat);
        if (documentAchat.getId() != null) {
            throw new BadRequestAlertException("A new documentAchat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentAchat result = documentAchatRepository.save(documentAchat);
        return ResponseEntity
            .created(new URI("/api/document-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-achats/:id} : Updates an existing documentAchat.
     *
     * @param id the id of the documentAchat to save.
     * @param documentAchat the documentAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentAchat,
     * or with status {@code 400 (Bad Request)} if the documentAchat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-achats/{id}")
    public ResponseEntity<DocumentAchat> updateDocumentAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentAchat documentAchat
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentAchat : {}, {}", id, documentAchat);
        if (documentAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentAchat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentAchat result = documentAchatRepository.save(documentAchat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentAchat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-achats/:id} : Partial updates given fields of an existing documentAchat, field will ignore if it is null
     *
     * @param id the id of the documentAchat to save.
     * @param documentAchat the documentAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentAchat,
     * or with status {@code 400 (Bad Request)} if the documentAchat is not valid,
     * or with status {@code 404 (Not Found)} if the documentAchat is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-achats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentAchat> partialUpdateDocumentAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentAchat documentAchat
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentAchat partially : {}, {}", id, documentAchat);
        if (documentAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentAchat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentAchat> result = documentAchatRepository
            .findById(documentAchat.getId())
            .map(existingDocumentAchat -> {
                if (documentAchat.getDateSaisie() != null) {
                    existingDocumentAchat.setDateSaisie(documentAchat.getDateSaisie());
                }
                if (documentAchat.getTaxe() != null) {
                    existingDocumentAchat.setTaxe(documentAchat.getTaxe());
                }
                if (documentAchat.getObservation() != null) {
                    existingDocumentAchat.setObservation(documentAchat.getObservation());
                }
                if (documentAchat.getMontantht() != null) {
                    existingDocumentAchat.setMontantht(documentAchat.getMontantht());
                }
                if (documentAchat.getMontantttc() != null) {
                    existingDocumentAchat.setMontantttc(documentAchat.getMontantttc());
                }

                return existingDocumentAchat;
            })
            .map(documentAchatRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentAchat.getId().toString())
        );
    }

    /**
     * {@code GET  /document-achats} : get all the documentAchats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentAchats in body.
     */
    @GetMapping("/document-achats")
    public List<DocumentAchat> getAllDocumentAchats() {
        log.debug("REST request to get all DocumentAchats");
        return documentAchatRepository.findAll();
    }

    /**
     * {@code GET  /document-achats/:id} : get the "id" documentAchat.
     *
     * @param id the id of the documentAchat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentAchat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-achats/{id}")
    public ResponseEntity<DocumentAchat> getDocumentAchat(@PathVariable Long id) {
        log.debug("REST request to get DocumentAchat : {}", id);
        Optional<DocumentAchat> documentAchat = documentAchatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentAchat);
    }

    /**
     * {@code DELETE  /document-achats/:id} : delete the "id" documentAchat.
     *
     * @param id the id of the documentAchat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-achats/{id}")
    public ResponseEntity<Void> deleteDocumentAchat(@PathVariable Long id) {
        log.debug("REST request to delete DocumentAchat : {}", id);
        documentAchatRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
