jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeProduitService } from '../service/type-produit.service';
import { ITypeProduit, TypeProduit } from '../type-produit.model';

import { TypeProduitUpdateComponent } from './type-produit-update.component';

describe('TypeProduit Management Update Component', () => {
  let comp: TypeProduitUpdateComponent;
  let fixture: ComponentFixture<TypeProduitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeProduitService: TypeProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TypeProduitUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TypeProduitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeProduitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeProduitService = TestBed.inject(TypeProduitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typeProduit: ITypeProduit = { id: 456 };

      activatedRoute.data = of({ typeProduit });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(typeProduit));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeProduit>>();
      const typeProduit = { id: 123 };
      jest.spyOn(typeProduitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeProduit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeProduit }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeProduitService.update).toHaveBeenCalledWith(typeProduit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeProduit>>();
      const typeProduit = new TypeProduit();
      jest.spyOn(typeProduitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeProduit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeProduit }));
      saveSubject.complete();

      // THEN
      expect(typeProduitService.create).toHaveBeenCalledWith(typeProduit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeProduit>>();
      const typeProduit = { id: 123 };
      jest.spyOn(typeProduitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeProduit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeProduitService.update).toHaveBeenCalledWith(typeProduit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
