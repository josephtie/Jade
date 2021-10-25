import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SocieteService } from '../service/societe.service';

import { SocieteComponent } from './societe.component';

describe('Societe Management Component', () => {
  let comp: SocieteComponent;
  let fixture: ComponentFixture<SocieteComponent>;
  let service: SocieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SocieteComponent],
    })
      .overrideTemplate(SocieteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocieteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SocieteService);

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
    expect(comp.societes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
