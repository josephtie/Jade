package com.ashergadiel.manage.web.rest;

import com.ashergadiel.manage.domain.DetailsStock;
import com.ashergadiel.manage.repository.DetailsStockRepository;
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
 * REST controller for managing {@link com.ashergadiel.manage.domain.DetailsStock}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailsStockResource {

    private final Logger log = LoggerFactory.getLogger(DetailsStockResource.class);

    private static final String ENTITY_NAME = "detailsStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsStockRepository detailsStockRepository;

    public DetailsStockResource(DetailsStockRepository detailsStockRepository) {
        this.detailsStockRepository = detailsStockRepository;
    }

    /**
     * {@code POST  /details-stocks} : Create a new detailsStock.
     *
     * @param detailsStock the detailsStock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailsStock, or with status {@code 400 (Bad Request)} if the detailsStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/details-stocks")
    public ResponseEntity<DetailsStock> createDetailsStock(@Valid @RequestBody DetailsStock detailsStock) throws URISyntaxException {
        log.debug("REST request to save DetailsStock : {}", detailsStock);
        if (detailsStock.getId() != null) {
            throw new BadRequestAlertException("A new detailsStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailsStock result = detailsStockRepository.save(detailsStock);
        return ResponseEntity
            .created(new URI("/api/details-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details-stocks/:id} : Updates an existing detailsStock.
     *
     * @param id the id of the detailsStock to save.
     * @param detailsStock the detailsStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsStock,
     * or with status {@code 400 (Bad Request)} if the detailsStock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailsStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/details-stocks/{id}")
    public ResponseEntity<DetailsStock> updateDetailsStock(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailsStock detailsStock
    ) throws URISyntaxException {
        log.debug("REST request to update DetailsStock : {}, {}", id, detailsStock);
        if (detailsStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailsStock result = detailsStockRepository.save(detailsStock);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsStock.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /details-stocks/:id} : Partial updates given fields of an existing detailsStock, field will ignore if it is null
     *
     * @param id the id of the detailsStock to save.
     * @param detailsStock the detailsStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsStock,
     * or with status {@code 400 (Bad Request)} if the detailsStock is not valid,
     * or with status {@code 404 (Not Found)} if the detailsStock is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailsStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/details-stocks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailsStock> partialUpdateDetailsStock(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailsStock detailsStock
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailsStock partially : {}, {}", id, detailsStock);
        if (detailsStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailsStock> result = detailsStockRepository
            .findById(detailsStock.getId())
            .map(existingDetailsStock -> {
                if (detailsStock.getStkQTEentrant() != null) {
                    existingDetailsStock.setStkQTEentrant(detailsStock.getStkQTEentrant());
                }
                if (detailsStock.getStkQTEinitial() != null) {
                    existingDetailsStock.setStkQTEinitial(detailsStock.getStkQTEinitial());
                }
                if (detailsStock.getStkQTEreel() != null) {
                    existingDetailsStock.setStkQTEreel(detailsStock.getStkQTEreel());
                }
                if (detailsStock.getIdCommande() != null) {
                    existingDetailsStock.setIdCommande(detailsStock.getIdCommande());
                }
                if (detailsStock.getIdVente() != null) {
                    existingDetailsStock.setIdVente(detailsStock.getIdVente());
                }
                if (detailsStock.getIdSortie() != null) {
                    existingDetailsStock.setIdSortie(detailsStock.getIdSortie());
                }
                if (detailsStock.getDateSaisie() != null) {
                    existingDetailsStock.setDateSaisie(detailsStock.getDateSaisie());
                }
                if (detailsStock.getMontunitaireOP() != null) {
                    existingDetailsStock.setMontunitaireOP(detailsStock.getMontunitaireOP());
                }

                return existingDetailsStock;
            })
            .map(detailsStockRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsStock.getId().toString())
        );
    }

    /**
     * {@code GET  /details-stocks} : get all the detailsStocks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailsStocks in body.
     */
    @GetMapping("/details-stocks")
    public List<DetailsStock> getAllDetailsStocks() {
        log.debug("REST request to get all DetailsStocks");
        return detailsStockRepository.findAll();
    }

    /**
     * {@code GET  /details-stocks/:id} : get the "id" detailsStock.
     *
     * @param id the id of the detailsStock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailsStock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/details-stocks/{id}")
    public ResponseEntity<DetailsStock> getDetailsStock(@PathVariable Long id) {
        log.debug("REST request to get DetailsStock : {}", id);
        Optional<DetailsStock> detailsStock = detailsStockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailsStock);
    }

    /**
     * {@code DELETE  /details-stocks/:id} : delete the "id" detailsStock.
     *
     * @param id the id of the detailsStock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/details-stocks/{id}")
    public ResponseEntity<Void> deleteDetailsStock(@PathVariable Long id) {
        log.debug("REST request to delete DetailsStock : {}", id);
        detailsStockRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
