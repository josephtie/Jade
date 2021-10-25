jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DocumentSortieService } from '../service/document-sortie.service';
import { IDocumentSortie, DocumentSortie } from '../document-sortie.model';

import { DocumentSortieUpdateComponent } from './document-sortie-update.component';

describe('DocumentSortie Management Update Component', () => {
  let comp: DocumentSortieUpdateComponent;
  let fixture: ComponentFixture<DocumentSortieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentSortieService: DocumentSortieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentSortieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DocumentSortieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentSortieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentSortieService = TestBed.inject(DocumentSortieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const documentSortie: IDocumentSortie = { id: 456 };

      activatedRoute.data = of({ documentSortie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(documentSortie));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentSortie>>();
      const documentSortie = { id: 123 };
      jest.spyOn(documentSortieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentSortie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentSortie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentSortieService.update).toHaveBeenCalledWith(documentSortie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentSortie>>();
      const documentSortie = new DocumentSortie();
      jest.spyOn(documentSortieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentSortie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentSortie }));
      saveSubject.complete();

      // THEN
      expect(documentSortieService.create).toHaveBeenCalledWith(documentSortie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumentSortie>>();
      const documentSortie = { id: 123 };
      jest.spyOn(documentSortieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentSortie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentSortieService.update).toHaveBeenCalledWith(documentSortie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
