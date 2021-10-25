import { IProduit } from 'app/entities/produit/produit.model';
import { IDocumentVente } from 'app/entities/document-vente/document-vente.model';

export interface IDetailDocVte {
  id?: number;
  prixUnit?: number;
  prixunitnet?: number | null;
  montligne?: number;
  qteUnit?: number;
  remise?: number | null;
  quantitecolis?: number | null;
  designation?: string | null;
  produit?: IProduit | null;
  documentVente?: IDocumentVente | null;
}

export class DetailDocVte implements IDetailDocVte {
  constructor(
    public id?: number,
    public prixUnit?: number,
    public prixunitnet?: number | null,
    public montligne?: number,
    public qteUnit?: number,
    public remise?: number | null,
    public quantitecolis?: number | null,
    public designation?: string | null,
    public produit?: IProduit | null,
    public documentVente?: IDocumentVente | null
  ) {}
}

export function getDetailDocVteIdentifier(detailDocVte: IDetailDocVte): number | undefined {
  return detailDocVte.id;
}
