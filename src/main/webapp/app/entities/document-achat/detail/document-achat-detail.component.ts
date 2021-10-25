import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentAchat } from '../document-achat.model';

@Component({
  selector: 'jhi-document-achat-detail',
  templateUrl: './document-achat-detail.component.html',
})
export class DocumentAchatDetailComponent implements OnInit {
  documentAchat: IDocumentAchat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentAchat }) => {
      this.documentAchat = documentAchat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
