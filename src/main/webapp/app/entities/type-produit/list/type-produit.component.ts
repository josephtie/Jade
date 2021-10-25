import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeProduit } from '../type-produit.model';
import { TypeProduitService } from '../service/type-produit.service';
import { TypeProduitDeleteDialogComponent } from '../delete/type-produit-delete-dialog.component';

@Component({
  selector: 'jhi-type-produit',
  templateUrl: './type-produit.component.html',
})
export class TypeProduitComponent implements OnInit {
  typeProduits?: ITypeProduit[];
  isLoading = false;

  constructor(protected typeProduitService: TypeProduitService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.typeProduitService.query().subscribe(
      (res: HttpResponse<ITypeProduit[]>) => {
        this.isLoading = false;
        this.typeProduits = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITypeProduit): number {
    return item.id!;
  }

  delete(typeProduit: ITypeProduit): void {
    const modalRef = this.modalService.open(TypeProduitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeProduit = typeProduit;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
