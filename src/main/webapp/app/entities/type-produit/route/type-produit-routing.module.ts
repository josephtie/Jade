import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeProduitComponent } from '../list/type-produit.component';
import { TypeProduitDetailComponent } from '../detail/type-produit-detail.component';
import { TypeProduitUpdateComponent } from '../update/type-produit-update.component';
import { TypeProduitRoutingResolveService } from './type-produit-routing-resolve.service';

const typeProduitRoute: Routes = [
  {
    path: '',
    component: TypeProduitComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeProduitDetailComponent,
    resolve: {
      typeProduit: TypeProduitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeProduitUpdateComponent,
    resolve: {
      typeProduit: TypeProduitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeProduitUpdateComponent,
    resolve: {
      typeProduit: TypeProduitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeProduitRoute)],
  exports: [RouterModule],
})
export class TypeProduitRoutingModule {}
