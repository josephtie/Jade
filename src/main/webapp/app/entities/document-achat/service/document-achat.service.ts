import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentAchat, getDocumentAchatIdentifier } from '../document-achat.model';

export type EntityResponseType = HttpResponse<IDocumentAchat>;
export type EntityArrayResponseType = HttpResponse<IDocumentAchat[]>;

@Injectable({ providedIn: 'root' })
export class DocumentAchatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-achats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentAchat: IDocumentAchat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentAchat);
    return this.http
      .post<IDocumentAchat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documentAchat: IDocumentAchat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentAchat);
    return this.http
      .put<IDocumentAchat>(`${this.resourceUrl}/${getDocumentAchatIdentifier(documentAchat) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(documentAchat: IDocumentAchat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentAchat);
    return this.http
      .patch<IDocumentAchat>(`${this.resourceUrl}/${getDocumentAchatIdentifier(documentAchat) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumentAchat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentAchat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDocumentAchatToCollectionIfMissing(
    documentAchatCollection: IDocumentAchat[],
    ...documentAchatsToCheck: (IDocumentAchat | null | undefined)[]
  ): IDocumentAchat[] {
    const documentAchats: IDocumentAchat[] = documentAchatsToCheck.filter(isPresent);
    if (documentAchats.length > 0) {
      const documentAchatCollectionIdentifiers = documentAchatCollection.map(
        documentAchatItem => getDocumentAchatIdentifier(documentAchatItem)!
      );
      const documentAchatsToAdd = documentAchats.filter(documentAchatItem => {
        const documentAchatIdentifier = getDocumentAchatIdentifier(documentAchatItem);
        if (documentAchatIdentifier == null || documentAchatCollectionIdentifiers.includes(documentAchatIdentifier)) {
          return false;
        }
        documentAchatCollectionIdentifiers.push(documentAchatIdentifier);
        return true;
      });
      return [...documentAchatsToAdd, ...documentAchatCollection];
    }
    return documentAchatCollection;
  }

  protected convertDateFromClient(documentAchat: IDocumentAchat): IDocumentAchat {
    return Object.assign({}, documentAchat, {
      dateSaisie: documentAchat.dateSaisie?.isValid() ? documentAchat.dateSaisie.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((documentAchat: IDocumentAchat) => {
        documentAchat.dateSaisie = documentAchat.dateSaisie ? dayjs(documentAchat.dateSaisie) : undefined;
      });
    }
    return res;
  }
}
