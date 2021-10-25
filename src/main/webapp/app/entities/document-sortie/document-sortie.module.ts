import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumentSortieComponent } from './list/document-sortie.component';
import { DocumentSortieDetailComponent } from './detail/document-sortie-detail.component';
import { DocumentSortieUpdateComponent } from './update/document-sortie-update.component';
import { DocumentSortieDeleteDialogComponent } from './delete/document-sortie-delete-dialog.component';
import { DocumentSortieRoutingModule } from './route/document-sortie-routing.module';

@NgModule({
  imports: [SharedModule, DocumentSortieRoutingModule],
  declarations: [
    DocumentSortieComponent,
    DocumentSortieDetailComponent,
    DocumentSortieUpdateComponent,
    DocumentSortieDeleteDialogComponent,
  ],
  entryComponents: [DocumentSortieDeleteDialogComponent],
})
export class DocumentSortieModule {}
