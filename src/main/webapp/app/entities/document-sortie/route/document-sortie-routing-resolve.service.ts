import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentSortie, DocumentSortie } from '../document-sortie.model';
import { DocumentSortieService } from '../service/document-sortie.service';

@Injectable({ providedIn: 'root' })
export class DocumentSortieRoutingResolveService implements Resolve<IDocumentSortie> {
  constructor(protected service: DocumentSortieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentSortie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentSortie: HttpResponse<DocumentSortie>) => {
          if (documentSortie.body) {
            return of(documentSortie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentSortie());
  }
}
