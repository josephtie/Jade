import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailsStock } from '../details-stock.model';

@Component({
  selector: 'jhi-details-stock-detail',
  templateUrl: './details-stock-detail.component.html',
})
export class DetailsStockDetailComponent implements OnInit {
  detailsStock: IDetailsStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailsStock }) => {
      this.detailsStock = detailsStock;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
