import { IProduit } from 'app/entities/produit/produit.model';

export interface ITypeProduit {
  id?: number;
  libelle?: string;
  description?: string | null;
  produits?: IProduit[] | null;
}

export class TypeProduit implements ITypeProduit {
  constructor(public id?: number, public libelle?: string, public description?: string | null, public produits?: IProduit[] | null) {}
}

export function getTypeProduitIdentifier(typeProduit: ITypeProduit): number | undefined {
  return typeProduit.id;
}
