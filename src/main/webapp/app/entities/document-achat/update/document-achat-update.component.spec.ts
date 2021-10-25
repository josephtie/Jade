jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DocumentAchatService } from '../service/document-achat.service';
import { IDocumentAchat, DocumentAchat } from '../document-achat.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';

import { DocumentAchatUpdateComponent } from './document-achat-update.component';

describe('DocumentAchat Management Update Component', () => {
  let comp: DocumentAchatUpdateComponent;
  let fixture: ComponentFixture<DocumentAchatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentAchatService: DocumentAchatService;
  let fournisseurService: FournisseurService;
  let societeService: SocieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentAchatUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DocumentAchatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentAchatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentAchatService = TestBed.inject(DocumentAchatService);
    fournisseurService = TestBed.inject(FournisseurService);
    societeService = TestBed.inject(SocieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call fournisseur query and add missing value', () => {
      const documentAchat: IDocumentAchat = { id: 456 };
      const fournisseur: IFournisseur = { id: 9432 };
      documentAchat.fournisseur = fournisseur;

      const fournisseurCollection: IFournisseur[] = [{ id: 73660 }];
      jest.spyOn(fournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: fournisseurCollection })));
      const expectedCollection: IFournisseur[] = [fournisseur, ...fournisseurCollection];
      jest.spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      expect(fournisseurService.query).toHaveBeenCalled();
      expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(fournisseurCollection, fournisseur);
      expect(comp.fournisseursCollection).toEqual(expectedCollection);
    });

    it('Should call Societe query and add missing value', () => {
      const documentAchat: IDocumentAchat = { id: 456 };
      const societe: ISociete = { id: 61504 };
      documentAchat.societe = societe;

      const societeCollection: ISociete[] = [{ id: 71655 }];
      jest.spyOn(societeService, 'query').mockReturnValue(of(new HttpResponse({ body: societeCollection })));
      const additionalSocietes = [societe];
      const expectedCollection: ISociete[] = [...additionalSocietes, ...societeCollection];
      jest.spyOn(societeService, 'addSocieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      expect(societeService.query).toHaveBeenCalled();
      expect(societeService.addSocieteToCollectionIfMissing).toHaveBeenCalledWith(societeCollection, ...additionalSocietes);
      expect(comp.societesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documentAchat: IDocumentAchat = { id: 456 };
      const fournisseur: IFournisseur = { id: 3067 };
      documentAchat.fournisseur = fournisseur;
      const societe: ISociete = { id: 92406 };
      documentAchat.societe = societe;

      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(documentAchat));
      expect(comp.fournisseursCollection).toContain(fournisseur);
      expect(comp.societesSharedCollection).toContain(societe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentAchat>>();
      const documentAchat = { id: 123 };
      jest.spyOn(documentAchatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentAchat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentAchatService.update).toHaveBeenCalledWith(documentAchat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentAchat>>();
      const documentAchat = new DocumentAchat();
      jest.spyOn(documentAchatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentAchat }));
      saveSubject.complete();

      // THEN
      expect(documentAchatService.create).toHaveBeenCalledWith(documentAchat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentAchat>>();
      const documentAchat = { id: 123 };
      jest.spyOn(documentAchatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentAchatService.update).toHaveBeenCalledWith(documentAchat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFournisseurById', () => {
      it('Should return tracked Fournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFournisseurById(0, entity);
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
