import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentAchatDetailComponent } from './document-achat-detail.component';

describe('DocumentAchat Management Detail Component', () => {
  let comp: DocumentAchatDetailComponent;
  let fixture: ComponentFixture<DocumentAchatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentAchatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documentAchat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentAchatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentAchatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentAchat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documentAchat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
