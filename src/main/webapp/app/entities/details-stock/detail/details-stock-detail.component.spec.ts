import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetailsStockDetailComponent } from './details-stock-detail.component';

describe('DetailsStock Management Detail Component', () => {
  let comp: DetailsStockDetailComponent;
  let fixture: ComponentFixture<DetailsStockDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsStockDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detailsStock: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetailsStockDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetailsStockDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detailsStock on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detailsStock).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
