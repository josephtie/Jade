import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentSortie } from '../document-sortie.model';

@Component({
  selector: 'jhi-document-sortie-detail',
  templateUrl: './document-sortie-detail.component.html',
})
export class DocumentSortieDetailComponent implements OnInit {
  documentSortie: IDocumentSortie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentSortie }) => {
      this.documentSortie = documentSortie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
