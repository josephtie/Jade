import * as dayjs from 'dayjs';
import { IDetailsStock } from 'app/entities/details-stock/details-stock.model';

export interface IDocumentSortie {
  id?: number;
  dateSaisie?: dayjs.Dayjs | null;
  taxe?: number | null;
  observation?: string | null;
  montantht?: number | null;
  montantttc?: number | null;
  detailsStocks?: IDetailsStock[] | null;
}

export class DocumentSortie implements IDocumentSortie {
  constructor(
    public id?: number,
    public dateSaisie?: dayjs.Dayjs | null,
    public taxe?: number | null,
    public observation?: string | null,
    public montantht?: number | null,
    public montantttc?: number | null,
    public detailsStocks?: IDetailsStock[] | null
  ) {}
}

export function getDocumentSortieIdentifier(documentSortie: IDocumentSortie): number | undefined {
  return documentSortie.id;
}
