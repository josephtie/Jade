import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetailDocVteComponent } from './list/detail-doc-vte.component';
import { DetailDocVteDetailComponent } from './detail/detail-doc-vte-detail.component';
import { DetailDocVteUpdateComponent } from './update/detail-doc-vte-update.component';
import { DetailDocVteDeleteDialogComponent } from './delete/detail-doc-vte-delete-dialog.component';
import { DetailDocVteRoutingModule } from './route/detail-doc-vte-routing.module';

@NgModule({
  imports: [SharedModule, DetailDocVteRoutingModule],
  declarations: [DetailDocVteComponent, DetailDocVteDetailComponent, DetailDocVteUpdateComponent, DetailDocVteDeleteDialogComponent],
  entryComponents: [DetailDocVteDeleteDialogComponent],
})
export class DetailDocVteModule {}
