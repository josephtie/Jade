import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailsStock } from '../details-stock.model';
import { DetailsStockService } from '../service/details-stock.service';
import { DetailsStockDeleteDialogComponent } from '../delete/details-stock-delete-dialog.component';

@Component({
  selector: 'jhi-details-stock',
  templateUrl: './details-stock.component.html',
})
export class DetailsStockComponent implements OnInit {
  detailsStocks?: IDetailsStock[];
  isLoading = false;

  constructor(protected detailsStockService: DetailsStockService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.detailsStockService.query().subscribe(
      (res: HttpResponse<IDetailsStock[]>) => {
        this.isLoading = false;
        this.detailsStocks = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDetailsStock): number {
    return item.id!;
  }

  delete(detailsStock: IDetailsStock): void {
    const modalRef = this.modalService.open(DetailsStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detailsStock = detailsStock;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
