package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.TypeProduit;
import com.ashergadiel.manage.repository.TypeProduitRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.TypeProduit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeProduitResource {

    private final Logger log = LoggerFactory.getLogger(TypeProduitResource.class);

    private static final String ENTITY_NAME = "typeProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeProduitRepository typeProduitRepository;

    public TypeProduitResource(TypeProduitRepository typeProduitRepository) {
        this.typeProduitRepository = typeProduitRepository;
    }

    /**
     * {@code POST  /type-produits} : Create a new typeProduit.
     *
     * @param typeProduit the typeProduit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeProduit, or with status {@code 400 (Bad Request)} if the typeProduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-produits")
    public ResponseEntity<TypeProduit> createTypeProduit(@Valid @RequestBody TypeProduit typeProduit) throws URISyntaxException {
        log.debug("REST request to save TypeProduit : {}", typeProduit);
        if (typeProduit.getId() != null) {
            throw new BadRequestAlertException("A new typeProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeProduit result = typeProduitRepository.save(typeProduit);
        return ResponseEntity
            .created(new URI("/api/type-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-produits/:id} : Updates an existing typeProduit.
     *
     * @param id the id of the typeProduit to save.
     * @param typeProduit the typeProduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeProduit,
     * or with status {@code 400 (Bad Request)} if the typeProduit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeProduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-produits/{id}")
    public ResponseEntity<TypeProduit> updateTypeProduit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeProduit typeProduit
    ) throws URISyntaxException {
        log.debug("REST request to update TypeProduit : {}, {}", id, typeProduit);
        if (typeProduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeProduit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeProduitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeProduit result = typeProduitRepository.save(typeProduit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeProduit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-produits/:id} : Partial updates given fields of an existing typeProduit, field will ignore if it is null
     *
     * @param id the id of the typeProduit to save.
     * @param typeProduit the typeProduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeProduit,
     * or with status {@code 400 (Bad Request)} if the typeProduit is not valid,
     * or with status {@code 404 (Not Found)} if the typeProduit is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeProduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-produits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeProduit> partialUpdateTypeProduit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeProduit typeProduit
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeProduit partially : {}, {}", id, typeProduit);
        if (typeProduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeProduit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeProduitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeProduit> result = typeProduitRepository
            .findById(typeProduit.getId())
            .map(existingTypeProduit -> {
                if (typeProduit.getLibelle() != null) {
                    existingTypeProduit.setLibelle(typeProduit.getLibelle());
                }
                if (typeProduit.getDescription() != null) {
                    existingTypeProduit.setDescription(typeProduit.getDescription());
                }

                return existingTypeProduit;
            })
            .map(typeProduitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeProduit.getId().toString())
        );
    }

    /**
     * {@code GET  /type-produits} : get all the typeProduits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeProduits in body.
     */
    @GetMapping("/type-produits")
    public List<TypeProduit> getAllTypeProduits() {
        log.debug("REST request to get all TypeProduits");
        return typeProduitRepository.findAll();
    }

    /**
     * {@code GET  /type-produits/:id} : get the "id" typeProduit.
     *
     * @param id the id of the typeProduit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeProduit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-produits/{id}")
    public ResponseEntity<TypeProduit> getTypeProduit(@PathVariable Long id) {
        log.debug("REST request to get TypeProduit : {}", id);
        Optional<TypeProduit> typeProduit = typeProduitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeProduit);
    }

    /**
     * {@code DELETE  /type-produits/:id} : delete the "id" typeProduit.
     *
     * @param id the id of the typeProduit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-produits/{id}")
    public ResponseEntity<Void> deleteTypeProduit(@PathVariable Long id) {
        log.debug("REST request to delete TypeProduit : {}", id);
        typeProduitRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
