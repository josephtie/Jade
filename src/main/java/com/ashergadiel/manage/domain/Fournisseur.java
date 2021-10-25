package com.ashergadiel.manage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "code_fournisseur", nullable = false)
    private String codeFournisseur;

    @NotNull
    @Column(name = "raison_fournisseur", nullable = false)
    private String raisonFournisseur;

    @Column(name = "adrfournisseur")
    private String adrfournisseur;

    @Column(name = "pays_fournisseur")
    private String paysFournisseur;

    @NotNull
    @Column(name = "ville_fournisseur", nullable = false)
    private String villeFournisseur;

    @Column(name = "mail_fournisseur")
    private String mailFournisseur;

    @NotNull
    @Column(name = "cel_fournisseur", nullable = false)
    private String celFournisseur;

    @Column(name = "tel_fournisseur")
    private String telFournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailsStocks", "produits", "fournisseurs", "documentAchats" }, allowSetters = true)
    private Societe societe;

    @JsonIgnoreProperties(value = { "fournisseur", "societe", "detailDocAches", "detailsStocks" }, allowSetters = true)
    @OneToOne(mappedBy = "fournisseur")
    private DocumentAchat documentAchat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeFournisseur() {
        return this.codeFournisseur;
    }

    public Fournisseur codeFournisseur(String codeFournisseur) {
        this.setCodeFournisseur(codeFournisseur);
        return this;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public String getRaisonFournisseur() {
        return this.raisonFournisseur;
    }

    public Fournisseur raisonFournisseur(String raisonFournisseur) {
        this.setRaisonFournisseur(raisonFournisseur);
        return this;
    }

    public void setRaisonFournisseur(String raisonFournisseur) {
        this.raisonFournisseur = raisonFournisseur;
    }

    public String getAdrfournisseur() {
        return this.adrfournisseur;
    }

    public Fournisseur adrfournisseur(String adrfournisseur) {
        this.setAdrfournisseur(adrfournisseur);
        return this;
    }

    public void setAdrfournisseur(String adrfournisseur) {
        this.adrfournisseur = adrfournisseur;
    }

    public String getPaysFournisseur() {
        return this.paysFournisseur;
    }

    public Fournisseur paysFournisseur(String paysFournisseur) {
        this.setPaysFournisseur(paysFournisseur);
        return this;
    }

    public void setPaysFournisseur(String paysFournisseur) {
        this.paysFournisseur = paysFournisseur;
    }

    public String getVilleFournisseur() {
        return this.villeFournisseur;
    }

    public Fournisseur villeFournisseur(String villeFournisseur) {
        this.setVilleFournisseur(villeFournisseur);
        return this;
    }

    public void setVilleFournisseur(String villeFournisseur) {
        this.villeFournisseur = villeFournisseur;
    }

    public String getMailFournisseur() {
        return this.mailFournisseur;
    }

    public Fournisseur mailFournisseur(String mailFournisseur) {
        this.setMailFournisseur(mailFournisseur);
        return this;
    }

    public void setMailFournisseur(String mailFournisseur) {
        this.mailFournisseur = mailFournisseur;
    }

    public String getCelFournisseur() {
        return this.celFournisseur;
    }

    public Fournisseur celFournisseur(String celFournisseur) {
        this.setCelFournisseur(celFournisseur);
        return this;
    }

    public void setCelFournisseur(String celFournisseur) {
        this.celFournisseur = celFournisseur;
    }

    public String getTelFournisseur() {
        return this.telFournisseur;
    }

    public Fournisseur telFournisseur(String telFournisseur) {
        this.setTelFournisseur(telFournisseur);
        return this;
    }

    public void setTelFournisseur(String telFournisseur) {
        this.telFournisseur = telFournisseur;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Fournisseur societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    public DocumentAchat getDocumentAchat() {
        return this.documentAchat;
    }

    public void setDocumentAchat(DocumentAchat documentAchat) {
        if (this.documentAchat != null) {
            this.documentAchat.setFournisseur(null);
        }
        if (documentAchat != null) {
            documentAchat.setFournisseur(this);
        }
        this.documentAchat = documentAchat;
    }

    public Fournisseur documentAchat(DocumentAchat documentAchat) {
        this.setDocumentAchat(documentAchat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", codeFournisseur='" + getCodeFournisseur() + "'" +
            ", raisonFournisseur='" + getRaisonFournisseur() + "'" +
            ", adrfournisseur='" + getAdrfournisseur() + "'" +
            ", paysFournisseur='" + getPaysFournisseur() + "'" +
            ", villeFournisseur='" + getVilleFournisseur() + "'" +
            ", mailFournisseur='" + getMailFournisseur() + "'" +
            ", celFournisseur='" + getCelFournisseur() + "'" +
            ", telFournisseur='" + getTelFournisseur() + "'" +
            "}";
    }
}
