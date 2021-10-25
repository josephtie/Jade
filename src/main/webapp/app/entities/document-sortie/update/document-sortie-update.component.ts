import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDocumentSortie, DocumentSortie } from '../document-sortie.model';
import { DocumentSortieService } from '../service/document-sortie.service';

@Component({
  selector: 'jhi-document-sortie-update',
  templateUrl: './document-sortie-update.component.html',
})
export class DocumentSortieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, []],
    dateSaisie: [],
    taxe: [],
    observation: [],
    montantht: [],
    montantttc: [],
  });

  constructor(
    protected documentSortieService: DocumentSortieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentSortie }) => {
      this.updateForm(documentSortie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentSortie = this.createFromForm();
    if (documentSortie.id !== undefined) {
      this.subscribeToSaveResponse(this.documentSortieService.update(documentSortie));
    } else {
      this.subscribeToSaveResponse(this.documentSortieService.create(documentSortie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentSortie>>): void {
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

  protected updateForm(documentSortie: IDocumentSortie): void {
    this.editForm.patchValue({
      id: documentSortie.id,
      dateSaisie: documentSortie.dateSaisie,
      taxe: documentSortie.taxe,
      observation: documentSortie.observation,
      montantht: documentSortie.montantht,
      montantttc: documentSortie.montantttc,
    });
  }

  protected createFromForm(): IDocumentSortie {
    return {
      ...new DocumentSortie(),
      id: this.editForm.get(['id'])!.value,
      dateSaisie: this.editForm.get(['dateSaisie'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      montantht: this.editForm.get(['montantht'])!.value,
      montantttc: this.editForm.get(['montantttc'])!.value,
    };
  }
}
