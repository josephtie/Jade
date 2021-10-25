import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDocumentSortie, DocumentSortie } from '../document-sortie.model';

import { DocumentSortieService } from './document-sortie.service';

describe('DocumentSortie Service', () => {
  let service: DocumentSortieService;
  let httpMock: HttpTestingController;
  let elemDefault: IDocumentSortie;
  let expectedResult: IDocumentSortie | IDocumentSortie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentSortieService);
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

    it('should create a DocumentSortie', () => {
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

      service.create(new DocumentSortie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentSortie', () => {
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

    it('should partial update a DocumentSortie', () => {
      const patchObject = Object.assign(
        {
          taxe: 1,
          observation: 'BBBBBB',
        },
        new DocumentSortie()
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

    it('should return a list of DocumentSortie', () => {
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

    it('should delete a DocumentSortie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDocumentSortieToCollectionIfMissing', () => {
      it('should add a DocumentSortie to an empty array', () => {
        const documentSortie: IDocumentSortie = { id: 123 };
        expectedResult = service.addDocumentSortieToCollectionIfMissing([], documentSortie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentSortie);
      });

      it('should not add a DocumentSortie to an array that contains it', () => {
        const documentSortie: IDocumentSortie = { id: 123 };
        const documentSortieCollection: IDocumentSortie[] = [
          {
            ...documentSortie,
          },
          { id: 456 },
        ];
        expectedResult = service.addDocumentSortieToCollectionIfMissing(documentSortieCollection, documentSortie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentSortie to an array that doesn't contain it", () => {
        const documentSortie: IDocumentSortie = { id: 123 };
        const documentSortieCollection: IDocumentSortie[] = [{ id: 456 }];
        expectedResult = service.addDocumentSortieToCollectionIfMissing(documentSortieCollection, documentSortie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentSortie);
      });

      it('should add only unique DocumentSortie to an array', () => {
        const documentSortieArray: IDocumentSortie[] = [{ id: 123 }, { id: 456 }, { id: 53256 }];
        const documentSortieCollection: IDocumentSortie[] = [{ id: 123 }];
        expectedResult = service.addDocumentSortieToCollectionIfMissing(documentSortieCollection, ...documentSortieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentSortie: IDocumentSortie = { id: 123 };
        const documentSortie2: IDocumentSortie = { id: 456 };
        expectedResult = service.addDocumentSortieToCollectionIfMissing([], documentSortie, documentSortie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentSortie);
        expect(expectedResult).toContain(documentSortie2);
      });

      it('should accept null and undefined values', () => {
        const documentSortie: IDocumentSortie = { id: 123 };
        expectedResult = service.addDocumentSortieToCollectionIfMissing([], null, documentSortie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentSortie);
      });

      it('should return initial array if no DocumentSortie is added', () => {
        const documentSortieCollection: IDocumentSortie[] = [{ id: 123 }];
        expectedResult = service.addDocumentSortieToCollectionIfMissing(documentSortieCollection, undefined, null);
        expect(expectedResult).toEqual(documentSortieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
