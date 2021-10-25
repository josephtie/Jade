import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentVente } from '../document-vente.model';

@Component({
  selector: 'jhi-document-vente-detail',
  templateUrl: './document-vente-detail.component.html',
})
export class DocumentVenteDetailComponent implements OnInit {
  documentVente: IDocumentVente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentVente }) => {
      this.documentVente = documentVente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
