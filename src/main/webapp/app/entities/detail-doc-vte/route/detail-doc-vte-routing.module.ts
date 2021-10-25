import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailDocVteComponent } from '../list/detail-doc-vte.component';
import { DetailDocVteDetailComponent } from '../detail/detail-doc-vte-detail.component';
import { DetailDocVteUpdateComponent } from '../update/detail-doc-vte-update.component';
import { DetailDocVteRoutingResolveService } from './detail-doc-vte-routing-resolve.service';

const detailDocVteRoute: Routes = [
  {
    path: '',
    component: DetailDocVteComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailDocVteDetailComponent,
    resolve: {
      detailDocVte: DetailDocVteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailDocVteUpdateComponent,
    resolve: {
      detailDocVte: DetailDocVteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailDocVteUpdateComponent,
    resolve: {
      detailDocVte: DetailDocVteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailDocVteRoute)],
  exports: [RouterModule],
})
export class DetailDocVteRoutingModule {}
