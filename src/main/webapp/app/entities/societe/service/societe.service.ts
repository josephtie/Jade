import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISociete, getSocieteIdentifier } from '../societe.model';

export type EntityResponseType = HttpResponse<ISociete>;
export type EntityArrayResponseType = HttpResponse<ISociete[]>;

@Injectable({ providedIn: 'root' })
export class SocieteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/societes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(societe: ISociete): Observable<EntityResponseType> {
    return this.http.post<ISociete>(this.resourceUrl, societe, { observe: 'response' });
  }

  update(societe: ISociete): Observable<EntityResponseType> {
    return this.http.put<ISociete>(`${this.resourceUrl}/${getSocieteIdentifier(societe) as number}`, societe, { observe: 'response' });
  }

  partialUpdate(societe: ISociete): Observable<EntityResponseType> {
    return this.http.patch<ISociete>(`${this.resourceUrl}/${getSocieteIdentifier(societe) as number}`, societe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISociete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISociete[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSocieteToCollectionIfMissing(societeCollection: ISociete[], ...societesToCheck: (ISociete | null | undefined)[]): ISociete[] {
    const societes: ISociete[] = societesToCheck.filter(isPresent);
    if (societes.length > 0) {
      const societeCollectionIdentifiers = societeCollection.map(societeItem => getSocieteIdentifier(societeItem)!);
      const societesToAdd = societes.filter(societeItem => {
        const societeIdentifier = getSocieteIdentifier(societeItem);
        if (societeIdentifier == null || societeCollectionIdentifiers.includes(societeIdentifier)) {
          return false;
        }
        societeCollectionIdentifiers.push(societeIdentifier);
        return true;
      });
      return [...societesToAdd, ...societeCollection];
    }
    return societeCollection;
  }
}
