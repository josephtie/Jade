jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetailDocAchService } from '../service/detail-doc-ach.service';
import { IDetailDocAch, DetailDocAch } from '../detail-doc-ach.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IDocumentAchat } from 'app/entities/document-achat/document-achat.model';
import { DocumentAchatService } from 'app/entities/document-achat/service/document-achat.service';

import { DetailDocAchUpdateComponent } from './detail-doc-ach-update.component';

describe('DetailDocAch Management Update Component', () => {
  let comp: DetailDocAchUpdateComponent;
  let fixture: ComponentFixture<DetailDocAchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailDocAchService: DetailDocAchService;
  let produitService: ProduitService;
  let documentAchatService: DocumentAchatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailDocAchUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DetailDocAchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailDocAchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailDocAchService = TestBed.inject(DetailDocAchService);
    produitService = TestBed.inject(ProduitService);
    documentAchatService = TestBed.inject(DocumentAchatService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const detailDocAch: IDetailDocAch = { id: 456 };
      const produit: IProduit = { id: 74539 };
      detailDocAch.produit = produit;

      const produitCollection: IProduit[] = [{ id: 70367 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentAchat query and add missing value', () => {
      const detailDocAch: IDetailDocAch = { id: 456 };
      const documentAchat: IDocumentAchat = { id: 69532 };
      detailDocAch.documentAchat = documentAchat;

      const documentAchatCollection: IDocumentAchat[] = [{ id: 8262 }];
      jest.spyOn(documentAchatService, 'query').mockReturnValue(of(new HttpResponse({ body: documentAchatCollection })));
      const additionalDocumentAchats = [documentAchat];
      const expectedCollection: IDocumentAchat[] = [...additionalDocumentAchats, ...documentAchatCollection];
      jest.spyOn(documentAchatService, 'addDocumentAchatToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      expect(documentAchatService.query).toHaveBeenCalled();
      expect(documentAchatService.addDocumentAchatToCollectionIfMissing).toHaveBeenCalledWith(
        documentAchatCollection,
        ...additionalDocumentAchats
      );
      expect(comp.documentAchatsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailDocAch: IDetailDocAch = { id: 456 };
      const produit: IProduit = { id: 94559 };
      detailDocAch.produit = produit;
      const documentAchat: IDocumentAchat = { id: 68039 };
      detailDocAch.documentAchat = documentAchat;

      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(detailDocAch));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.documentAchatsSharedCollection).toContain(documentAchat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocAch>>();
      const detailDocAch = { id: 123 };
      jest.spyOn(detailDocAchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDocAch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailDocAchService.update).toHaveBeenCalledWith(detailDocAch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocAch>>();
      const detailDocAch = new DetailDocAch();
      jest.spyOn(detailDocAchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDocAch }));
      saveSubject.complete();

      // THEN
      expect(detailDocAchService.create).toHaveBeenCalledWith(detailDocAch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocAch>>();
      const detailDocAch = { id: 123 };
      jest.spyOn(detailDocAchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocAch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailDocAchService.update).toHaveBeenCalledWith(detailDocAch);
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
  });
});
