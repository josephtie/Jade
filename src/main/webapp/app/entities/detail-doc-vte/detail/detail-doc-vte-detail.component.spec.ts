import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetailDocVteDetailComponent } from './detail-doc-vte-detail.component';

describe('DetailDocVte Management Detail Component', () => {
  let comp: DetailDocVteDetailComponent;
  let fixture: ComponentFixture<DetailDocVteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailDocVteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detailDocVte: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetailDocVteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetailDocVteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detailDocVte on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detailDocVte).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
