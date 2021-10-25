import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DetailDocAchService } from '../service/detail-doc-ach.service';

import { DetailDocAchComponent } from './detail-doc-ach.component';

describe('DetailDocAch Management Component', () => {
  let comp: DetailDocAchComponent;
  let fixture: ComponentFixture<DetailDocAchComponent>;
  let service: DetailDocAchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailDocAchComponent],
    })
      .overrideTemplate(DetailDocAchComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailDocAchComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetailDocAchService);

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
    expect(comp.detailDocAches?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
