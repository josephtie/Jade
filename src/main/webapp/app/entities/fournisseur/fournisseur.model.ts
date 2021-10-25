import { ISociete } from 'app/entities/societe/societe.model';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';

export interface IFournisseur {
  id?: number;
  codeFournisseur?: string;
  raisonFournisseur?: string;
  adrfournisseur?: string | null;
  paysFournisseur?: string | null;
  villeFournisseur?: string;
  mailFournisseur?: string | null;
  celFournisseur?: string;
  telFournisseur?: string | null;
  societe?: ISociete | null;
  documentAchat?: IDocumentAchat | null;
}

export class Fournisseur implements IFournisseur {
  constructor(
    public id?: number,
    public codeFournisseur?: string,
    public raisonFournisseur?: string,
    public adrfournisseur?: string | null,
    public paysFournisseur?: string | null,
    public villeFournisseur?: string,
    public mailFournisseur?: string | null,
    public celFournisseur?: string,
    public telFournisseur?: string | null,
    public societe?: ISociete | null,
    public documentAchat?: IDocumentAchat | null
  ) {}
}

export function getFournisseurIdentifier(fournisseur: IFournisseur): number | undefined {
  return fournisseur.id;
}
