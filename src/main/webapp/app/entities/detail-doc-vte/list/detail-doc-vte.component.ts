import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailDocVte } from '../detail-doc-vte.model';
import { DetailDocVteService } from '../service/detail-doc-vte.service';
import { DetailDocVteDeleteDialogComponent } from '../delete/detail-doc-vte-delete-dialog.component';

@Component({
  selector: 'jhi-detail-doc-vte',
  templateUrl: './detail-doc-vte.component.html',
})
export class DetailDocVteComponent implements OnInit {
  detailDocVtes?: IDetailDocVte[];
  isLoading = false;

  constructor(protected detailDocVteService: DetailDocVteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.detailDocVteService.query().subscribe(
      (res: HttpResponse<IDetailDocVte[]>) => {
        this.isLoading = false;
        this.detailDocVtes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDetailDocVte): number {
    return item.id!;
  }

  delete(detailDocVte: IDetailDocVte): void {
    const modalRef = this.modalService.open(DetailDocVteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detailDocVte = detailDocVte;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
