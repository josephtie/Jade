import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetailDocAchComponent } from './list/detail-doc-ach.component';
import { DetailDocAchDetailComponent } from './detail/detail-doc-ach-detail.component';
import { DetailDocAchUpdateComponent } from './update/detail-doc-ach-update.component';
import { DetailDocAchDeleteDialogComponent } from './delete/detail-doc-ach-delete-dialog.component';
import { DetailDocAchRoutingModule } from './route/detail-doc-ach-routing.module';

@NgModule({
  imports: [SharedModule, DetailDocAchRoutingModule],
  declarations: [DetailDocAchComponent, DetailDocAchDetailComponent, DetailDocAchUpdateComponent, DetailDocAchDeleteDialogComponent],
  entryComponents: [DetailDocAchDeleteDialogComponent],
})
export class DetailDocAchModule {}
