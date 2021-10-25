import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailDocVte, getDetailDocVteIdentifier } from '../detail-doc-vte.model';

export type EntityResponseType = HttpResponse<IDetailDocVte>;
export type EntityArrayResponseType = HttpResponse<IDetailDocVte[]>;

@Injectable({ providedIn: 'root' })
export class DetailDocVteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detail-doc-vtes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detailDocVte: IDetailDocVte): Observable<EntityResponseType> {
    return this.http.post<IDetailDocVte>(this.resourceUrl, detailDocVte, { observe: 'response' });
  }

  update(detailDocVte: IDetailDocVte): Observable<EntityResponseType> {
    return this.http.put<IDetailDocVte>(`${this.resourceUrl}/${getDetailDocVteIdentifier(detailDocVte) as number}`, detailDocVte, {
      observe: 'response',
    });
  }

  partialUpdate(detailDocVte: IDetailDocVte): Observable<EntityResponseType> {
    return this.http.patch<IDetailDocVte>(`${this.resourceUrl}/${getDetailDocVteIdentifier(detailDocVte) as number}`, detailDocVte, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailDocVte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailDocVte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetailDocVteToCollectionIfMissing(
    detailDocVteCollection: IDetailDocVte[],
    ...detailDocVtesToCheck: (IDetailDocVte | null | undefined)[]
  ): IDetailDocVte[] {
    const detailDocVtes: IDetailDocVte[] = detailDocVtesToCheck.filter(isPresent);
    if (detailDocVtes.length > 0) {
      const detailDocVteCollectionIdentifiers = detailDocVteCollection.map(
        detailDocVteItem => getDetailDocVteIdentifier(detailDocVteItem)!
      );
      const detailDocVtesToAdd = detailDocVtes.filter(detailDocVteItem => {
        const detailDocVteIdentifier = getDetailDocVteIdentifier(detailDocVteItem);
        if (detailDocVteIdentifier == null || detailDocVteCollectionIdentifiers.includes(detailDocVteIdentifier)) {
          return false;
        }
        detailDocVteCollectionIdentifiers.push(detailDocVteIdentifier);
        return true;
      });
      return [...detailDocVtesToAdd, ...detailDocVteCollection];
    }
    return detailDocVteCollection;
  }
}
