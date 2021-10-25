import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumentAchatComponent } from './list/document-achat.component';
import { DocumentAchatDetailComponent } from './detail/document-achat-detail.component';
import { DocumentAchatUpdateComponent } from './update/document-achat-update.component';
import { DocumentAchatDeleteDialogComponent } from './delete/document-achat-delete-dialog.component';
import { DocumentAchatRoutingModule } from './route/document-achat-routing.module';

@NgModule({
  imports: [SharedModule, DocumentAchatRoutingModule],
  declarations: [DocumentAchatComponent, DocumentAchatDetailComponent, DocumentAchatUpdateComponent, DocumentAchatDeleteDialogComponent],
  entryComponents: [DocumentAchatDeleteDialogComponent],
})
export class DocumentAchatModule {}
