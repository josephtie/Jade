jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetailsStockService } from '../service/details-stock.service';
import { IDetailsStock, DetailsStock } from '../details-stock.model';
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

import { DetailsStockUpdateComponent } from './details-stock-update.component';

describe('DetailsStock Management Update Component', () => {
  let comp: DetailsStockUpdateComponent;
  let fixture: ComponentFixture<DetailsStockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailsStockService: DetailsStockService;
  let produitService: ProduitService;
  let documentAchatService: DocumentAchatService;
  let documentVenteService: DocumentVenteService;
  let documentSortieService: DocumentSortieService;
  let societeService: SocieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailsStockUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DetailsStockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailsStockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailsStockService = TestBed.inject(DetailsStockService);
    produitService = TestBed.inject(ProduitService);
    documentAchatService = TestBed.inject(DocumentAchatService);
    documentVenteService = TestBed.inject(DocumentVenteService);
    documentSortieService = TestBed.inject(DocumentSortieService);
    societeService = TestBed.inject(SocieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const produit: IProduit = { id: 14303 };
      detailsStock.produit = produit;

      const produitCollection: IProduit[] = [{ id: 45477 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentAchat query and add missing value', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const documentAchat: IDocumentAchat = { id: 16388 };
      detailsStock.documentAchat = documentAchat;

      const documentAchatCollection: IDocumentAchat[] = [{ id: 44422 }];
      jest.spyOn(documentAchatService, 'query').mockReturnValue(of(new HttpResponse({ body: documentAchatCollection })));
      const additionalDocumentAchats = [documentAchat];
      const expectedCollection: IDocumentAchat[] = [...additionalDocumentAchats, ...documentAchatCollection];
      jest.spyOn(documentAchatService, 'addDocumentAchatToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(documentAchatService.query).toHaveBeenCalled();
      expect(documentAchatService.addDocumentAchatToCollectionIfMissing).toHaveBeenCalledWith(
        documentAchatCollection,
        ...additionalDocumentAchats
      );
      expect(comp.documentAchatsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentVente query and add missing value', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const documentVente: IDocumentVente = { id: 21734 };
      detailsStock.documentVente = documentVente;

      const documentVenteCollection: IDocumentVente[] = [{ id: 80750 }];
      jest.spyOn(documentVenteService, 'query').mockReturnValue(of(new HttpResponse({ body: documentVenteCollection })));
      const additionalDocumentVentes = [documentVente];
      const expectedCollection: IDocumentVente[] = [...additionalDocumentVentes, ...documentVenteCollection];
      jest.spyOn(documentVenteService, 'addDocumentVenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(documentVenteService.query).toHaveBeenCalled();
      expect(documentVenteService.addDocumentVenteToCollectionIfMissing).toHaveBeenCalledWith(
        documentVenteCollection,
        ...additionalDocumentVentes
      );
      expect(comp.documentVentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentSortie query and add missing value', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const documentSortie: IDocumentSortie = { id: 38305 };
      detailsStock.documentSortie = documentSortie;

      const documentSortieCollection: IDocumentSortie[] = [{ id: 68225 }];
      jest.spyOn(documentSortieService, 'query').mockReturnValue(of(new HttpResponse({ body: documentSortieCollection })));
      const additionalDocumentSorties = [documentSortie];
      const expectedCollection: IDocumentSortie[] = [...additionalDocumentSorties, ...documentSortieCollection];
      jest.spyOn(documentSortieService, 'addDocumentSortieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(documentSortieService.query).toHaveBeenCalled();
      expect(documentSortieService.addDocumentSortieToCollectionIfMissing).toHaveBeenCalledWith(
        documentSortieCollection,
        ...additionalDocumentSorties
      );
      expect(comp.documentSortiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Societe query and add missing value', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const societe: ISociete = { id: 23089 };
      detailsStock.societe = societe;

      const societeCollection: ISociete[] = [{ id: 71102 }];
      jest.spyOn(societeService, 'query').mockReturnValue(of(new HttpResponse({ body: societeCollection })));
      const additionalSocietes = [societe];
      const expectedCollection: ISociete[] = [...additionalSocietes, ...societeCollection];
      jest.spyOn(societeService, 'addSocieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(societeService.query).toHaveBeenCalled();
      expect(societeService.addSocieteToCollectionIfMissing).toHaveBeenCalledWith(societeCollection, ...additionalSocietes);
      expect(comp.societesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailsStock: IDetailsStock = { id: 456 };
      const produit: IProduit = { id: 36928 };
      detailsStock.produit = produit;
      const documentAchat: IDocumentAchat = { id: 55197 };
      detailsStock.documentAchat = documentAchat;
      const documentVente: IDocumentVente = { id: 15784 };
      detailsStock.documentVente = documentVente;
      const documentSortie: IDocumentSortie = { id: 37563 };
      detailsStock.documentSortie = documentSortie;
      const societe: ISociete = { id: 70783 };
      detailsStock.societe = societe;

      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(detailsStock));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.documentAchatsSharedCollection).toContain(documentAchat);
      expect(comp.documentVentesSharedCollection).toContain(documentVente);
      expect(comp.documentSortiesSharedCollection).toContain(documentSortie);
      expect(comp.societesSharedCollection).toContain(societe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailsStock>>();
      const detailsStock = { id: 123 };
      jest.spyOn(detailsStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailsStock }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailsStockService.update).toHaveBeenCalledWith(detailsStock);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailsStock>>();
      const detailsStock = new DetailsStock();
      jest.spyOn(detailsStockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailsStock }));
      saveSubject.complete();

      // THEN
      expect(detailsStockService.create).toHaveBeenCalledWith(detailsStock);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailsStock>>();
      const detailsStock = { id: 123 };
      jest.spyOn(detailsStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailsStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailsStockService.update).toHaveBeenCalledWith(detailsStock);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProduitById', () => {
      it('Should return tracked Produit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProduitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDocumentAchatById', () => {
      it('Should return tracked DocumentAchat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentAchatById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDocumentVenteById', () => {
      it('Should return tracked DocumentVente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentVenteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDocumentSortieById', () => {
      it('Should return tracked DocumentSortie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentSortieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSocieteById', () => {
      it('Should return tracked Societe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSocieteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
