import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDetailDocVte, DetailDocVte } from '../detail-doc-vte.model';
import { DetailDocVteService } from '../service/detail-doc-vte.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IDocumentVente } from 'app/entities/document-vente/document-vente.model';
import { DocumentVenteService } from 'app/entities/document-vente/service/document-vente.service';

@Component({
  selector: 'jhi-detail-doc-vte-update',
  templateUrl: './detail-doc-vte-update.component.html',
})
export class DetailDocVteUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  documentVentesSharedCollection: IDocumentVente[] = [];

  editForm = this.fb.group({
    id: [null, []],
    prixUnit: [null, [Validators.required]],
    prixunitnet: [],
    montligne: [null, [Validators.required]],
    qteUnit: [null, [Validators.required]],
    remise: [],
    quantitecolis: [],
    designation: [],
    produit: [],
    documentVente: [],
  });

  constructor(
    protected detailDocVteService: DetailDocVteService,
    protected produitService: ProduitService,
    protected documentVenteService: DocumentVenteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDocVte }) => {
      this.updateForm(detailDocVte);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailDocVte = this.createFromForm();
    if (detailDocVte.id !== undefined) {
      this.subscribeToSaveResponse(this.detailDocVteService.update(detailDocVte));
    } else {
      this.subscribeToSaveResponse(this.detailDocVteService.create(detailDocVte));
    }
  }

  trackProduitById(index: number, item: IProduit): number {
    return item.id!;
  }

  trackDocumentVenteById(index: number, item: IDocumentVente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailDocVte>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(detailDocVte: IDetailDocVte): void {
    this.editForm.patchValue({
      id: detailDocVte.id,
      prixUnit: detailDocVte.prixUnit,
      prixunitnet: detailDocVte.prixunitnet,
      montligne: detailDocVte.montligne,
      qteUnit: detailDocVte.qteUnit,
      remise: detailDocVte.remise,
      quantitecolis: detailDocVte.quantitecolis,
      designation: detailDocVte.designation,
      produit: detailDocVte.produit,
      documentVente: detailDocVte.documentVente,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(
      this.produitsSharedCollection,
      detailDocVte.produit
    );
    this.documentVentesSharedCollection = this.documentVenteService.addDocumentVenteToCollectionIfMissing(
      this.documentVentesSharedCollection,
      detailDocVte.documentVente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing(produits, this.editForm.get('produit')!.value))
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));

    this.documentVenteService
      .query()
      .pipe(map((res: HttpResponse<IDocumentVente[]>) => res.body ?? []))
      .pipe(
        map((documentVentes: IDocumentVente[]) =>
          this.documentVenteService.addDocumentVenteToCollectionIfMissing(documentVentes, this.editForm.get('documentVente')!.value)
        )
      )
      .subscribe((documentVentes: IDocumentVente[]) => (this.documentVentesSharedCollection = documentVentes));
  }

  protected createFromForm(): IDetailDocVte {
    return {
      ...new DetailDocVte(),
      id: this.editForm.get(['id'])!.value,
      prixUnit: this.editForm.get(['prixUnit'])!.value,
      prixunitnet: this.editForm.get(['prixunitnet'])!.value,
      montligne: this.editForm.get(['montligne'])!.value,
      qteUnit: this.editForm.get(['qteUnit'])!.value,
      remise: this.editForm.get(['remise'])!.value,
      quantitecolis: this.editForm.get(['quantitecolis'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      documentVente: this.editForm.get(['documentVente'])!.value,
    };
  }
}
