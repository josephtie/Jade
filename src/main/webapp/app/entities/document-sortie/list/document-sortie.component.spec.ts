import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DocumentSortieService } from '../service/document-sortie.service';

import { DocumentSortieComponent } from './document-sortie.component';

describe('DocumentSortie Management Component', () => {
  let comp: DocumentSortieComponent;
  let fixture: ComponentFixture<DocumentSortieComponent>;
  let service: DocumentSortieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentSortieComponent],
    })
      .overrideTemplate(DocumentSortieComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentSortieComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DocumentSortieService);

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
    expect(comp.documentSorties?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
