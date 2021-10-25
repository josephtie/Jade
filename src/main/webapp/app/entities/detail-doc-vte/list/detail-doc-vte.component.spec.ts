import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DetailDocVteService } from '../service/detail-doc-vte.service';

import { DetailDocVteComponent } from './detail-doc-vte.component';

describe('DetailDocVte Management Component', () => {
  let comp: DetailDocVteComponent;
  let fixture: ComponentFixture<DetailDocVteComponent>;
  let service: DetailDocVteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailDocVteComponent],
    })
      .overrideTemplate(DetailDocVteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailDocVteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetailDocVteService);

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
    expect(comp.detailDocVtes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
