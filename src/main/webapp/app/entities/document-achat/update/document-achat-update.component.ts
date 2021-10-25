import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDocumentAchat, DocumentAchat } from '../document-achat.model';
import { DocumentAchatService } from '../service/document-achat.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';

@Component({
  selector: 'jhi-document-achat-update',
  templateUrl: './document-achat-update.component.html',
})
export class DocumentAchatUpdateComponent implements OnInit {
  isSaving = false;

  fournisseursCollection: IFournisseur[] = [];
  societesSharedCollection: ISociete[] = [];

  editForm = this.fb.group({
    id: [null, []],
    dateSaisie: [null, [Validators.required]],
    taxe: [null, [Validators.required]],
    observation: [],
    montantht: [null, [Validators.required]],
    montantttc: [null, [Validators.required]],
    fournisseur: [],
    societe: [],
  });

  constructor(
    protected documentAchatService: DocumentAchatService,
    protected fournisseurService: FournisseurService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentAchat }) => {
      this.updateForm(documentAchat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentAchat = this.createFromForm();
    if (documentAchat.id !== undefined) {
      this.subscribeToSaveResponse(this.documentAchatService.update(documentAchat));
    } else {
      this.subscribeToSaveResponse(this.documentAchatService.create(documentAchat));
    }
  }

  trackFournisseurById(index: number, item: IFournisseur): number {
    return item.id!;
  }

  trackSocieteById(index: number, item: ISociete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentAchat>>): void {
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

  protected updateForm(documentAchat: IDocumentAchat): void {
    this.editForm.patchValue({
      id: documentAchat.id,
      dateSaisie: documentAchat.dateSaisie,
      taxe: documentAchat.taxe,
      observation: documentAchat.observation,
      montantht: documentAchat.montantht,
      montantttc: documentAchat.montantttc,
      fournisseur: documentAchat.fournisseur,
      societe: documentAchat.societe,
    });

    this.fournisseursCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursCollection,
      documentAchat.fournisseur
    );
    this.societesSharedCollection = this.societeService.addSocieteToCollectionIfMissing(
      this.societesSharedCollection,
      documentAchat.societe
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fournisseurService
      .query({ filter: 'documentachat-is-null' })
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursCollection = fournisseurs));

    this.societeService
      .query()
      .pipe(map((res: HttpResponse<ISociete[]>) => res.body ?? []))
      .pipe(
        map((societes: ISociete[]) => this.societeService.addSocieteToCollectionIfMissing(societes, this.editForm.get('societe')!.value))
      )
      .subscribe((societes: ISociete[]) => (this.societesSharedCollection = societes));
  }

  protected createFromForm(): IDocumentAchat {
    return {
      ...new DocumentAchat(),
      id: this.editForm.get(['id'])!.value,
      dateSaisie: this.editForm.get(['dateSaisie'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      montantht: this.editForm.get(['montantht'])!.value,
      montantttc: this.editForm.get(['montantttc'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
      societe: this.editForm.get(['societe'])!.value,
    };
  }
}
