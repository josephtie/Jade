import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDetailsStock, DetailsStock } from '../details-stock.model';
import { DetailsStockService } from '../service/details-stock.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';
import { DocumentAchatService } from 'app/entities/document-achat/service/document-achat.service';
import { IDocumentVente } from 'app/entities/document-vente/document-vente.model';
import { DocumentVenteService } from 'app/entities/document-vente/service/document-vente.service';
import { IDocumentSortie } from 'app/entities/document-sortie/document-sortie.model';
import { DocumentSortieService } from 'app/entities/document-sortie/service/document-sortie.service';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';

@Component({
  selector: 'jhi-details-stock-update',
  templateUrl: './details-stock-update.component.html',
})
export class DetailsStockUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  documentAchatsSharedCollection: IDocumentAchat[] = [];
  documentVentesSharedCollection: IDocumentVente[] = [];
  documentSortiesSharedCollection: IDocumentSortie[] = [];
  societesSharedCollection: ISociete[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    stkQTEentrant: [null, [Validators.required]],
    stkQTEinitial: [null, [Validators.required]],
    stkQTEreel: [null, [Validators.required]],
    idCommande: [],
    idVente: [],
    idSortie: [],
    dateSaisie: [],
    montunitaireOP: [],
    produit: [],
    documentAchat: [],
    documentVente: [],
    documentSortie: [],
    societe: [],
  });

  constructor(
    protected detailsStockService: DetailsStockService,
    protected produitService: ProduitService,
    protected documentAchatService: DocumentAchatService,
    protected documentVenteService: DocumentVenteService,
    protected documentSortieService: DocumentSortieService,
    protected societeService: SocieteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailsStock }) => {
      this.updateForm(detailsStock);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailsStock = this.createFromForm();
    if (detailsStock.id !== undefined) {
      this.subscribeToSaveResponse(this.detailsStockService.update(detailsStock));
    } else {
      this.subscribeToSaveResponse(this.detailsStockService.create(detailsStock));
    }
  }

  trackProduitById(index: number, item: IProduit): number {
    return item.id!;
  }

  trackDocumentAchatById(index: number, item: IDocumentAchat): number {
    return item.id!;
  }

  trackDocumentVenteById(index: number, item: IDocumentVente): number {
    return item.id!;
  }

  trackDocumentSortieById(index: number, item: IDocumentSortie): number {
    return item.id!;
  }

  trackSocieteById(index: number, item: ISociete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailsStock>>): void {
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

  protected updateForm(detailsStock: IDetailsStock): void {
    this.editForm.patchValue({
      id: detailsStock.id,
      stkQTEentrant: detailsStock.stkQTEentrant,
      stkQTEinitial: detailsStock.stkQTEinitial,
      stkQTEreel: detailsStock.stkQTEreel,
      idCommande: detailsStock.idCommande,
      idVente: detailsStock.idVente,
      idSortie: detailsStock.idSortie,
      dateSaisie: detailsStock.dateSaisie,
      montunitaireOP: detailsStock.montunitaireOP,
      produit: detailsStock.produit,
      documentAchat: detailsStock.documentAchat,
      documentVente: detailsStock.documentVente,
      documentSortie: detailsStock.documentSortie,
      societe: detailsStock.societe,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(
      this.produitsSharedCollection,
      detailsStock.produit
    );
    this.documentAchatsSharedCollection = this.documentAchatService.addDocumentAchatToCollectionIfMissing(
      this.documentAchatsSharedCollection,
      detailsStock.documentAchat
    );
    this.documentVentesSharedCollection = this.documentVenteService.addDocumentVenteToCollectionIfMissing(
      this.documentVentesSharedCollection,
      detailsStock.documentVente
    );
    this.documentSortiesSharedCollection = this.documentSortieService.addDocumentSortieToCollectionIfMissing(
      this.documentSortiesSharedCollection,
      detailsStock.documentSortie
    );
    this.societesSharedCollection = this.societeService.addSocieteToCollectionIfMissing(
      this.societesSharedCollection,
      detailsStock.societe
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

    this.documentVenteService
      .query()
      .pipe(map((res: HttpResponse<IDocumentVente[]>) => res.body ?? []))
      .pipe(
        map((documentVentes: IDocumentVente[]) =>
          this.documentVenteService.addDocumentVenteToCollectionIfMissing(documentVentes, this.editForm.get('documentVente')!.value)
        )
      )
      .subscribe((documentVentes: IDocumentVente[]) => (this.documentVentesSharedCollection = documentVentes));

    this.documentSortieService
      .query()
      .pipe(map((res: HttpResponse<IDocumentSortie[]>) => res.body ?? []))
      .pipe(
        map((documentSorties: IDocumentSortie[]) =>
          this.documentSortieService.addDocumentSortieToCollectionIfMissing(documentSorties, this.editForm.get('documentSortie')!.value)
        )
      )
      .subscribe((documentSorties: IDocumentSortie[]) => (this.documentSortiesSharedCollection = documentSorties));

    this.societeService
      .query()
      .pipe(map((res: HttpResponse<ISociete[]>) => res.body ?? []))
      .pipe(
        map((societes: ISociete[]) => this.societeService.addSocieteToCollectionIfMissing(societes, this.editForm.get('societe')!.value))
      )
      .subscribe((societes: ISociete[]) => (this.societesSharedCollection = societes));
  }

  protected createFromForm(): IDetailsStock {
    return {
      ...new DetailsStock(),
      id: this.editForm.get(['id'])!.value,
      stkQTEentrant: this.editForm.get(['stkQTEentrant'])!.value,
      stkQTEinitial: this.editForm.get(['stkQTEinitial'])!.value,
      stkQTEreel: this.editForm.get(['stkQTEreel'])!.value,
      idCommande: this.editForm.get(['idCommande'])!.value,
      idVente: this.editForm.get(['idVente'])!.value,
      idSortie: this.editForm.get(['idSortie'])!.value,
      dateSaisie: this.editForm.get(['dateSaisie'])!.value,
      montunitaireOP: this.editForm.get(['montunitaireOP'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      documentAchat: this.editForm.get(['documentAchat'])!.value,
      documentVente: this.editForm.get(['documentVente'])!.value,
      documentSortie: this.editForm.get(['documentSortie'])!.value,
      societe: this.editForm.get(['societe'])!.value,
    };
  }
}
