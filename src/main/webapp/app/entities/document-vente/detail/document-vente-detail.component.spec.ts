import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentVenteDetailComponent } from './document-vente-detail.component';

describe('DocumentVente Management Detail Component', () => {
  let comp: DocumentVenteDetailComponent;
  let fixture: ComponentFixture<DocumentVenteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentVenteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documentVente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentVenteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentVenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentVente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documentVente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
