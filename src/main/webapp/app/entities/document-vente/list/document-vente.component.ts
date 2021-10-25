import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentVente } from '../document-vente.model';
import { DocumentVenteService } from '../service/document-vente.service';
import { DocumentVenteDeleteDialogComponent } from '../delete/document-vente-delete-dialog.component';

@Component({
  selector: 'jhi-document-vente',
  templateUrl: './document-vente.component.html',
})
export class DocumentVenteComponent implements OnInit {
  documentVentes?: IDocumentVente[];
  isLoading = false;

  constructor(protected documentVenteService: DocumentVenteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.documentVenteService.query().subscribe(
      (res: HttpResponse<IDocumentVente[]>) => {
        this.isLoading = false;
        this.documentVentes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDocumentVente): number {
    return item.id!;
  }

  delete(documentVente: IDocumentVente): void {
    const modalRef = this.modalService.open(DocumentVenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentVente = documentVente;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
