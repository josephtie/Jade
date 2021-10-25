import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailDocVte } from '../detail-doc-vte.model';

@Component({
  selector: 'jhi-detail-doc-vte-detail',
  templateUrl: './detail-doc-vte-detail.component.html',
})
export class DetailDocVteDetailComponent implements OnInit {
  detailDocVte: IDetailDocVte | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDocVte }) => {
      this.detailDocVte = detailDocVte;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
