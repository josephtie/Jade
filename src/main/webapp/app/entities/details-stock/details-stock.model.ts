import * as dayjs from 'dayjs';
import { IProduit } from 'app/entities/produit/produit.model';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';
import { IDocumentVente } from 'app/entities/document-vente/document-vente.model';
import { IDocumentSortie } from 'app/entities/document-sortie/document-sortie.model';
import { ISociete } from 'app/entities/societe/societe.model';

export interface IDetailsStock {
  id?: number;
  stkQTEentrant?: number;
  stkQTEinitial?: number;
  stkQTEreel?: number;
  idCommande?: number | null;
  idVente?: number | null;
  idSortie?: number | null;
  dateSaisie?: dayjs.Dayjs | null;
  montunitaireOP?: number | null;
  produit?: IProduit | null;
  documentAchat?: IDocumentAchat | null;
  documentVente?: IDocumentVente | null;
  documentSortie?: IDocumentSortie | null;
  societe?: ISociete | null;
}

export class DetailsStock implements IDetailsStock {
  constructor(
    public id?: number,
    public stkQTEentrant?: number,
    public stkQTEinitial?: number,
    public stkQTEreel?: number,
    public idCommande?: number | null,
    public idVente?: number | null,
    public idSortie?: number | null,
    public dateSaisie?: dayjs.Dayjs | null,
    public montunitaireOP?: number | null,
    public produit?: IProduit | null,
    public documentAchat?: IDocumentAchat | null,
    public documentVente?: IDocumentVente | null,
    public documentSortie?: IDocumentSortie | null,
    public societe?: ISociete | null
  ) {}
}

export function getDetailsStockIdentifier(detailsStock: IDetailsStock): number | undefined {
  return detailsStock.id;
}
