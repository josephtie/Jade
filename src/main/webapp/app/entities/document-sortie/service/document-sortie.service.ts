import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentSortie, getDocumentSortieIdentifier } from '../document-sortie.model';

export type EntityResponseType = HttpResponse<IDocumentSortie>;
export type EntityArrayResponseType = HttpResponse<IDocumentSortie[]>;

@Injectable({ providedIn: 'root' })
export class DocumentSortieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-sorties');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentSortie: IDocumentSortie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentSortie);
    return this.http
      .post<IDocumentSortie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documentSortie: IDocumentSortie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentSortie);
    return this.http
      .put<IDocumentSortie>(`${this.resourceUrl}/${getDocumentSortieIdentifier(documentSortie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(documentSortie: IDocumentSortie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentSortie);
    return this.http
      .patch<IDocumentSortie>(`${this.resourceUrl}/${getDocumentSortieIdentifier(documentSortie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumentSortie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentSortie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDocumentSortieToCollectionIfMissing(
    documentSortieCollection: IDocumentSortie[],
    ...documentSortiesToCheck: (IDocumentSortie | null | undefined)[]
  ): IDocumentSortie[] {
    const documentSorties: IDocumentSortie[] = documentSortiesToCheck.filter(isPresent);
    if (documentSorties.length > 0) {
      const documentSortieCollectionIdentifiers = documentSortieCollection.map(
        documentSortieItem => getDocumentSortieIdentifier(documentSortieItem)!
      );
      const documentSortiesToAdd = documentSorties.filter(documentSortieItem => {
        const documentSortieIdentifier = getDocumentSortieIdentifier(documentSortieItem);
        if (documentSortieIdentifier == null || documentSortieCollectionIdentifiers.includes(documentSortieIdentifier)) {
          return false;
        }
        documentSortieCollectionIdentifiers.push(documentSortieIdentifier);
        return true;
      });
      return [...documentSortiesToAdd, ...documentSortieCollection];
    }
    return documentSortieCollection;
  }

  protected convertDateFromClient(documentSortie: IDocumentSortie): IDocumentSortie {
    return Object.assign({}, documentSortie, {
      dateSaisie: documentSortie.dateSaisie?.isValid() ? documentSortie.dateSaisie.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((documentSortie: IDocumentSortie) => {
        documentSortie.dateSaisie = documentSortie.dateSaisie ? dayjs(documentSortie.dateSaisie) : undefined;
      });
    }
    return res;
  }
}
