jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetailDocVteService } from '../service/detail-doc-vte.service';
import { IDetailDocVte, DetailDocVte } from '../detail-doc-vte.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IDocumentVente } from 'app/entities/document-vente/document-vente.model';
import { DocumentVenteService } from 'app/entities/document-vente/service/document-vente.service';

import { DetailDocVteUpdateComponent } from './detail-doc-vte-update.component';

describe('DetailDocVte Management Update Component', () => {
  let comp: DetailDocVteUpdateComponent;
  let fixture: ComponentFixture<DetailDocVteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailDocVteService: DetailDocVteService;
  let produitService: ProduitService;
  let documentVenteService: DocumentVenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailDocVteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DetailDocVteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailDocVteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailDocVteService = TestBed.inject(DetailDocVteService);
    produitService = TestBed.inject(ProduitService);
    documentVenteService = TestBed.inject(DocumentVenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const detailDocVte: IDetailDocVte = { id: 456 };
      const produit: IProduit = { id: 70861 };
      detailDocVte.produit = produit;

      const produitCollection: IProduit[] = [{ id: 6837 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentVente query and add missing value', () => {
      const detailDocVte: IDetailDocVte = { id: 456 };
      const documentVente: IDocumentVente = { id: 51912 };
      detailDocVte.documentVente = documentVente;

      const documentVenteCollection: IDocumentVente[] = [{ id: 8807 }];
      jest.spyOn(documentVenteService, 'query').mockReturnValue(of(new HttpResponse({ body: documentVenteCollection })));
      const additionalDocumentVentes = [documentVente];
      const expectedCollection: IDocumentVente[] = [...additionalDocumentVentes, ...documentVenteCollection];
      jest.spyOn(documentVenteService, 'addDocumentVenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      expect(documentVenteService.query).toHaveBeenCalled();
      expect(documentVenteService.addDocumentVenteToCollectionIfMissing).toHaveBeenCalledWith(
        documentVenteCollection,
        ...additionalDocumentVentes
      );
      expect(comp.documentVentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailDocVte: IDetailDocVte = { id: 456 };
      const produit: IProduit = { id: 9778 };
      detailDocVte.produit = produit;
      const documentVente: IDocumentVente = { id: 14468 };
      detailDocVte.documentVente = documentVente;

      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(detailDocVte));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.documentVentesSharedCollection).toContain(documentVente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocVte>>();
      const detailDocVte = { id: 123 };
      jest.spyOn(detailDocVteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDocVte }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailDocVteService.update).toHaveBeenCalledWith(detailDocVte);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocVte>>();
      const detailDocVte = new DetailDocVte();
      jest.spyOn(detailDocVteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDocVte }));
      saveSubject.complete();

      // THEN
      expect(detailDocVteService.create).toHaveBeenCalledWith(detailDocVte);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailDocVte>>();
      const detailDocVte = { id: 123 };
      jest.spyOn(detailDocVteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDocVte });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailDocVteService.update).toHaveBeenCalledWith(detailDocVte);
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

    describe('trackDocumentVenteById', () => {
      it('Should return tracked DocumentVente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentVenteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
