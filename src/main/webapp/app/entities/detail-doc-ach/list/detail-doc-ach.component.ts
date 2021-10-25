import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailDocAch } from '../detail-doc-ach.model';
import { DetailDocAchService } from '../service/detail-doc-ach.service';
import { DetailDocAchDeleteDialogComponent } from '../delete/detail-doc-ach-delete-dialog.component';

@Component({
  selector: 'jhi-detail-doc-ach',
  templateUrl: './detail-doc-ach.component.html',
})
export class DetailDocAchComponent implements OnInit {
  detailDocAches?: IDetailDocAch[];
  isLoading = false;

  constructor(protected detailDocAchService: DetailDocAchService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.detailDocAchService.query().subscribe(
      (res: HttpResponse<IDetailDocAch[]>) => {
        this.isLoading = false;
        this.detailDocAches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDetailDocAch): number {
    return item.id!;
  }

  delete(detailDocAch: IDetailDocAch): void {
    const modalRef = this.modalService.open(DetailDocAchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detailDocAch = detailDocAch;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
