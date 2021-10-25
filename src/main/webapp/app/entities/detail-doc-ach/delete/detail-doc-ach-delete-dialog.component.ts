import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailDocAch } from '../detail-doc-ach.model';
import { DetailDocAchService } from '../service/detail-doc-ach.service';

@Component({
  templateUrl: './detail-doc-ach-delete-dialog.component.html',
})
export class DetailDocAchDeleteDialogComponent {
  detailDocAch?: IDetailDocAch;

  constructor(protected detailDocAchService: DetailDocAchService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailDocAchService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
