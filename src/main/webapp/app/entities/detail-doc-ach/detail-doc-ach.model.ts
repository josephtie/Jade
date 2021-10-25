import { IProduit } from 'app/entities/produit/produit.model';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';

export interface IDetailDocAch {
  id?: number;
  prixUnit?: number;
  prixunitnet?: number | null;
  montligne?: number;
  qteUnit?: number;
  remise?: number | null;
  quantitecolis?: number | null;
  designation?: string | null;
  produit?: IProduit | null;
  documentAchat?: IDocumentAchat | null;
}

export class DetailDocAch implements IDetailDocAch {
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
    public documentAchat?: IDocumentAchat | null
  ) {}
}

export function getDetailDocAchIdentifier(detailDocAch: IDetailDocAch): number | undefined {
  return detailDocAch.id;
}
