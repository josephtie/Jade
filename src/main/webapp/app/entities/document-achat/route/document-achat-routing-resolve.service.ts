import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentAchat, DocumentAchat } from '../document-achat.model';
import { DocumentAchatService } from '../service/document-achat.service';

@Injectable({ providedIn: 'root' })
export class DocumentAchatRoutingResolveService implements Resolve<IDocumentAchat> {
  constructor(protected service: DocumentAchatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentAchat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentAchat: HttpResponse<DocumentAchat>) => {
          if (documentAchat.body) {
            return of(documentAchat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentAchat());
  }
}
