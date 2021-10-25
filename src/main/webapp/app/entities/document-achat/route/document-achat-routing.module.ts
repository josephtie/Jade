import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentAchatComponent } from '../list/document-achat.component';
import { DocumentAchatDetailComponent } from '../detail/document-achat-detail.component';
import { DocumentAchatUpdateComponent } from '../update/document-achat-update.component';
import { DocumentAchatRoutingResolveService } from './document-achat-routing-resolve.service';

const documentAchatRoute: Routes = [
  {
    path: '',
    component: DocumentAchatComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentAchatDetailComponent,
    resolve: {
      documentAchat: DocumentAchatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentAchatUpdateComponent,
    resolve: {
      documentAchat: DocumentAchatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentAchatUpdateComponent,
    resolve: {
      documentAchat: DocumentAchatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentAchatRoute)],
  exports: [RouterModule],
})
export class DocumentAchatRoutingModule {}
