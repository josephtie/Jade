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
 * A Societe.
 */
@Entity
@Table(name = "societe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "raisonsoc", nullable = false)
    private String raisonsoc;

    @NotNull
    @Column(name = "sigle", nullable = false)
    private String sigle;

    @NotNull
    @Column(name = "activitepp", nullable = false)
    private String activitepp;

    @Column(name = "adressgeo")
    private String adressgeo;

    @Column(name = "formjuri")
    private String formjuri;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "bp")
    private String bp;

    @Column(name = "registre_cce")
    private String registreCce;

    @NotNull
    @Column(name = "pays", nullable = false)
    private String pays;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @NotNull
    @Column(name = "commune", nullable = false)
    private String commune;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "actif")
    private Boolean actif;

    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @NotNull
    @Column(name = "file_data_content_type", nullable = false)
    private String fileDataContentType;

    @Column(name = "url_logo")
    private String urlLogo;

    @OneToMany(mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "documentAchat", "documentVente", "documentSortie", "societe" }, allowSetters = true)
    private Set<DetailsStock> detailsStocks = new HashSet<>();

    @OneToMany(mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "societe", "typeProduit", "detailDocAches", "detailsStocks", "detailDocVtes" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "societe", "documentAchat" }, allowSetters = true)
    private Set<Fournisseur> fournisseurs = new HashSet<>();

    @OneToMany(mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "societe", "detailDocAches", "detailsStocks" }, allowSetters = true)
    private Set<DocumentAchat> documentAchats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Societe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonsoc() {
        return this.raisonsoc;
    }

    public Societe raisonsoc(String raisonsoc) {
        this.setRaisonsoc(raisonsoc);
        return this;
    }

    public void setRaisonsoc(String raisonsoc) {
        this.raisonsoc = raisonsoc;
    }

    public String getSigle() {
        return this.sigle;
    }

    public Societe sigle(String sigle) {
        this.setSigle(sigle);
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getActivitepp() {
        return this.activitepp;
    }

    public Societe activitepp(String activitepp) {
        this.setActivitepp(activitepp);
        return this;
    }

    public void setActivitepp(String activitepp) {
        this.activitepp = activitepp;
    }

    public String getAdressgeo() {
        return this.adressgeo;
    }

    public Societe adressgeo(String adressgeo) {
        this.setAdressgeo(adressgeo);
        return this;
    }

    public void setAdressgeo(String adressgeo) {
        this.adressgeo = adressgeo;
    }

    public String getFormjuri() {
        return this.formjuri;
    }

    public Societe formjuri(String formjuri) {
        this.setFormjuri(formjuri);
        return this;
    }

    public void setFormjuri(String formjuri) {
        this.formjuri = formjuri;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Societe telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBp() {
        return this.bp;
    }

    public Societe bp(String bp) {
        this.setBp(bp);
        return this;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getRegistreCce() {
        return this.registreCce;
    }

    public Societe registreCce(String registreCce) {
        this.setRegistreCce(registreCce);
        return this;
    }

    public void setRegistreCce(String registreCce) {
        this.registreCce = registreCce;
    }

    public String getPays() {
        return this.pays;
    }

    public Societe pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return this.ville;
    }

    public Societe ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCommune() {
        return this.commune;
    }

    public Societe commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getEmail() {
        return this.email;
    }

    public Societe email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Societe actif(Boolean actif) {
        this.setActif(actif);
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public byte[] getFileData() {
        return this.fileData;
    }

    public Societe fileData(byte[] fileData) {
        this.setFileData(fileData);
        return this;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileDataContentType() {
        return this.fileDataContentType;
    }

    public Societe fileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
        return this;
    }

    public void setFileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
    }

    public String getUrlLogo() {
        return this.urlLogo;
    }

    public Societe urlLogo(String urlLogo) {
        this.setUrlLogo(urlLogo);
        return this;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Set<DetailsStock> getDetailsStocks() {
        return this.detailsStocks;
    }

    public void setDetailsStocks(Set<DetailsStock> detailsStocks) {
        if (this.detailsStocks != null) {
            this.detailsStocks.forEach(i -> i.setSociete(null));
        }
        if (detailsStocks != null) {
            detailsStocks.forEach(i -> i.setSociete(this));
        }
        this.detailsStocks = detailsStocks;
    }

    public Societe detailsStocks(Set<DetailsStock> detailsStocks) {
        this.setDetailsStocks(detailsStocks);
        return this;
    }

    public Societe addDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.add(detailsStock);
        detailsStock.setSociete(this);
        return this;
    }

    public Societe removeDetailsStock(DetailsStock detailsStock) {
        this.detailsStocks.remove(detailsStock);
        detailsStock.setSociete(null);
        return this;
    }

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setSociete(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setSociete(this));
        }
        this.produits = produits;
    }

    public Societe produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public Societe addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setSociete(this);
        return this;
    }

    public Societe removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setSociete(null);
        return this;
    }

    public Set<Fournisseur> getFournisseurs() {
        return this.fournisseurs;
    }

    public void setFournisseurs(Set<Fournisseur> fournisseurs) {
        if (this.fournisseurs != null) {
            this.fournisseurs.forEach(i -> i.setSociete(null));
        }
        if (fournisseurs != null) {
            fournisseurs.forEach(i -> i.setSociete(this));
        }
        this.fournisseurs = fournisseurs;
    }

    public Societe fournisseurs(Set<Fournisseur> fournisseurs) {
        this.setFournisseurs(fournisseurs);
        return this;
    }

    public Societe addFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.add(fournisseur);
        fournisseur.setSociete(this);
        return this;
    }

    public Societe removeFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.remove(fournisseur);
        fournisseur.setSociete(null);
        return this;
    }

    public Set<DocumentAchat> getDocumentAchats() {
        return this.documentAchats;
    }

    public void setDocumentAchats(Set<DocumentAchat> documentAchats) {
        if (this.documentAchats != null) {
            this.documentAchats.forEach(i -> i.setSociete(null));
        }
        if (documentAchats != null) {
            documentAchats.forEach(i -> i.setSociete(this));
        }
        this.documentAchats = documentAchats;
    }

    public Societe documentAchats(Set<DocumentAchat> documentAchats) {
        this.setDocumentAchats(documentAchats);
        return this;
    }

    public Societe addDocumentAchat(DocumentAchat documentAchat) {
        this.documentAchats.add(documentAchat);
        documentAchat.setSociete(this);
        return this;
    }

    public Societe removeDocumentAchat(DocumentAchat documentAchat) {
        this.documentAchats.remove(documentAchat);
        documentAchat.setSociete(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Societe)) {
            return false;
        }
        return id != null && id.equals(((Societe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Societe{" +
            "id=" + getId() +
            ", raisonsoc='" + getRaisonsoc() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", activitepp='" + getActivitepp() + "'" +
            ", adressgeo='" + getAdressgeo() + "'" +
            ", formjuri='" + getFormjuri() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", bp='" + getBp() + "'" +
            ", registreCce='" + getRegistreCce() + "'" +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", commune='" + getCommune() + "'" +
            ", email='" + getEmail() + "'" +
            ", actif='" + getActif() + "'" +
            ", fileData='" + getFileData() + "'" +
            ", fileDataContentType='" + getFileDataContentType() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            "}";
    }
}
