package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DocumentSortie;
import com.ashergadiel.manage.repository.DocumentSortieRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.DocumentSortie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentSortieResource {

    private final Logger log = LoggerFactory.getLogger(DocumentSortieResource.class);

    private static final String ENTITY_NAME = "documentSortie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentSortieRepository documentSortieRepository;

    public DocumentSortieResource(DocumentSortieRepository documentSortieRepository) {
        this.documentSortieRepository = documentSortieRepository;
    }

    /**
     * {@code POST  /document-sorties} : Create a new documentSortie.
     *
     * @param documentSortie the documentSortie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentSortie, or with status {@code 400 (Bad Request)} if the documentSortie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-sorties")
    public ResponseEntity<DocumentSortie> createDocumentSortie(@Valid @RequestBody DocumentSortie documentSortie)
        throws URISyntaxException {
        log.debug("REST request to save DocumentSortie : {}", documentSortie);
        if (documentSortie.getId() != null) {
            throw new BadRequestAlertException("A new documentSortie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentSortie result = documentSortieRepository.save(documentSortie);
        return ResponseEntity
            .created(new URI("/api/document-sorties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-sorties/:id} : Updates an existing documentSortie.
     *
     * @param id the id of the documentSortie to save.
     * @param documentSortie the documentSortie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentSortie,
     * or with status {@code 400 (Bad Request)} if the documentSortie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentSortie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-sorties/{id}")
    public ResponseEntity<DocumentSortie> updateDocumentSortie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentSortie documentSortie
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentSortie : {}, {}", id, documentSortie);
        if (documentSortie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentSortie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentSortieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentSortie result = documentSortieRepository.save(documentSortie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentSortie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-sorties/:id} : Partial updates given fields of an existing documentSortie, field will ignore if it is null
     *
     * @param id the id of the documentSortie to save.
     * @param documentSortie the documentSortie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentSortie,
     * or with status {@code 400 (Bad Request)} if the documentSortie is not valid,
     * or with status {@code 404 (Not Found)} if the documentSortie is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentSortie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-sorties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentSortie> partialUpdateDocumentSortie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentSortie documentSortie
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentSortie partially : {}, {}", id, documentSortie);
        if (documentSortie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentSortie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentSortieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentSortie> result = documentSortieRepository
            .findById(documentSortie.getId())
            .map(existingDocumentSortie -> {
                if (documentSortie.getDateSaisie() != null) {
                    existingDocumentSortie.setDateSaisie(documentSortie.getDateSaisie());
                }
                if (documentSortie.getTaxe() != null) {
                    existingDocumentSortie.setTaxe(documentSortie.getTaxe());
                }
                if (documentSortie.getObservation() != null) {
                    existingDocumentSortie.setObservation(documentSortie.getObservation());
                }
                if (documentSortie.getMontantht() != null) {
                    existingDocumentSortie.setMontantht(documentSortie.getMontantht());
                }
                if (documentSortie.getMontantttc() != null) {
                    existingDocumentSortie.setMontantttc(documentSortie.getMontantttc());
                }

                return existingDocumentSortie;
            })
            .map(documentSortieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentSortie.getId().toString())
        );
    }

    /**
     * {@code GET  /document-sorties} : get all the documentSorties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentSorties in body.
     */
    @GetMapping("/document-sorties")
    public List<DocumentSortie> getAllDocumentSorties() {
        log.debug("REST request to get all DocumentSorties");
        return documentSortieRepository.findAll();
    }

    /**
     * {@code GET  /document-sorties/:id} : get the "id" documentSortie.
     *
     * @param id the id of the documentSortie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentSortie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-sorties/{id}")
    public ResponseEntity<DocumentSortie> getDocumentSortie(@PathVariable Long id) {
        log.debug("REST request to get DocumentSortie : {}", id);
        Optional<DocumentSortie> documentSortie = documentSortieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentSortie);
    }

    /**
     * {@code DELETE  /document-sorties/:id} : delete the "id" documentSortie.
     *
     * @param id the id of the documentSortie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-sorties/{id}")
    public ResponseEntity<Void> deleteDocumentSortie(@PathVariable Long id) {
        log.debug("REST request to delete DocumentSortie : {}", id);
        documentSortieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
