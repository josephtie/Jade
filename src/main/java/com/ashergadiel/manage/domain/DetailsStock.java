package com.ashergadiel.manage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetailsStock.
 */
@Entity
@Table(name = "details_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetailsStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "stk_qt_eentrant", nullable = false)
    private Double stkQTEentrant;

    @NotNull
    @Column(name = "stk_qt_einitial", nullable = false)
    private Double stkQTEinitial;

    @NotNull
    @Column(name = "stk_qt_ereel", nullable = false)
    private Double stkQTEreel;

    @Column(name = "id_commande")
    private Long idCommande;

    @Column(name = "id_vente")
    private Long idVente;

    @Column(name = "id_sortie")
    private Long idSortie;

    @Column(name = "date_saisie")
    private LocalDate dateSaisie;

    @Column(name = "montunitaire_op")
    private Double montunitaireOP;

    @ManyToOne
    @JsonIgnoreProperties(value = { "societe", "typeProduit", "detailDocAches", "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fournisseur", "societe", "detailDocAches", "detailsStocks" }, allowSetters = true)
    private DocumentAchat documentAchat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private DocumentVente documentVente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks" }, allowSetters = true)
    private DocumentSortie documentSortie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "produits", "fournisseurs", "documentAchats" }, allowSetters = true)
    private Societe societe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailsStock id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStkQTEentrant() {
        return this.stkQTEentrant;
    }

    public DetailsStock stkQTEentrant(Double stkQTEentrant) {
        this.setStkQTEentrant(stkQTEentrant);
        return this;
    }

    public void setStkQTEentrant(Double stkQTEentrant) {
        this.stkQTEentrant = stkQTEentrant;
    }

    public Double getStkQTEinitial() {
        return this.stkQTEinitial;
    }

    public DetailsStock stkQTEinitial(Double stkQTEinitial) {
        this.setStkQTEinitial(stkQTEinitial);
        return this;
    }

    public void setStkQTEinitial(Double stkQTEinitial) {
        this.stkQTEinitial = stkQTEinitial;
    }

    public Double getStkQTEreel() {
        return this.stkQTEreel;
    }

    public DetailsStock stkQTEreel(Double stkQTEreel) {
        this.setStkQTEreel(stkQTEreel);
        return this;
    }

    public void setStkQTEreel(Double stkQTEreel) {
        this.stkQTEreel = stkQTEreel;
    }

    public Long getIdCommande() {
        return this.idCommande;
    }

    public DetailsStock idCommande(Long idCommande) {
        this.setIdCommande(idCommande);
        return this;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdVente() {
        return this.idVente;
    }

    public DetailsStock idVente(Long idVente) {
        this.setIdVente(idVente);
        return this;
    }

    public void setIdVente(Long idVente) {
        this.idVente = idVente;
    }

    public Long getIdSortie() {
        return this.idSortie;
    }

    public DetailsStock idSortie(Long idSortie) {
        this.setIdSortie(idSortie);
        return this;
    }

    public void setIdSortie(Long idSortie) {
        this.idSortie = idSortie;
    }

    public LocalDate getDateSaisie() {
        return this.dateSaisie;
    }

    public DetailsStock dateSaisie(LocalDate dateSaisie) {
        this.setDateSaisie(dateSaisie);
        return this;
    }

    public void setDateSaisie(LocalDate dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public Double getMontunitaireOP() {
        return this.montunitaireOP;
    }

    public DetailsStock montunitaireOP(Double montunitaireOP) {
        this.setMontunitaireOP(montunitaireOP);
        return this;
    }

    public void setMontunitaireOP(Double montunitaireOP) {
        this.montunitaireOP = montunitaireOP;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public DetailsStock produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public DocumentAchat getDocumentAchat() {
        return this.documentAchat;
    }

    public void setDocumentAchat(DocumentAchat documentAchat) {
        this.documentAchat = documentAchat;
    }

    public DetailsStock documentAchat(DocumentAchat documentAchat) {
        this.setDocumentAchat(documentAchat);
        return this;
    }

    public DocumentVente getDocumentVente() {
        return this.documentVente;
    }

    public void setDocumentVente(DocumentVente documentVente) {
        this.documentVente = documentVente;
    }

    public DetailsStock documentVente(DocumentVente documentVente) {
        this.setDocumentVente(documentVente);
        return this;
    }

    public DocumentSortie getDocumentSortie() {
        return this.documentSortie;
    }

    public void setDocumentSortie(DocumentSortie documentSortie) {
        this.documentSortie = documentSortie;
    }

    public DetailsStock documentSortie(DocumentSortie documentSortie) {
        this.setDocumentSortie(documentSortie);
        return this;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public DetailsStock societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailsStock)) {
            return false;
        }
        return id != null && id.equals(((DetailsStock) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailsStock{" +
            "id=" + getId() +
            ", stkQTEentrant=" + getStkQTEentrant() +
            ", stkQTEinitial=" + getStkQTEinitial() +
            ", stkQTEreel=" + getStkQTEreel() +
            ", idCommande=" + getIdCommande() +
            ", idVente=" + getIdVente() +
            ", idSortie=" + getIdSortie() +
            ", dateSaisie='" + getDateSaisie() + "'" +
            ", montunitaireOP=" + getMontunitaireOP() +
            "}";
    }
}
