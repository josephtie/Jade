import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailsStock, DetailsStock } from '../details-stock.model';
import { DetailsStockService } from '../service/details-stock.service';

@Injectable({ providedIn: 'root' })
export class DetailsStockRoutingResolveService implements Resolve<IDetailsStock> {
  constructor(protected service: DetailsStockService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailsStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detailsStock: HttpResponse<DetailsStock>) => {
          if (detailsStock.body) {
            return of(detailsStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailsStock());
  }
}
