import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentVenteComponent } from '../list/document-vente.component';
import { DocumentVenteDetailComponent } from '../detail/document-vente-detail.component';
import { DocumentVenteUpdateComponent } from '../update/document-vente-update.component';
import { DocumentVenteRoutingResolveService } from './document-vente-routing-resolve.service';

const documentVenteRoute: Routes = [
  {
    path: '',
    component: DocumentVenteComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentVenteDetailComponent,
    resolve: {
      documentVente: DocumentVenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentVenteUpdateComponent,
    resolve: {
      documentVente: DocumentVenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentVenteUpdateComponent,
    resolve: {
      documentVente: DocumentVenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentVenteRoute)],
  exports: [RouterModule],
})
export class DocumentVenteRoutingModule {}
