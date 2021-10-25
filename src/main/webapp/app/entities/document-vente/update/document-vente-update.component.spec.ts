jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DocumentVenteService } from '../service/document-vente.service';
import { IDocumentVente, DocumentVente } from '../document-vente.model';

import { DocumentVenteUpdateComponent } from './document-vente-update.component';

describe('DocumentVente Management Update Component', () => {
  let comp: DocumentVenteUpdateComponent;
  let fixture: ComponentFixture<DocumentVenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentVenteService: DocumentVenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentVenteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DocumentVenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentVenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentVenteService = TestBed.inject(DocumentVenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const documentVente: IDocumentVente = { id: 456 };

      activatedRoute.data = of({ documentVente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(documentVente));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentVente>>();
      const documentVente = { id: 123 };
      jest.spyOn(documentVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentVente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentVenteService.update).toHaveBeenCalledWith(documentVente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentVente>>();
      const documentVente = new DocumentVente();
      jest.spyOn(documentVenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentVente }));
      saveSubject.complete();

      // THEN
      expect(documentVenteService.create).toHaveBeenCalledWith(documentVente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentVente>>();
      const documentVente = { id: 123 };
      jest.spyOn(documentVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentVenteService.update).toHaveBeenCalledWith(documentVente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
