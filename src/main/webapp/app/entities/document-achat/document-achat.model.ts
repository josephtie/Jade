import * as dayjs from 'dayjs';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { ISociete } from 'app/entities/societe/societe.model';
import { IDetailDocAch } from 'app/entities/detail-doc-ach/detail-doc-ach.model';
import { IDetailsStock } from 'app/entities/details-stock/details-stock.model';

export interface IDocumentAchat {
  id?: number;
  dateSaisie?: dayjs.Dayjs;
  taxe?: number;
  observation?: string | null;
  montantht?: number;
  montantttc?: number;
  fournisseur?: IFournisseur | null;
  societe?: ISociete | null;
  detailDocAches?: IDetailDocAch[] | null;
  detailsStocks?: IDetailsStock[] | null;
}

export class DocumentAchat implements IDocumentAchat {
  constructor(
    public id?: number,
    public dateSaisie?: dayjs.Dayjs,
    public taxe?: number,
    public observation?: string | null,
    public montantht?: number,
    public montantttc?: number,
    public fournisseur?: IFournisseur | null,
    public societe?: ISociete | null,
    public detailDocAches?: IDetailDocAch[] | null,
    public detailsStocks?: IDetailsStock[] | null
  ) {}
}

export function getDocumentAchatIdentifier(documentAchat: IDocumentAchat): number | undefined {
  return documentAchat.id;
}
