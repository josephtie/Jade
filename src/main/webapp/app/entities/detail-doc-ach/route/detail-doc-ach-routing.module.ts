import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailDocAchComponent } from '../list/detail-doc-ach.component';
import { DetailDocAchDetailComponent } from '../detail/detail-doc-ach-detail.component';
import { DetailDocAchUpdateComponent } from '../update/detail-doc-ach-update.component';
import { DetailDocAchRoutingResolveService } from './detail-doc-ach-routing-resolve.service';

const detailDocAchRoute: Routes = [
  {
    path: '',
    component: DetailDocAchComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailDocAchDetailComponent,
    resolve: {
      detailDocAch: DetailDocAchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailDocAchUpdateComponent,
    resolve: {
      detailDocAch: DetailDocAchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailDocAchUpdateComponent,
    resolve: {
      detailDocAch: DetailDocAchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailDocAchRoute)],
  exports: [RouterModule],
})
export class DetailDocAchRoutingModule {}
