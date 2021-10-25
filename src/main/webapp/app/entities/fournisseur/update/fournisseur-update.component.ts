import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFournisseur, Fournisseur } from '../fournisseur.model';
import { FournisseurService } from '../service/fournisseur.service';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';

@Component({
  selector: 'jhi-fournisseur-update',
  templateUrl: './fournisseur-update.component.html',
})
export class FournisseurUpdateComponent implements OnInit {
  isSaving = false;

  societesSharedCollection: ISociete[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    codeFournisseur: [null, [Validators.required]],
    raisonFournisseur: [null, [Validators.required]],
    adrfournisseur: [],
    paysFournisseur: [],
    villeFournisseur: [null, [Validators.required]],
    mailFournisseur: [],
    celFournisseur: [null, [Validators.required]],
    telFournisseur: [],
    societe: [],
  });

  constructor(
    protected fournisseurService: FournisseurService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournisseur }) => {
      this.updateForm(fournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fournisseur = this.createFromForm();
    if (fournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.fournisseurService.update(fournisseur));
    } else {
      this.subscribeToSaveResponse(this.fournisseurService.create(fournisseur));
    }
  }

  trackSocieteById(index: number, item: ISociete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseur>>): void {
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

  protected updateForm(fournisseur: IFournisseur): void {
    this.editForm.patchValue({
      id: fournisseur.id,
      codeFournisseur: fournisseur.codeFournisseur,
      raisonFournisseur: fournisseur.raisonFournisseur,
      adrfournisseur: fournisseur.adrfournisseur,
      paysFournisseur: fournisseur.paysFournisseur,
      villeFournisseur: fournisseur.villeFournisseur,
      mailFournisseur: fournisseur.mailFournisseur,
      celFournisseur: fournisseur.celFournisseur,
      telFournisseur: fournisseur.telFournisseur,
      societe: fournisseur.societe,
    });

    this.societesSharedCollection = this.societeService.addSocieteToCollectionIfMissing(this.societesSharedCollection, fournisseur.societe);
  }

  protected loadRelationshipsOptions(): void {
    this.societeService
      .query()
      .pipe(map((res: HttpResponse<ISociete[]>) => res.body ?? []))
      .pipe(
        map((societes: ISociete[]) => this.societeService.addSocieteToCollectionIfMissing(societes, this.editForm.get('societe')!.value))
      )
      .subscribe((societes: ISociete[]) => (this.societesSharedCollection = societes));
  }

  protected createFromForm(): IFournisseur {
    return {
      ...new Fournisseur(),
      id: this.editForm.get(['id'])!.value,
      codeFournisseur: this.editForm.get(['codeFournisseur'])!.value,
      raisonFournisseur: this.editForm.get(['raisonFournisseur'])!.value,
      adrfournisseur: this.editForm.get(['adrfournisseur'])!.value,
      paysFournisseur: this.editForm.get(['paysFournisseur'])!.value,
      villeFournisseur: this.editForm.get(['villeFournisseur'])!.value,
      mailFournisseur: this.editForm.get(['mailFournisseur'])!.value,
      celFournisseur: this.editForm.get(['celFournisseur'])!.value,
      telFournisseur: this.editForm.get(['telFournisseur'])!.value,
      societe: this.editForm.get(['societe'])!.value,
    };
  }
}
