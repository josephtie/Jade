import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISociete, Societe } from '../societe.model';

import { SocieteService } from './societe.service';

describe('Societe Service', () => {
  let service: SocieteService;
  let httpMock: HttpTestingController;
  let elemDefault: ISociete;
  let expectedResult: ISociete | ISociete[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocieteService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      raisonsoc: 'AAAAAAA',
      sigle: 'AAAAAAA',
      activitepp: 'AAAAAAA',
      adressgeo: 'AAAAAAA',
      formjuri: 'AAAAAAA',
      telephone: 'AAAAAAA',
      bp: 'AAAAAAA',
      registreCce: 'AAAAAAA',
      pays: 'AAAAAAA',
      ville: 'AAAAAAA',
      commune: 'AAAAAAA',
      email: 'AAAAAAA',
      actif: false,
      fileDataContentType: 'image/png',
      fileData: 'AAAAAAA',
      urlLogo: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Societe', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Societe()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Societe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          raisonsoc: 'BBBBBB',
          sigle: 'BBBBBB',
          activitepp: 'BBBBBB',
          adressgeo: 'BBBBBB',
          formjuri: 'BBBBBB',
          telephone: 'BBBBBB',
          bp: 'BBBBBB',
          registreCce: 'BBBBBB',
          pays: 'BBBBBB',
          ville: 'BBBBBB',
          commune: 'BBBBBB',
          email: 'BBBBBB',
          actif: true,
          fileData: 'BBBBBB',
          urlLogo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Societe', () => {
      const patchObject = Object.assign(
        {
          raisonsoc: 'BBBBBB',
          activitepp: 'BBBBBB',
          formjuri: 'BBBBBB',
          telephone: 'BBBBBB',
          bp: 'BBBBBB',
          urlLogo: 'BBBBBB',
        },
        new Societe()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Societe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          raisonsoc: 'BBBBBB',
          sigle: 'BBBBBB',
          activitepp: 'BBBBBB',
          adressgeo: 'BBBBBB',
          formjuri: 'BBBBBB',
          telephone: 'BBBBBB',
          bp: 'BBBBBB',
          registreCce: 'BBBBBB',
          pays: 'BBBBBB',
          ville: 'BBBBBB',
          commune: 'BBBBBB',
          email: 'BBBBBB',
          actif: true,
          fileData: 'BBBBBB',
          urlLogo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Societe', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocieteToCollectionIfMissing', () => {
      it('should add a Societe to an empty array', () => {
        const societe: ISociete = { id: 123 };
        expectedResult = service.addSocieteToCollectionIfMissing([], societe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societe);
      });

      it('should not add a Societe to an array that contains it', () => {
        const societe: ISociete = { id: 123 };
        const societeCollection: ISociete[] = [
          {
            ...societe,
          },
          { id: 456 },
        ];
        expectedResult = service.addSocieteToCollectionIfMissing(societeCollection, societe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Societe to an array that doesn't contain it", () => {
        const societe: ISociete = { id: 123 };
        const societeCollection: ISociete[] = [{ id: 456 }];
        expectedResult = service.addSocieteToCollectionIfMissing(societeCollection, societe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societe);
      });

      it('should add only unique Societe to an array', () => {
        const societeArray: ISociete[] = [{ id: 123 }, { id: 456 }, { id: 41791 }];
        const societeCollection: ISociete[] = [{ id: 123 }];
        expectedResult = service.addSocieteToCollectionIfMissing(societeCollection, ...societeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societe: ISociete = { id: 123 };
        const societe2: ISociete = { id: 456 };
        expectedResult = service.addSocieteToCollectionIfMissing([], societe, societe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societe);
        expect(expectedResult).toContain(societe2);
      });

      it('should accept null and undefined values', () => {
        const societe: ISociete = { id: 123 };
        expectedResult = service.addSocieteToCollectionIfMissing([], null, societe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societe);
      });

      it('should return initial array if no Societe is added', () => {
        const societeCollection: ISociete[] = [{ id: 123 }];
        expectedResult = service.addSocieteToCollectionIfMissing(societeCollection, undefined, null);
        expect(expectedResult).toEqual(societeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
