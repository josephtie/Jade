import * as dayjs from 'dayjs';
import { IDetailsStock } from 'app/entities/details-stock/details-stock.model';
import { IDetailDocVte } from 'app/entities/detail-doc-vte/detail-doc-vte.model';

export interface IDocumentVente {
  id?: number;
  dateSaisie?: dayjs.Dayjs | null;
  taxe?: number | null;
  observation?: string | null;
  montantht?: number | null;
  montantttc?: number | null;
  detailsStocks?: IDetailsStock[] | null;
  detailDocVtes?: IDetailDocVte[] | null;
}

export class DocumentVente implements IDocumentVente {
  constructor(
    public id?: number,
    public dateSaisie?: dayjs.Dayjs | null,
    public taxe?: number | null,
    public observation?: string | null,
    public montantht?: number | null,
    public montantttc?: number | null,
    public detailsStocks?: IDetailsStock[] | null,
    public detailDocVtes?: IDetailDocVte[] | null
  ) {}
}

export function getDocumentVenteIdentifier(documentVente: IDocumentVente): number | undefined {
  return documentVente.id;
}
