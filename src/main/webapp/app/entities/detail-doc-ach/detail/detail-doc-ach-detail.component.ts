import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailDocAch } from '../detail-doc-ach.model';

@Component({
  selector: 'jhi-detail-doc-ach-detail',
  templateUrl: './detail-doc-ach-detail.component.html',
})
export class DetailDocAchDetailComponent implements OnInit {
  detailDocAch: IDetailDocAch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDocAch }) => {
      this.detailDocAch = detailDocAch;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
