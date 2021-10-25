import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentAchat } from '../document-achat.model';
import { DocumentAchatService } from '../service/document-achat.service';

@Component({
  templateUrl: './document-achat-delete-dialog.component.html',
})
export class DocumentAchatDeleteDialogComponent {
  documentAchat?: IDocumentAchat;

  constructor(protected documentAchatService: DocumentAchatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentAchatService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
