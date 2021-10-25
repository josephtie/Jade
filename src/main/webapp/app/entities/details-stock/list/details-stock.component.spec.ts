import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DetailsStockService } from '../service/details-stock.service';

import { DetailsStockComponent } from './details-stock.component';

describe('DetailsStock Management Component', () => {
  let comp: DetailsStockComponent;
  let fixture: ComponentFixture<DetailsStockComponent>;
  let service: DetailsStockService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailsStockComponent],
    })
      .overrideTemplate(DetailsStockComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailsStockComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetailsStockService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.detailsStocks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
