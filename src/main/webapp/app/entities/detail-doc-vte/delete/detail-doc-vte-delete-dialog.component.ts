import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailDocVte } from '../detail-doc-vte.model';
import { DetailDocVteService } from '../service/detail-doc-vte.service';

@Component({
  templateUrl: './detail-doc-vte-delete-dialog.component.html',
})
export class DetailDocVteDeleteDialogComponent {
  detailDocVte?: IDetailDocVte;

  constructor(protected detailDocVteService: DetailDocVteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailDocVteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
