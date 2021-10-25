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
 * A TypeProduit.
 */
@Entity
@Table(name = "type_produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeProduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "typeProduit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "societe", "typeProduit", "detailDocAches", "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeProduit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeProduit libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeProduit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setTypeProduit(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setTypeProduit(this));
        }
        this.produits = produits;
    }

    public TypeProduit produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public TypeProduit addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setTypeProduit(this);
        return this;
    }

    public TypeProduit removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setTypeProduit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeProduit)) {
            return false;
        }
        return id != null && id.equals(((TypeProduit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeProduit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
