import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumentVenteComponent } from './list/document-vente.component';
import { DocumentVenteDetailComponent } from './detail/document-vente-detail.component';
import { DocumentVenteUpdateComponent } from './update/document-vente-update.component';
import { DocumentVenteDeleteDialogComponent } from './delete/document-vente-delete-dialog.component';
import { DocumentVenteRoutingModule } from './route/document-vente-routing.module';

@NgModule({
  imports: [SharedModule, DocumentVenteRoutingModule],
  declarations: [DocumentVenteComponent, DocumentVenteDetailComponent, DocumentVenteUpdateComponent, DocumentVenteDeleteDialogComponent],
  entryComponents: [DocumentVenteDeleteDialogComponent],
})
export class DocumentVenteModule {}
