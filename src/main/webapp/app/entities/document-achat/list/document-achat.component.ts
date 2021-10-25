import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentAchat } from '../document-achat.model';
import { DocumentAchatService } from '../service/document-achat.service';
import { DocumentAchatDeleteDialogComponent } from '../delete/document-achat-delete-dialog.component';

@Component({
  selector: 'jhi-document-achat',
  templateUrl: './document-achat.component.html',
})
export class DocumentAchatComponent implements OnInit {
  documentAchats?: IDocumentAchat[];
  isLoading = false;

  constructor(protected documentAchatService: DocumentAchatService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.documentAchatService.query().subscribe(
      (res: HttpResponse<IDocumentAchat[]>) => {
        this.isLoading = false;
        this.documentAchats = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDocumentAchat): number {
    return item.id!;
  }

  delete(documentAchat: IDocumentAchat): void {
    const modalRef = this.modalService.open(DocumentAchatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentAchat = documentAchat;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
