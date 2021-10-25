import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailDocAch, getDetailDocAchIdentifier } from '../detail-doc-ach.model';

export type EntityResponseType = HttpResponse<IDetailDocAch>;
export type EntityArrayResponseType = HttpResponse<IDetailDocAch[]>;

@Injectable({ providedIn: 'root' })
export class DetailDocAchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detail-doc-aches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detailDocAch: IDetailDocAch): Observable<EntityResponseType> {
    return this.http.post<IDetailDocAch>(this.resourceUrl, detailDocAch, { observe: 'response' });
  }

  update(detailDocAch: IDetailDocAch): Observable<EntityResponseType> {
    return this.http.put<IDetailDocAch>(`${this.resourceUrl}/${getDetailDocAchIdentifier(detailDocAch) as number}`, detailDocAch, {
      observe: 'response',
    });
  }

  partialUpdate(detailDocAch: IDetailDocAch): Observable<EntityResponseType> {
    return this.http.patch<IDetailDocAch>(`${this.resourceUrl}/${getDetailDocAchIdentifier(detailDocAch) as number}`, detailDocAch, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailDocAch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailDocAch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetailDocAchToCollectionIfMissing(
    detailDocAchCollection: IDetailDocAch[],
    ...detailDocAchesToCheck: (IDetailDocAch | null | undefined)[]
  ): IDetailDocAch[] {
    const detailDocAches: IDetailDocAch[] = detailDocAchesToCheck.filter(isPresent);
    if (detailDocAches.length > 0) {
      const detailDocAchCollectionIdentifiers = detailDocAchCollection.map(
        detailDocAchItem => getDetailDocAchIdentifier(detailDocAchItem)!
      );
      const detailDocAchesToAdd = detailDocAches.filter(detailDocAchItem => {
        const detailDocAchIdentifier = getDetailDocAchIdentifier(detailDocAchItem);
        if (detailDocAchIdentifier == null || detailDocAchCollectionIdentifiers.includes(detailDocAchIdentifier)) {
          return false;
        }
        detailDocAchCollectionIdentifiers.push(detailDocAchIdentifier);
        return true;
      });
      return [...detailDocAchesToAdd, ...detailDocAchCollection];
    }
    return detailDocAchCollection;
  }
}
