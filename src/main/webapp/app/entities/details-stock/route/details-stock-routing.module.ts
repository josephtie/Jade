import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailsStockComponent } from '../list/details-stock.component';
import { DetailsStockDetailComponent } from '../detail/details-stock-detail.component';
import { DetailsStockUpdateComponent } from '../update/details-stock-update.component';
import { DetailsStockRoutingResolveService } from './details-stock-routing-resolve.service';

const detailsStockRoute: Routes = [
  {
    path: '',
    component: DetailsStockComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailsStockDetailComponent,
    resolve: {
      detailsStock: DetailsStockRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailsStockUpdateComponent,
    resolve: {
      detailsStock: DetailsStockRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailsStockUpdateComponent,
    resolve: {
      detailsStock: DetailsStockRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailsStockRoute)],
  exports: [RouterModule],
})
export class DetailsStockRoutingModule {}
