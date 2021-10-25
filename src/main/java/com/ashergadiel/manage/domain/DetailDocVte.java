package com.ashergadiel.manage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetailDocVte.
 */
@Entity
@Table(name = "detail_doc_vte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetailDocVte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Column(name = "prix_unit", nullable = false)
    private Double prixUnit;

    @Column(name = "prixunitnet")
    private Double prixunitnet;

    @NotNull
    @Column(name = "montligne", nullable = false)
    private Double montligne;

    @NotNull
    @Column(name = "qte_unit", nullable = false)
    private Double qteUnit;

    @Column(name = "remise")
    private Double remise;

    @Column(name = "quantitecolis")
    private Double quantitecolis;

    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "societe", "typeProduit", "detailDocAches", "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private DocumentVente documentVente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailDocVte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrixUnit() {
        return this.prixUnit;
    }

    public DetailDocVte prixUnit(Double prixUnit) {
        this.setPrixUnit(prixUnit);
        return this;
    }

    public void setPrixUnit(Double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public Double getPrixunitnet() {
        return this.prixunitnet;
    }

    public DetailDocVte prixunitnet(Double prixunitnet) {
        this.setPrixunitnet(prixunitnet);
        return this;
    }

    public void setPrixunitnet(Double prixunitnet) {
        this.prixunitnet = prixunitnet;
    }

    public Double getMontligne() {
        return this.montligne;
    }

    public DetailDocVte montligne(Double montligne) {
        this.setMontligne(montligne);
        return this;
    }

    public void setMontligne(Double montligne) {
        this.montligne = montligne;
    }

    public Double getQteUnit() {
        return this.qteUnit;
    }

    public DetailDocVte qteUnit(Double qteUnit) {
        this.setQteUnit(qteUnit);
        return this;
    }

    public void setQteUnit(Double qteUnit) {
        this.qteUnit = qteUnit;
    }

    public Double getRemise() {
        return this.remise;
    }

    public DetailDocVte remise(Double remise) {
        this.setRemise(remise);
        return this;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public Double getQuantitecolis() {
        return this.quantitecolis;
    }

    public DetailDocVte quantitecolis(Double quantitecolis) {
        this.setQuantitecolis(quantitecolis);
        return this;
    }

    public void setQuantitecolis(Double quantitecolis) {
        this.quantitecolis = quantitecolis;
    }

    public String getDesignation() {
        return this.designation;
    }

    public DetailDocVte designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public DetailDocVte produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public DocumentVente getDocumentVente() {
        return this.documentVente;
    }

    public void setDocumentVente(DocumentVente documentVente) {
        this.documentVente = documentVente;
    }

    public DetailDocVte documentVente(DocumentVente documentVente) {
        this.setDocumentVente(documentVente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailDocVte)) {
            return false;
        }
        return id != null && id.equals(((DetailDocVte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailDocVte{" +
            "id=" + getId() +
            ", prixUnit=" + getPrixUnit() +
            ", prixunitnet=" + getPrixunitnet() +
            ", montligne=" + getMontligne() +
            ", qteUnit=" + getQteUnit() +
            ", remise=" + getRemise() +
            ", quantitecolis=" + getQuantitecolis() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
