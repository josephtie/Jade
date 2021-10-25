import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDetailDocAch, DetailDocAch } from '../detail-doc-ach.model';
import { DetailDocAchService } from '../service/detail-doc-ach.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';
import { DocumentAchatService } from 'app/entities/document-achat/service/document-achat.service';

@Component({
  selector: 'jhi-detail-doc-ach-update',
  templateUrl: './detail-doc-ach-update.component.html',
})
export class DetailDocAchUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  documentAchatsSharedCollection: IDocumentAchat[] = [];

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
    documentAchat: [],
  });

  constructor(
    protected detailDocAchService: DetailDocAchService,
    protected produitService: ProduitService,
    protected documentAchatService: DocumentAchatService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDocAch }) => {
      this.updateForm(detailDocAch);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailDocAch = this.createFromForm();
    if (detailDocAch.id !== undefined) {
      this.subscribeToSaveResponse(this.detailDocAchService.update(detailDocAch));
    } else {
      this.subscribeToSaveResponse(this.detailDocAchService.create(detailDocAch));
    }
  }

  trackProduitById(index: number, item: IProduit): number {
    return item.id!;
  }

  trackDocumentAchatById(index: number, item: IDocumentAchat): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailDocAch>>): void {
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

  protected updateForm(detailDocAch: IDetailDocAch): void {
    this.editForm.patchValue({
      id: detailDocAch.id,
      prixUnit: detailDocAch.prixUnit,
      prixunitnet: detailDocAch.prixunitnet,
      montligne: detailDocAch.montligne,
      qteUnit: detailDocAch.qteUnit,
      remise: detailDocAch.remise,
      quantitecolis: detailDocAch.quantitecolis,
      designation: detailDocAch.designation,
      produit: detailDocAch.produit,
      documentAchat: detailDocAch.documentAchat,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(
      this.produitsSharedCollection,
      detailDocAch.produit
    );
    this.documentAchatsSharedCollection = this.documentAchatService.addDocumentAchatToCollectionIfMissing(
      this.documentAchatsSharedCollection,
      detailDocAch.documentAchat
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

    this.documentAchatService
      .query()
      .pipe(map((res: HttpResponse<IDocumentAchat[]>) => res.body ?? []))
      .pipe(
        map((documentAchats: IDocumentAchat[]) =>
          this.documentAchatService.addDocumentAchatToCollectionIfMissing(documentAchats, this.editForm.get('documentAchat')!.value)
        )
      )
      .subscribe((documentAchats: IDocumentAchat[]) => (this.documentAchatsSharedCollection = documentAchats));
  }

  protected createFromForm(): IDetailDocAch {
    return {
      ...new DetailDocAch(),
      id: this.editForm.get(['id'])!.value,
      prixUnit: this.editForm.get(['prixUnit'])!.value,
      prixunitnet: this.editForm.get(['prixunitnet'])!.value,
      montligne: this.editForm.get(['montligne'])!.value,
      qteUnit: this.editForm.get(['qteUnit'])!.value,
      remise: this.editForm.get(['remise'])!.value,
      quantitecolis: this.editForm.get(['quantitecolis'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      documentAchat: this.editForm.get(['documentAchat'])!.value,
    };
  }
}
