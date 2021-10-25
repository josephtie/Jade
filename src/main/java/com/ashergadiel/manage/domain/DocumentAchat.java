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
 * A DocumentAchat.
 */
@Entity
@Table(name = "document_achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Column(name = "date_saisie", nullable = false)
    private LocalDate dateSaisie;

    @NotNull
    @Column(name = "taxe", nullable = false)
    private Double taxe;

    @Column(name = "observation")
    private String observation;

    @NotNull
    @Column(name = "montantht", nullable = false)
    private Double montantht;

    @NotNull
    @Column(name = "montantttc", nullable = false)
    private Double montantttc;

    @JsonIgnoreProperties(value = { "societe", "documentAchat" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "produits", "fournisseurs", "documentAchats" }, allowSetters = true)
    private Societe societe;

    @OneToMany(mappedBy = "documentAchat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat" }, allowSetters = true)
    private Set<DetailDocAch> detailDocAches = new HashSet<>();

    @OneToMany(mappedBy = "documentAchat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat", "documentVente", "documentSortie", "societe" }, allowSetters = true)
    private Set<DetailsStock> detailsStocks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentAchat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateSaisie() {
        return this.dateSaisie;
    }

    public DocumentAchat dateSaisie(LocalDate dateSaisie) {
        this.setDateSaisie(dateSaisie);
        return this;
    }

    public void setDateSaisie(LocalDate dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public Double getTaxe() {
        return this.taxe;
    }

    public DocumentAchat taxe(Double taxe) {
        this.setTaxe(taxe);
        return this;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public String getObservation() {
        return this.observation;
    }

    public DocumentAchat observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getMontantht() {
        return this.montantht;
    }

    public DocumentAchat montantht(Double montantht) {
        this.setMontantht(montantht);
        return this;
    }

    public void setMontantht(Double montantht) {
        this.montantht = montantht;
    }

    public Double getMontantttc() {
        return this.montantttc;
    }

    public DocumentAchat montantttc(Double montantttc) {
        this.setMontantttc(montantttc);
        return this;
    }

    public void setMontantttc(Double montantttc) {
        this.montantttc = montantttc;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DocumentAchat fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public DocumentAchat societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    public Set<DetailDocAch> getDetailDocAches() {
        return this.detailDocAches;
    }

    public void setDetailDocAches(Set<DetailDocAch> detailDocAches) {
        if (this.detailDocAches != null) {
            this.detailDocAches.forEach(i -> i.setDocumentAchat(null));
        }
        if (detailDocAches != null) {
            detailDocAches.forEach(i -> i.setDocumentAchat(this));
        }
        this.detailDocAches = detailDocAches;
    }

    public DocumentAchat detailDocAches(Set<DetailDocAch> detailDocAches) {
        this.setDetailDocAches(detailDocAches);
        return this;
    }

    public DocumentAchat addDetailDocAch(DetailDocAch detailDocAch) {
        this.detailDocAches.add(detailDocAch);
        detailDocAch.setDocumentAchat(this);
        return this;
    }

    public DocumentAchat removeDetailDocAch(DetailDocAch detailDocAch) {
        this.detailDocAches.remove(detailDocAch);
        detailDocAch.setDocumentAchat(null);
        return this;
    }

    public Set<DetailsStock> getDetailsStocks() {
        return this.detailsStocks;
    }

    public void setDetailsStocks(Set<DetailsStock> detailsStocks) {
        if (this.detailsStocks != null) {
            this.detailsStocks.forEach(i -> i.setDocumentAchat(null));
        }
        if (detailsStocks != null) {
            detailsStocks.forEach(i -> i.setDocumentAchat(this));
        }
        this.detailsStocks = detailsStocks;
    }

    public DocumentAchat detailsStocks(Set<DetailsStock> detailsStocks) {
        this.setDetailsStocks(detailsStocks);
        return this;
    }

    public DocumentAchat addDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.add(detailsStock);
        detailsStock.setDocumentAchat(this);
        return this;
    }

    public DocumentAchat removeDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.remove(detailsStock);
        detailsStock.setDocumentAchat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentAchat)) {
            return false;
        }
        return id != null && id.equals(((DocumentAchat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentAchat{" +
            "id=" + getId() +
            ", dateSaisie='" + getDateSaisie() + "'" +
            ", taxe=" + getTaxe() +
            ", observation='" + getObservation() + "'" +
            ", montantht=" + getMontantht() +
            ", montantttc=" + getMontantttc() +
            "}";
    }
}
