import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentVente, getDocumentVenteIdentifier } from '../document-vente.model';

export type EntityResponseType = HttpResponse<IDocumentVente>;
export type EntityArrayResponseType = HttpResponse<IDocumentVente[]>;

@Injectable({ providedIn: 'root' })
export class DocumentVenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-ventes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentVente: IDocumentVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentVente);
    return this.http
      .post<IDocumentVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documentVente: IDocumentVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentVente);
    return this.http
      .put<IDocumentVente>(`${this.resourceUrl}/${getDocumentVenteIdentifier(documentVente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(documentVente: IDocumentVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentVente);
    return this.http
      .patch<IDocumentVente>(`${this.resourceUrl}/${getDocumentVenteIdentifier(documentVente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumentVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDocumentVenteToCollectionIfMissing(
    documentVenteCollection: IDocumentVente[],
    ...documentVentesToCheck: (IDocumentVente | null | undefined)[]
  ): IDocumentVente[] {
    const documentVentes: IDocumentVente[] = documentVentesToCheck.filter(isPresent);
    if (documentVentes.length > 0) {
      const documentVenteCollectionIdentifiers = documentVenteCollection.map(
        documentVenteItem => getDocumentVenteIdentifier(documentVenteItem)!
      );
      const documentVentesToAdd = documentVentes.filter(documentVenteItem => {
        const documentVenteIdentifier = getDocumentVenteIdentifier(documentVenteItem);
        if (documentVenteIdentifier == null || documentVenteCollectionIdentifiers.includes(documentVenteIdentifier)) {
          return false;
        }
        documentVenteCollectionIdentifiers.push(documentVenteIdentifier);
        return true;
      });
      return [...documentVentesToAdd, ...documentVenteCollection];
    }
    return documentVenteCollection;
  }

  protected convertDateFromClient(documentVente: IDocumentVente): IDocumentVente {
    return Object.assign({}, documentVente, {
      dateSaisie: documentVente.dateSaisie?.isValid() ? documentVente.dateSaisie.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSaisie = res.body.dateSaisie ? dayjs(res.body.dateSaisie) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((documentVente: IDocumentVente) => {
        documentVente.dateSaisie = documentVente.dateSaisie ? dayjs(documentVente.dateSaisie) : undefined;
      });
    }
    return res;
  }
}
