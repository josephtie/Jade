package com.ashergadiel.manage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentSortie.
 */
@Entity
@Table(name = "document_sortie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentSortie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "date_saisie")
    private LocalDate dateSaisie;

    @Column(name = "taxe")
    private Double taxe;

    @Column(name = "observation")
    private String observation;

    @Column(name = "montantht")
    private Double montantht;

    @Column(name = "montantttc")
    private Double montantttc;

    @OneToMany(mappedBy = "documentSortie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat", "documentVente", "documentSortie", "societe" }, allowSetters = true)
    private Set<DetailsStock> detailsStocks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentSortie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateSaisie() {
        return this.dateSaisie;
    }

    public DocumentSortie dateSaisie(LocalDate dateSaisie) {
        this.setDateSaisie(dateSaisie);
        return this;
    }

    public void setDateSaisie(LocalDate dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public Double getTaxe() {
        return this.taxe;
    }

    public DocumentSortie taxe(Double taxe) {
        this.setTaxe(taxe);
        return this;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public String getObservation() {
        return this.observation;
    }

    public DocumentSortie observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getMontantht() {
        return this.montantht;
    }

    public DocumentSortie montantht(Double montantht) {
        this.setMontantht(montantht);
        return this;
    }

    public void setMontantht(Double montantht) {
        this.montantht = montantht;
    }

    public Double getMontantttc() {
        return this.montantttc;
    }

    public DocumentSortie montantttc(Double montantttc) {
        this.setMontantttc(montantttc);
        return this;
    }

    public void setMontantttc(Double montantttc) {
        this.montantttc = montantttc;
    }

    public Set<DetailsStock> getDetailsStocks() {
        return this.detailsStocks;
    }

    public void setDetailsStocks(Set<DetailsStock> detailsStocks) {
        if (this.detailsStocks != null) {
            this.detailsStocks.forEach(i -> i.setDocumentSortie(null));
        }
        if (detailsStocks != null) {
            detailsStocks.forEach(i -> i.setDocumentSortie(this));
        }
        this.detailsStocks = detailsStocks;
    }

    public DocumentSortie detailsStocks(Set<DetailsStock> detailsStocks) {
        this.setDetailsStocks(detailsStocks);
        return this;
    }

    public DocumentSortie addDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.add(detailsStock);
        detailsStock.setDocumentSortie(this);
        return this;
    }

    public DocumentSortie removeDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.remove(detailsStock);
        detailsStock.setDocumentSortie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentSortie)) {
            return false;
        }
        return id != null && id.equals(((DocumentSortie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentSortie{" +
            "id=" + getId() +
            ", dateSaisie='" + getDateSaisie() + "'" +
            ", taxe=" + getTaxe() +
            ", observation='" + getObservation() + "'" +
            ", montantht=" + getMontantht() +
            ", montantttc=" + getMontantttc() +
            "}";
    }
}
