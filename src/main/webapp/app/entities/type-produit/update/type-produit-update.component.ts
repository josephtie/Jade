import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITypeProduit, TypeProduit } from '../type-produit.model';
import { TypeProduitService } from '../service/type-produit.service';

@Component({
  selector: 'jhi-type-produit-update',
  templateUrl: './type-produit-update.component.html',
})
export class TypeProduitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
    description: [],
  });

  constructor(protected typeProduitService: TypeProduitService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeProduit }) => {
      this.updateForm(typeProduit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeProduit = this.createFromForm();
    if (typeProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.typeProduitService.update(typeProduit));
    } else {
      this.subscribeToSaveResponse(this.typeProduitService.create(typeProduit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeProduit>>): void {
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

  protected updateForm(typeProduit: ITypeProduit): void {
    this.editForm.patchValue({
      id: typeProduit.id,
      libelle: typeProduit.libelle,
      description: typeProduit.description,
    });
  }

  protected createFromForm(): ITypeProduit {
    return {
      ...new TypeProduit(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
