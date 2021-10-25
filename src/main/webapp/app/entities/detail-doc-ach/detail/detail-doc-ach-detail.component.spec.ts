import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetailDocAchDetailComponent } from './detail-doc-ach-detail.component';

describe('DetailDocAch Management Detail Component', () => {
  let comp: DetailDocAchDetailComponent;
  let fixture: ComponentFixture<DetailDocAchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailDocAchDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detailDocAch: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetailDocAchDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetailDocAchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detailDocAch on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detailDocAch).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
