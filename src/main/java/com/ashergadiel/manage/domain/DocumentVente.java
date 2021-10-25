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
 * A DocumentVente.
 */
@Entity
@Table(name = "document_vente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentVente implements Serializable {

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

    @OneToMany(mappedBy = "documentVente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat", "documentVente", "documentSortie", "societe" }, allowSetters = true)
    private Set<DetailsStock> detailsStocks = new HashSet<>();

    @OneToMany(mappedBy = "documentVente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentVente" }, allowSetters = true)
    private Set<DetailDocVte> detailDocVtes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentVente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateSaisie() {
        return this.dateSaisie;
    }

    public DocumentVente dateSaisie(LocalDate dateSaisie) {
        this.setDateSaisie(dateSaisie);
        return this;
    }

    public void setDateSaisie(LocalDate dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public Double getTaxe() {
        return this.taxe;
    }

    public DocumentVente taxe(Double taxe) {
        this.setTaxe(taxe);
        return this;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public String getObservation() {
        return this.observation;
    }

    public DocumentVente observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getMontantht() {
        return this.montantht;
    }

    public DocumentVente montantht(Double montantht) {
        this.setMontantht(montantht);
        return this;
    }

    public void setMontantht(Double montantht) {
        this.montantht = montantht;
    }

    public Double getMontantttc() {
        return this.montantttc;
    }

    public DocumentVente montantttc(Double montantttc) {
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
            this.detailsStocks.forEach(i -> i.setDocumentVente(null));
        }
        if (detailsStocks != null) {
            detailsStocks.forEach(i -> i.setDocumentVente(this));
        }
        this.detailsStocks = detailsStocks;
    }

    public DocumentVente detailsStocks(Set<DetailsStock> detailsStocks) {
        this.setDetailsStocks(detailsStocks);
        return this;
    }

    public DocumentVente addDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.add(detailsStock);
        detailsStock.setDocumentVente(this);
        return this;
    }

    public DocumentVente removeDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.remove(detailsStock);
        detailsStock.setDocumentVente(null);
        return this;
    }

    public Set<DetailDocVte> getDetailDocVtes() {
        return this.detailDocVtes;
    }

    public void setDetailDocVtes(Set<DetailDocVte> detailDocVtes) {
        if (this.detailDocVtes != null) {
            this.detailDocVtes.forEach(i -> i.setDocumentVente(null));
        }
        if (detailDocVtes != null) {
            detailDocVtes.forEach(i -> i.setDocumentVente(this));
        }
        this.detailDocVtes = detailDocVtes;
    }

    public DocumentVente detailDocVtes(Set<DetailDocVte> detailDocVtes) {
        this.setDetailDocVtes(detailDocVtes);
        return this;
    }

    public DocumentVente addDetailDocVte(DetailDocVte detailDocVte) {
        this.detailDocVtes.add(detailDocVte);
        detailDocVte.setDocumentVente(this);
        return this;
    }

    public DocumentVente removeDetailDocVte(DetailDocVte detailDocVte) {
        this.detailDocVtes.remove(detailDocVte);
        detailDocVte.setDocumentVente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentVente)) {
            return false;
        }
        return id != null && id.equals(((DocumentVente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentVente{" +
            "id=" + getId() +
            ", dateSaisie='" + getDateSaisie() + "'" +
            ", taxe=" + getTaxe() +
            ", observation='" + getObservation() + "'" +
            ", montantht=" + getMontantht() +
            ", montantttc=" + getMontantttc() +
            "}";
    }
}
