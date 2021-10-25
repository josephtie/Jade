import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentVente, DocumentVente } from '../document-vente.model';
import { DocumentVenteService } from '../service/document-vente.service';

@Injectable({ providedIn: 'root' })
export class DocumentVenteRoutingResolveService implements Resolve<IDocumentVente> {
  constructor(protected service: DocumentVenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentVente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentVente: HttpResponse<DocumentVente>) => {
          if (documentVente.body) {
            return of(documentVente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentVente());
  }
}
