import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentSortieComponent } from '../list/document-sortie.component';
import { DocumentSortieDetailComponent } from '../detail/document-sortie-detail.component';
import { DocumentSortieUpdateComponent } from '../update/document-sortie-update.component';
import { DocumentSortieRoutingResolveService } from './document-sortie-routing-resolve.service';

const documentSortieRoute: Routes = [
  {
    path: '',
    component: DocumentSortieComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentSortieDetailComponent,
    resolve: {
      documentSortie: DocumentSortieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentSortieUpdateComponent,
    resolve: {
      documentSortie: DocumentSortieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentSortieUpdateComponent,
    resolve: {
      documentSortie: DocumentSortieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentSortieRoute)],
  exports: [RouterModule],
})
export class DocumentSortieRoutingModule {}
