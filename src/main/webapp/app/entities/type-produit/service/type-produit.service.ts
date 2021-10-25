import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeProduit, getTypeProduitIdentifier } from '../type-produit.model';

export type EntityResponseType = HttpResponse<ITypeProduit>;
export type EntityArrayResponseType = HttpResponse<ITypeProduit[]>;

@Injectable({ providedIn: 'root' })
export class TypeProduitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-produits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeProduit: ITypeProduit): Observable<EntityResponseType> {
    return this.http.post<ITypeProduit>(this.resourceUrl, typeProduit, { observe: 'response' });
  }

  update(typeProduit: ITypeProduit): Observable<EntityResponseType> {
    return this.http.put<ITypeProduit>(`${this.resourceUrl}/${getTypeProduitIdentifier(typeProduit) as number}`, typeProduit, {
      observe: 'response',
    });
  }

  partialUpdate(typeProduit: ITypeProduit): Observable<EntityResponseType> {
    return this.http.patch<ITypeProduit>(`${this.resourceUrl}/${getTypeProduitIdentifier(typeProduit) as number}`, typeProduit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeProduit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeProduitToCollectionIfMissing(
    typeProduitCollection: ITypeProduit[],
    ...typeProduitsToCheck: (ITypeProduit | null | undefined)[]
  ): ITypeProduit[] {
    const typeProduits: ITypeProduit[] = typeProduitsToCheck.filter(isPresent);
    if (typeProduits.length > 0) {
      const typeProduitCollectionIdentifiers = typeProduitCollection.map(typeProduitItem => getTypeProduitIdentifier(typeProduitItem)!);
      const typeProduitsToAdd = typeProduits.filter(typeProduitItem => {
        const typeProduitIdentifier = getTypeProduitIdentifier(typeProduitItem);
        if (typeProduitIdentifier == null || typeProduitCollectionIdentifiers.includes(typeProduitIdentifier)) {
          return false;
        }
        typeProduitCollectionIdentifiers.push(typeProduitIdentifier);
        return true;
      });
      return [...typeProduitsToAdd, ...typeProduitCollection];
    }
    return typeProduitCollection;
  }
}
