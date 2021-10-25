import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TypeProduitService } from '../service/type-produit.service';

import { TypeProduitComponent } from './type-produit.component';

describe('TypeProduit Management Component', () => {
  let comp: TypeProduitComponent;
  let fixture: ComponentFixture<TypeProduitComponent>;
  let service: TypeProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TypeProduitComponent],
    })
      .overrideTemplate(TypeProduitComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeProduitComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TypeProduitService);

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
    expect(comp.typeProduits?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
