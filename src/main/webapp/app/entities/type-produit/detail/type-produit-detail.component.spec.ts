import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeProduitDetailComponent } from './type-produit-detail.component';

describe('TypeProduit Management Detail Component', () => {
  let comp: TypeProduitDetailComponent;
  let fixture: ComponentFixture<TypeProduitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeProduitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typeProduit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypeProduitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypeProduitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typeProduit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typeProduit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
