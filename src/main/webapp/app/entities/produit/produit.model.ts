import { ISociete } from 'app/entities/societe/societe.model';
import { ITypeProduit } from 'app/entities/type-produit/type-produit.model';
import { IDetailDocAch } from 'app/entities/detail-doc-ach/detail-doc-ach.model';
import { IDetailsStock } from 'app/entities/details-stock/details-stock.model';
import { IDetailDocVte } from 'app/entities/detail-doc-vte/detail-doc-vte.model';

export interface IProduit {
  id?: number;
  libelle?: string;
  stockinit?: number;
  stockApprov?: number | null;
  boissonPrixUnitairenet?: number | null;
  societe?: ISociete | null;
  typeProduit?: ITypeProduit | null;
  detailDocAches?: IDetailDocAch[] | null;
  detailsStocks?: IDetailsStock[] | null;
  detailDocVtes?: IDetailDocVte[] | null;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public libelle?: string,
    public stockinit?: number,
    public stockApprov?: number | null,
    public boissonPrixUnitairenet?: number | null,
    public societe?: ISociete | null,
    public typeProduit?: ITypeProduit | null,
    public detailDocAches?: IDetailDocAch[] | null,
    public detailsStocks?: IDetailsStock[] | null,
    public detailDocVtes?: IDetailDocVte[] | null
  ) {}
}

export function getProduitIdentifier(produit: IProduit): number | undefined {
  return produit.id;
}
