import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentSortieDetailComponent } from './document-sortie-detail.component';

describe('DocumentSortie Management Detail Component', () => {
  let comp: DocumentSortieDetailComponent;
  let fixture: ComponentFixture<DocumentSortieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentSortieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documentSortie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentSortieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentSortieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentSortie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documentSortie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
