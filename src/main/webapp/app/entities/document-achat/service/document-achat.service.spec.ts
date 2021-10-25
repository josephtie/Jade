import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDocumentAchat, DocumentAchat } from '../document-achat.model';

import { DocumentAchatService } from './document-achat.service';

describe('DocumentAchat Service', () => {
  let service: DocumentAchatService;
  let httpMock: HttpTestingController;
  let elemDefault: IDocumentAchat;
  let expectedResult: IDocumentAchat | IDocumentAchat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentAchatService);
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

    it('should create a DocumentAchat', () => {
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

      service.create(new DocumentAchat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentAchat', () => {
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

    it('should partial update a DocumentAchat', () => {
      const patchObject = Object.assign(
        {
          montantht: 1,
        },
        new DocumentAchat()
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

    it('should return a list of DocumentAchat', () => {
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

    it('should delete a DocumentAchat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDocumentAchatToCollectionIfMissing', () => {
      it('should add a DocumentAchat to an empty array', () => {
        const documentAchat: IDocumentAchat = { id: 123 };
        expectedResult = service.addDocumentAchatToCollectionIfMissing([], documentAchat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentAchat);
      });

      it('should not add a DocumentAchat to an array that contains it', () => {
        const documentAchat: IDocumentAchat = { id: 123 };
        const documentAchatCollection: IDocumentAchat[] = [
          {
            ...documentAchat,
          },
          { id: 456 },
        ];
        expectedResult = service.addDocumentAchatToCollectionIfMissing(documentAchatCollection, documentAchat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentAchat to an array that doesn't contain it", () => {
        const documentAchat: IDocumentAchat = { id: 123 };
        const documentAchatCollection: IDocumentAchat[] = [{ id: 456 }];
        expectedResult = service.addDocumentAchatToCollectionIfMissing(documentAchatCollection, documentAchat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentAchat);
      });

      it('should add only unique DocumentAchat to an array', () => {
        const documentAchatArray: IDocumentAchat[] = [{ id: 123 }, { id: 456 }, { id: 28777 }];
        const documentAchatCollection: IDocumentAchat[] = [{ id: 123 }];
        expectedResult = service.addDocumentAchatToCollectionIfMissing(documentAchatCollection, ...documentAchatArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentAchat: IDocumentAchat = { id: 123 };
        const documentAchat2: IDocumentAchat = { id: 456 };
        expectedResult = service.addDocumentAchatToCollectionIfMissing([], documentAchat, documentAchat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentAchat);
        expect(expectedResult).toContain(documentAchat2);
      });

      it('should accept null and undefined values', () => {
        const documentAchat: IDocumentAchat = { id: 123 };
        expectedResult = service.addDocumentAchatToCollectionIfMissing([], null, documentAchat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentAchat);
      });

      it('should return initial array if no DocumentAchat is added', () => {
        const documentAchatCollection: IDocumentAchat[] = [{ id: 123 }];
        expectedResult = service.addDocumentAchatToCollectionIfMissing(documentAchatCollection, undefined, null);
        expect(expectedResult).toEqual(documentAchatCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
