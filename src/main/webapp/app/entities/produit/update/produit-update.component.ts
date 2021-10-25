import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduit, Produit } from '../produit.model';
import { ProduitService } from '../service/produit.service';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';
import { ITypeProduit } from 'app/entities/type-produit/type-produit.model';
import { TypeProduitService } from 'app/entities/type-produit/service/type-produit.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;

  societesSharedCollection: ISociete[] = [];
  typeProduitsSharedCollection: ITypeProduit[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
    stockinit: [null, [Validators.required]],
    stockApprov: [],
    boissonPrixUnitairenet: [],
    societe: [],
    typeProduit: [],
  });

  constructor(
    protected produitService: ProduitService,
    protected societeService: SocieteService,
    protected typeProduitService: TypeProduitService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  trackSocieteById(index: number, item: ISociete): number {
    return item.id!;
  }

  trackTypeProduitById(index: number, item: ITypeProduit): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  protected updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      libelle: produit.libelle,
      stockinit: produit.stockinit,
      stockApprov: produit.stockApprov,
      boissonPrixUnitairenet: produit.boissonPrixUnitairenet,
      societe: produit.societe,
      typeProduit: produit.typeProduit,
    });

    this.societesSharedCollection = this.societeService.addSocieteToCollectionIfMissing(this.societesSharedCollection, produit.societe);
    this.typeProduitsSharedCollection = this.typeProduitService.addTypeProduitToCollectionIfMissing(
      this.typeProduitsSharedCollection,
      produit.typeProduit
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societeService
      .query()
      .pipe(map((res: HttpResponse<ISociete[]>) => res.body ?? []))
      .pipe(
        map((societes: ISociete[]) => this.societeService.addSocieteToCollectionIfMissing(societes, this.editForm.get('societe')!.value))
      )
      .subscribe((societes: ISociete[]) => (this.societesSharedCollection = societes));

    this.typeProduitService
      .query()
      .pipe(map((res: HttpResponse<ITypeProduit[]>) => res.body ?? []))
      .pipe(
        map((typeProduits: ITypeProduit[]) =>
          this.typeProduitService.addTypeProduitToCollectionIfMissing(typeProduits, this.editForm.get('typeProduit')!.value)
        )
      )
      .subscribe((typeProduits: ITypeProduit[]) => (this.typeProduitsSharedCollection = typeProduits));
  }

  protected createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      stockinit: this.editForm.get(['stockinit'])!.value,
      stockApprov: this.editForm.get(['stockApprov'])!.value,
      boissonPrixUnitairenet: this.editForm.get(['boissonPrixUnitairenet'])!.value,
      societe: this.editForm.get(['societe'])!.value,
      typeProduit: this.editForm.get(['typeProduit'])!.value,
    };
  }
}
