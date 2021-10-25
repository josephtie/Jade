import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentSortie } from '../document-sortie.model';
import { DocumentSortieService } from '../service/document-sortie.service';
import { DocumentSortieDeleteDialogComponent } from '../delete/document-sortie-delete-dialog.component';

@Component({
  selector: 'jhi-document-sortie',
  templateUrl: './document-sortie.component.html',
})
export class DocumentSortieComponent implements OnInit {
  documentSorties?: IDocumentSortie[];
  isLoading = false;

  constructor(protected documentSortieService: DocumentSortieService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.documentSortieService.query().subscribe(
      (res: HttpResponse<IDocumentSortie[]>) => {
        this.isLoading = false;
        this.documentSorties = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDocumentSortie): number {
    return item.id!;
  }

  delete(documentSortie: IDocumentSortie): void {
    const modalRef = this.modalService.open(DocumentSortieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentSortie = documentSortie;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
