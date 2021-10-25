import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailsStock } from '../details-stock.model';
import { DetailsStockService } from '../service/details-stock.service';

@Component({
  templateUrl: './details-stock-delete-dialog.component.html',
})
export class DetailsStockDeleteDialogComponent {
  detailsStock?: IDetailsStock;

  constructor(protected detailsStockService: DetailsStockService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailsStockService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
