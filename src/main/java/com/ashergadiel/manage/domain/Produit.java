package com.ashergadiel.manage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "stockinit", nullable = false)
    private Double stockinit;

    @Column(name = "stock_approv")
    private Double stockApprov;

    @Column(name = "boisson_prix_unitairenet")
    private Double boissonPrixUnitairenet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "produits", "fournisseurs", "documentAchats" }, allowSetters = true)
    private Societe societe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private TypeProduit typeProduit;

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat" }, allowSetters = true)
    private Set<DetailDocAch> detailDocAches = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat", "documentVente", "documentSortie", "societe" }, allowSetters = true)
    private Set<DetailsStock> detailsStocks = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentVente" }, allowSetters = true)
    private Set<DetailDocVte> detailDocVtes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Produit libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getStockinit() {
        return this.stockinit;
    }

    public Produit stockinit(Double stockinit) {
        this.setStockinit(stockinit);
        return this;
    }

    public void setStockinit(Double stockinit) {
        this.stockinit = stockinit;
    }

    public Double getStockApprov() {
        return this.stockApprov;
    }

    public Produit stockApprov(Double stockApprov) {
        this.setStockApprov(stockApprov);
        return this;
    }

    public void setStockApprov(Double stockApprov) {
        this.stockApprov = stockApprov;
    }

    public Double getBoissonPrixUnitairenet() {
        return this.boissonPrixUnitairenet;
    }

    public Produit boissonPrixUnitairenet(Double boissonPrixUnitairenet) {
        this.setBoissonPrixUnitairenet(boissonPrixUnitairenet);
        return this;
    }

    public void setBoissonPrixUnitairenet(Double boissonPrixUnitairenet) {
        this.boissonPrixUnitairenet = boissonPrixUnitairenet;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Produit societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    public TypeProduit getTypeProduit() {
        return this.typeProduit;
    }

    public void setTypeProduit(TypeProduit typeProduit) {
        this.typeProduit = typeProduit;
    }

    public Produit typeProduit(TypeProduit typeProduit) {
        this.setTypeProduit(typeProduit);
        return this;
    }

    public Set<DetailDocAch> getDetailDocAches() {
        return this.detailDocAches;
    }

    public void setDetailDocAches(Set<DetailDocAch> detailDocAches) {
        if (this.detailDocAches != null) {
            this.detailDocAches.forEach(i -> i.setProduit(null));
        }
        if (detailDocAches != null) {
            detailDocAches.forEach(i -> i.setProduit(this));
        }
        this.detailDocAches = detailDocAches;
    }

    public Produit detailDocAches(Set<DetailDocAch> detailDocAches) {
        this.setDetailDocAches(detailDocAches);
        return this;
    }

    public Produit addDetailDocAch(DetailDocAch detailDocAch) {
        this.detailDocAches.add(detailDocAch);
        detailDocAch.setProduit(this);
        return this;
    }

    public Produit removeDetailDocAch(DetailDocAch detailDocAch) {
        this.detailDocAches.remove(detailDocAch);
        detailDocAch.setProduit(null);
        return this;
    }

    public Set<DetailsStock> getDetailsStocks() {
        return this.detailsStocks;
    }

    public void setDetailsStocks(Set<DetailsStock> detailsStocks) {
        if (this.detailsStocks != null) {
            this.detailsStocks.forEach(i -> i.setProduit(null));
        }
        if (detailsStocks != null) {
            detailsStocks.forEach(i -> i.setProduit(this));
        }
        this.detailsStocks = detailsStocks;
    }

    public Produit detailsStocks(Set<DetailsStock> detailsStocks) {
        this.setDetailsStocks(detailsStocks);
        return this;
    }

    public Produit addDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.add(detailsStock);
        detailsStock.setProduit(this);
        return this;
    }

    public Produit removeDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.remove(detailsStock);
        detailsStock.setProduit(null);
        return this;
    }

    public Set<DetailDocVte> getDetailDocVtes() {
        return this.detailDocVtes;
    }

    public void setDetailDocVtes(Set<DetailDocVte> detailDocVtes) {
        if (this.detailDocVtes != null) {
            this.detailDocVtes.forEach(i -> i.setProduit(null));
        }
        if (detailDocVtes != null) {
            detailDocVtes.forEach(i -> i.setProduit(this));
        }
        this.detailDocVtes = detailDocVtes;
    }

    public Produit detailDocVtes(Set<DetailDocVte> detailDocVtes) {
        this.setDetailDocVtes(detailDocVtes);
        return this;
    }

    public Produit addDetailDocVte(DetailDocVte detailDocVte) {
        this.detailDocVtes.add(detailDocVte);
        detailDocVte.setProduit(this);
        return this;
    }

    public Produit removeDetailDocVte(DetailDocVte detailDocVte) {
        this.detailDocVtes.remove(detailDocVte);
        detailDocVte.setProduit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", stockinit=" + getStockinit() +
            ", stockApprov=" + getStockApprov() +
            ", boissonPrixUnitairenet=" + getBoissonPrixUnitairenet() +
            "}";
    }
}
