jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProduitService } from '../service/produit.service';
import { IProduit, Produit } from '../produit.model';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';
import { ITypeProduit } from 'app/entities/type-produit/type-produit.model';
import { TypeProduitService } from 'app/entities/type-produit/service/type-produit.service';

import { ProduitUpdateComponent } from './produit-update.component';

describe('Produit Management Update Component', () => {
  let comp: ProduitUpdateComponent;
  let fixture: ComponentFixture<ProduitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let produitService: ProduitService;
  let societeService: SocieteService;
  let typeProduitService: TypeProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProduitUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ProduitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProduitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    produitService = TestBed.inject(ProduitService);
    societeService = TestBed.inject(SocieteService);
    typeProduitService = TestBed.inject(TypeProduitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Societe query and add missing value', () => {
      const produit: IProduit = { id: 456 };
      const societe: ISociete = { id: 9210 };
      produit.societe = societe;

      const societeCollection: ISociete[] = [{ id: 61914 }];
      jest.spyOn(societeService, 'query').mockReturnValue(of(new HttpResponse({ body: societeCollection })));
      const additionalSocietes = [societe];
      const expectedCollection: ISociete[] = [...additionalSocietes, ...societeCollection];
      jest.spyOn(societeService, 'addSocieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(societeService.query).toHaveBeenCalled();
      expect(societeService.addSocieteToCollectionIfMissing).toHaveBeenCalledWith(societeCollection, ...additionalSocietes);
      expect(comp.societesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeProduit query and add missing value', () => {
      const produit: IProduit = { id: 456 };
      const typeProduit: ITypeProduit = { id: 4503 };
      produit.typeProduit = typeProduit;

      const typeProduitCollection: ITypeProduit[] = [{ id: 64248 }];
      jest.spyOn(typeProduitService, 'query').mockReturnValue(of(new HttpResponse({ body: typeProduitCollection })));
      const additionalTypeProduits = [typeProduit];
      const expectedCollection: ITypeProduit[] = [...additionalTypeProduits, ...typeProduitCollection];
      jest.spyOn(typeProduitService, 'addTypeProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(typeProduitService.query).toHaveBeenCalled();
      expect(typeProduitService.addTypeProduitToCollectionIfMissing).toHaveBeenCalledWith(typeProduitCollection, ...additionalTypeProduits);
      expect(comp.typeProduitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const produit: IProduit = { id: 456 };
      const societe: ISociete = { id: 22839 };
      produit.societe = societe;
      const typeProduit: ITypeProduit = { id: 33686 };
      produit.typeProduit = typeProduit;

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(produit));
      expect(comp.societesSharedCollection).toContain(societe);
      expect(comp.typeProduitsSharedCollection).toContain(typeProduit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = { id: 123 };
      jest.spyOn(produitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produit }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(produitService.update).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = new Produit();
      jest.spyOn(produitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produit }));
      saveSubject.complete();

      // THEN
      expect(produitService.create).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = { id: 123 };
      jest.spyOn(produitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(produitService.update).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSocieteById', () => {
      it('Should return tracked Societe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSocieteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTypeProduitById', () => {
      it('Should return tracked TypeProduit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeProduitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
