import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailDocAch, DetailDocAch } from '../detail-doc-ach.model';
import { DetailDocAchService } from '../service/detail-doc-ach.service';

@Injectable({ providedIn: 'root' })
export class DetailDocAchRoutingResolveService implements Resolve<IDetailDocAch> {
  constructor(protected service: DetailDocAchService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailDocAch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detailDocAch: HttpResponse<DetailDocAch>) => {
          if (detailDocAch.body) {
            return of(detailDocAch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailDocAch());
  }
}
