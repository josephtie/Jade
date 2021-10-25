import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentSortie } from '../document-sortie.model';
import { DocumentSortieService } from '../service/document-sortie.service';

@Component({
  templateUrl: './document-sortie-delete-dialog.component.html',
})
export class DocumentSortieDeleteDialogComponent {
  documentSortie?: IDocumentSortie;

  constructor(protected documentSortieService: DocumentSortieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentSortieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
