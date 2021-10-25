import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISociete, Societe } from '../societe.model';
import { SocieteService } from '../service/societe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-societe-update',
  templateUrl: './societe-update.component.html',
})
export class SocieteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    raisonsoc: [null, [Validators.required]],
    sigle: [null, [Validators.required]],
    activitepp: [null, [Validators.required]],
    adressgeo: [],
    formjuri: [],
    telephone: [null, [Validators.required]],
    bp: [],
    registreCce: [],
    pays: [null, [Validators.required]],
    ville: [null, [Validators.required]],
    commune: [null, [Validators.required]],
    email: [null, [Validators.required]],
    actif: [],
    fileData: [null, [Validators.required]],
    fileDataContentType: [],
    urlLogo: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected societeService: SocieteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societe }) => {
      this.updateForm(societe);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('jadeApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societe = this.createFromForm();
    if (societe.id !== undefined) {
      this.subscribeToSaveResponse(this.societeService.update(societe));
    } else {
      this.subscribeToSaveResponse(this.societeService.create(societe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISociete>>): void {
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

  protected updateForm(societe: ISociete): void {
    this.editForm.patchValue({
      id: societe.id,
      raisonsoc: societe.raisonsoc,
      sigle: societe.sigle,
      activitepp: societe.activitepp,
      adressgeo: societe.adressgeo,
      formjuri: societe.formjuri,
      telephone: societe.telephone,
      bp: societe.bp,
      registreCce: societe.registreCce,
      pays: societe.pays,
      ville: societe.ville,
      commune: societe.commune,
      email: societe.email,
      actif: societe.actif,
      fileData: societe.fileData,
      fileDataContentType: societe.fileDataContentType,
      urlLogo: societe.urlLogo,
    });
  }

  protected createFromForm(): ISociete {
    return {
      ...new Societe(),
      id: this.editForm.get(['id'])!.value,
      raisonsoc: this.editForm.get(['raisonsoc'])!.value,
      sigle: this.editForm.get(['sigle'])!.value,
      activitepp: this.editForm.get(['activitepp'])!.value,
      adressgeo: this.editForm.get(['adressgeo'])!.value,
      formjuri: this.editForm.get(['formjuri'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      bp: this.editForm.get(['bp'])!.value,
      registreCce: this.editForm.get(['registreCce'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      email: this.editForm.get(['email'])!.value,
      actif: this.editForm.get(['actif'])!.value,
      fileDataContentType: this.editForm.get(['fileDataContentType'])!.value,
      fileData: this.editForm.get(['fileData'])!.value,
      urlLogo: this.editForm.get(['urlLogo'])!.value,
    };
  }
}
