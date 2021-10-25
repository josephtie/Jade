import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeProduit, TypeProduit } from '../type-produit.model';
import { TypeProduitService } from '../service/type-produit.service';

@Injectable({ providedIn: 'root' })
export class TypeProduitRoutingResolveService implements Resolve<ITypeProduit> {
  constructor(protected service: TypeProduitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeProduit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeProduit: HttpResponse<TypeProduit>) => {
          if (typeProduit.body) {
            return of(typeProduit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeProduit());
  }
}
