import { IDetailsStock } from 'app/entities/details-stock/details-stock.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';

export interface ISociete {
  id?: number;
  raisonsoc?: string;
  sigle?: string;
  activitepp?: string;
  adressgeo?: string | null;
  formjuri?: string | null;
  telephone?: string;
  bp?: string | null;
  registreCce?: string | null;
  pays?: string;
  ville?: string;
  commune?: string;
  email?: string;
  actif?: boolean | null;
  fileDataContentType?: string;
  fileData?: string;
  urlLogo?: string | null;
  detailsStocks?: IDetailsStock[] | null;
  produits?: IProduit[] | null;
  fournisseurs?: IFournisseur[] | null;
  documentAchats?: IDocumentAchat[] | null;
}

export class Societe implements ISociete {
  constructor(
    public id?: number,
    public raisonsoc?: string,
    public sigle?: string,
    public activitepp?: string,
    public adressgeo?: string | null,
    public formjuri?: string | null,
    public telephone?: string,
    public bp?: string | null,
    public registreCce?: string | null,
    public pays?: string,
    public ville?: string,
    public commune?: string,
    public email?: string,
    public actif?: boolean | null,
    public fileDataContentType?: string,
    public fileData?: string,
    public urlLogo?: string | null,
    public detailsStocks?: IDetailsStock[] | null,
    public produits?: IProduit[] | null,
    public fournisseurs?: IFournisseur[] | null,
    public documentAchats?: IDocumentAchat[] | null
  ) {
    this.actif = this.actif ?? false;
  }
}

export function getSocieteIdentifier(societe: ISociete): number | undefined {
  return societe.id;
}
