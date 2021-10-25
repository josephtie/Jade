import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeProduit } from '../type-produit.model';
import { TypeProduitService } from '../service/type-produit.service';

@Component({
  templateUrl: './type-produit-delete-dialog.component.html',
})
export class TypeProduitDeleteDialogComponent {
  typeProduit?: ITypeProduit;

  constructor(protected typeProduitService: TypeProduitService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeProduitService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
