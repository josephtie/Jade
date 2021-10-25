import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentVente } from '../document-vente.model';
import { DocumentVenteService } from '../service/document-vente.service';

@Component({
  templateUrl: './document-vente-delete-dialog.component.html',
})
export class DocumentVenteDeleteDialogComponent {
  documentVente?: IDocumentVente;

  constructor(protected documentVenteService: DocumentVenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentVenteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
