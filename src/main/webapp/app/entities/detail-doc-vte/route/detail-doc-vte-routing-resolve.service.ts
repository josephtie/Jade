import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailDocVte, DetailDocVte } from '../detail-doc-vte.model';
import { DetailDocVteService } from '../service/detail-doc-vte.service';

@Injectable({ providedIn: 'root' })
export class DetailDocVteRoutingResolveService implements Resolve<IDetailDocVte> {
  constructor(protected service: DetailDocVteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailDocVte> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detailDocVte: HttpResponse<DetailDocVte>) => {
          if (detailDocVte.body) {
            return of(detailDocVte.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailDocVte());
  }
}
