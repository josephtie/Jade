import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailsStock, getDetailsStockIdentifier } from '../details-stock.model';

export type EntityResponseType = HttpResponse<IDetailsStock>;
export type EntityArrayResponseType = HttpResponse<IDetailsStock[]>;

@Injectable({ providedIn: 'root' })
export class DetailsStockService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/details-stocks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detailsStock: IDetailsStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailsStock);
    return this.http
      .post<IDetailsStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detailsStock: IDetailsStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailsStock);
    return this.http
      .put<IDetailsStock>(`${this.resourceUrl}/${getDetailsStockIdentifier(detailsStock) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(detailsStock: IDetailsStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailsStock);
    return this.http
      .patch<IDetailsStock>(`${this.resourceUrl}/${getDetailsStockIdentifier(detailsStock) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetailsStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetailsStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetailsStockToCollectionIfMissing(
    detailsStockCollection: IDetailsStock[],
    ...detailsStocksToCheck: (IDetailsStock | null | undefined)[]
  ): IDetailsStock[] {
    const detailsStocks: IDetailsStock[] = detailsStocksToCheck.filter(isPresent);
    if (detailsStocks.length > 0) {
      const detailsStockCollectionIdentifiers = detailsStockCollection.map(
        detailsStockItem => getDetailsStockIdentifier(detailsStockItem)!
      );
      const detailsStocksToAdd = detailsStocks.filter(detailsStockItem => {
        const detailsStockIdentifier = getDetailsStockIdentifier(detailsStockItem);
        if (detailsStockIdentifier == null || detailsStockCollectionIdentifiers.includes(detailsStockIdentifier)) {
          return false;
        }
        detailsStockCollectionIdentifiers.push(detailsStockIdentifier);
        return true;
      });
      return [...detailsStocksToAdd, ...detailsStockCollection];
    }
    return detailsStockCollection;
  }

  protected convertDateFromClient(detailsStock: IDetailsStock): IDetailsStock {
    return Object.assign({}, detailsStock, {
      dateSaisie: detailsStock.dateSaisie?.isValid() ? detailsStock.dateSaisie.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((detailsStock: IDetailsStock) => {
        detailsStock.dateSaisie = detailsStock.dateSaisie ? dayjs(detailsStock.dateSaisie) : undefined;
      });
    }
    return res;
  }
}
