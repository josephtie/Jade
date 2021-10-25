import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDocumentVente, DocumentVente } from '../document-vente.model';

import { DocumentVenteService } from './document-vente.service';

describe('DocumentVente Service', () => {
  let service: DocumentVenteService;
  let httpMock: HttpTestingController;
  let elemDefault: IDocumentVente;
  let expectedResult: IDocumentVente | IDocumentVente[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentVenteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateSaisie: currentDate,
      taxe: 0,
      observation: 'AAAAAAA',
      montantht: 0,
      montantttc: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateSaisie: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DocumentVente', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateSaisie: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSaisie: currentDate,
        },
        returnedFromService
      );

      service.create(new DocumentVente()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentVente', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateSaisie: currentDate.format(DATE_FORMAT),
          taxe: 1,
          observation: 'BBBBBB',
          montantht: 1,
          montantttc: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSaisie: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocumentVente', () => {
      const patchObject = Object.assign(
        {
          dateSaisie: currentDate.format(DATE_FORMAT),
          observation: 'BBBBBB',
        },
        new DocumentVente()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateSaisie: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocumentVente', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateSaisie: currentDate.format(DATE_FORMAT),
          taxe: 1,
          observation: 'BBBBBB',
          montantht: 1,
          montantttc: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSaisie: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DocumentVente', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDocumentVenteToCollectionIfMissing', () => {
      it('should add a DocumentVente to an empty array', () => {
        const documentVente: IDocumentVente = { id: 123 };
        expectedResult = service.addDocumentVenteToCollectionIfMissing([], documentVente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentVente);
      });

      it('should not add a DocumentVente to an array that contains it', () => {
        const documentVente: IDocumentVente = { id: 123 };
        const documentVenteCollection: IDocumentVente[] = [
          {
            ...documentVente,
          },
          { id: 456 },
        ];
        expectedResult = service.addDocumentVenteToCollectionIfMissing(documentVenteCollection, documentVente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentVente to an array that doesn't contain it", () => {
        const documentVente: IDocumentVente = { id: 123 };
        const documentVenteCollection: IDocumentVente[] = [{ id: 456 }];
        expectedResult = service.addDocumentVenteToCollectionIfMissing(documentVenteCollection, documentVente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentVente);
      });

      it('should add only unique DocumentVente to an array', () => {
        const documentVenteArray: IDocumentVente[] = [{ id: 123 }, { id: 456 }, { id: 47504 }];
        const documentVenteCollection: IDocumentVente[] = [{ id: 123 }];
        expectedResult = service.addDocumentVenteToCollectionIfMissing(documentVenteCollection, ...documentVenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentVente: IDocumentVente = { id: 123 };
        const documentVente2: IDocumentVente = { id: 456 };
        expectedResult = service.addDocumentVenteToCollectionIfMissing([], documentVente, documentVente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentVente);
        expect(expectedResult).toContain(documentVente2);
      });

      it('should accept null and undefined values', () => {
        const documentVente: IDocumentVente = { id: 123 };
        expectedResult = service.addDocumentVenteToCollectionIfMissing([], null, documentVente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentVente);
      });

      it('should return initial array if no DocumentVente is added', () => {
        const documentVenteCollection: IDocumentVente[] = [{ id: 123 }];
        expectedResult = service.addDocumentVenteToCollectionIfMissing(documentVenteCollection, undefined, null);
        expect(expectedResult).toEqual(documentVenteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
