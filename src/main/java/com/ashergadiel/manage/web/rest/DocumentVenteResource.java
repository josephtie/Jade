package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DocumentVente;
import com.ashergadiel.manage.repository.DocumentVenteRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.DocumentVente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentVenteResource {

    private final Logger log = LoggerFactory.getLogger(DocumentVenteResource.class);

    private static final String ENTITY_NAME = "documentVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentVenteRepository documentVenteRepository;

    public DocumentVenteResource(DocumentVenteRepository documentVenteRepository) {
        this.documentVenteRepository = documentVenteRepository;
    }

    /**
     * {@code POST  /document-ventes} : Create a new documentVente.
     *
     * @param documentVente the documentVente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentVente, or with status {@code 400 (Bad Request)} if the documentVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-ventes")
    public ResponseEntity<DocumentVente> createDocumentVente(@Valid @RequestBody DocumentVente documentVente) throws URISyntaxException {
        log.debug("REST request to save DocumentVente : {}", documentVente);
        if (documentVente.getId() != null) {
            throw new BadRequestAlertException("A new documentVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentVente result = documentVenteRepository.save(documentVente);
        return ResponseEntity
            .created(new URI("/api/document-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-ventes/:id} : Updates an existing documentVente.
     *
     * @param id the id of the documentVente to save.
     * @param documentVente the documentVente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentVente,
     * or with status {@code 400 (Bad Request)} if the documentVente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentVente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-ventes/{id}")
    public ResponseEntity<DocumentVente> updateDocumentVente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentVente documentVente
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentVente : {}, {}", id, documentVente);
        if (documentVente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentVente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentVente result = documentVenteRepository.save(documentVente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentVente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-ventes/:id} : Partial updates given fields of an existing documentVente, field will ignore if it is null
     *
     * @param id the id of the documentVente to save.
     * @param documentVente the documentVente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentVente,
     * or with status {@code 400 (Bad Request)} if the documentVente is not valid,
     * or with status {@code 404 (Not Found)} if the documentVente is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentVente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-ventes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentVente> partialUpdateDocumentVente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentVente documentVente
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentVente partially : {}, {}", id, documentVente);
        if (documentVente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentVente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentVenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentVente> result = documentVenteRepository
            .findById(documentVente.getId())
            .map(existingDocumentVente -> {
                if (documentVente.getDateSaisie() != null) {
                    existingDocumentVente.setDateSaisie(documentVente.getDateSaisie());
                }
                if (documentVente.getTaxe() != null) {
                    existingDocumentVente.setTaxe(documentVente.getTaxe());
                }
                if (documentVente.getObservation() != null) {
                    existingDocumentVente.setObservation(documentVente.getObservation());
                }
                if (documentVente.getMontantht() != null) {
                    existingDocumentVente.setMontantht(documentVente.getMontantht());
                }
                if (documentVente.getMontantttc() != null) {
                    existingDocumentVente.setMontantttc(documentVente.getMontantttc());
                }

                return existingDocumentVente;
            })
            .map(documentVenteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentVente.getId().toString())
        );
    }

    /**
     * {@code GET  /document-ventes} : get all the documentVentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentVentes in body.
     */
    @GetMapping("/document-ventes")
    public List<DocumentVente> getAllDocumentVentes() {
        log.debug("REST request to get all DocumentVentes");
        return documentVenteRepository.findAll();
    }

    /**
     * {@code GET  /document-ventes/:id} : get the "id" documentVente.
     *
     * @param id the id of the documentVente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentVente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-ventes/{id}")
    public ResponseEntity<DocumentVente> getDocumentVente(@PathVariable Long id) {
        log.debug("REST request to get DocumentVente : {}", id);
        Optional<DocumentVente> documentVente = documentVenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentVente);
    }

    /**
     * {@code DELETE  /document-ventes/:id} : delete the "id" documentVente.
     *
     * @param id the id of the documentVente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-ventes/{id}")
    public ResponseEntity<Void> deleteDocumentVente(@PathVariable Long id) {
        log.debug("REST request to delete DocumentVente : {}", id);
        documentVenteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
