import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDocumentVente, DocumentVente } from '../document-vente.model';
import { DocumentVenteService } from '../service/document-vente.service';

@Component({
  selector: 'jhi-document-vente-update',
  templateUrl: './document-vente-update.component.html',
})
export class DocumentVenteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, []],
    dateSaisie: [],
    taxe: [],
    observation: [],
    montantht: [],
    montantttc: [],
  });

  constructor(protected documentVenteService: DocumentVenteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentVente }) => {
      this.updateForm(documentVente);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentVente = this.createFromForm();
    if (documentVente.id !== undefined) {
      this.subscribeToSaveResponse(this.documentVenteService.update(documentVente));
    } else {
      this.subscribeToSaveResponse(this.documentVenteService.create(documentVente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentVente>>): void {
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

  protected updateForm(documentVente: IDocumentVente): void {
    this.editForm.patchValue({
      id: documentVente.id,
      dateSaisie: documentVente.dateSaisie,
      taxe: documentVente.taxe,
      observation: documentVente.observation,
      montantht: documentVente.montantht,
      montantttc: documentVente.montantttc,
    });
  }

  protected createFromForm(): IDocumentVente {
    return {
      ...new DocumentVente(),
      id: this.editForm.get(['id'])!.value,
      dateSaisie: this.editForm.get(['dateSaisie'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      montantht: this.editForm.get(['montantht'])!.value,
      montantttc: this.editForm.get(['montantttc'])!.value,
    };
  }
}
