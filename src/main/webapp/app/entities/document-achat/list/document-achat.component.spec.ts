import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DocumentAchatService } from '../service/document-achat.service';

import { DocumentAchatComponent } from './document-achat.component';

describe('DocumentAchat Management Component', () => {
  let comp: DocumentAchatComponent;
  let fixture: ComponentFixture<DocumentAchatComponent>;
  let service: DocumentAchatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentAchatComponent],
    })
      .overrideTemplate(DocumentAchatComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentAchatComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DocumentAchatService);

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
    expect(comp.documentAchats?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
