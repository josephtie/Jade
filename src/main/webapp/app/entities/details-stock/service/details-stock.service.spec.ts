import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDetailsStock, DetailsStock } from '../details-stock.model';

import { DetailsStockService } from './details-stock.service';

describe('DetailsStock Service', () => {
  let service: DetailsStockService;
  let httpMock: HttpTestingController;
  let elemDefault: IDetailsStock;
  let expectedResult: IDetailsStock | IDetailsStock[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailsStockService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      stkQTEentrant: 0,
      stkQTEinitial: 0,
      stkQTEreel: 0,
      idCommande: 0,
      idVente: 0,
      idSortie: 0,
      dateSaisie: currentDate,
      montunitaireOP: 0,
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

    it('should create a DetailsStock', () => {
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

      service.create(new DetailsStock()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailsStock', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stkQTEentrant: 1,
          stkQTEinitial: 1,
          stkQTEreel: 1,
          idCommande: 1,
          idVente: 1,
          idSortie: 1,
          dateSaisie: currentDate.format(DATE_FORMAT),
          montunitaireOP: 1,
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

    it('should partial update a DetailsStock', () => {
      const patchObject = Object.assign(
        {
          stkQTEentrant: 1,
          stkQTEinitial: 1,
          idCommande: 1,
          idVente: 1,
          idSortie: 1,
          dateSaisie: currentDate.format(DATE_FORMAT),
        },
        new DetailsStock()
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

    it('should return a list of DetailsStock', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stkQTEentrant: 1,
          stkQTEinitial: 1,
          stkQTEreel: 1,
          idCommande: 1,
          idVente: 1,
          idSortie: 1,
          dateSaisie: currentDate.format(DATE_FORMAT),
          montunitaireOP: 1,
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

    it('should delete a DetailsStock', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDetailsStockToCollectionIfMissing', () => {
      it('should add a DetailsStock to an empty array', () => {
        const detailsStock: IDetailsStock = { id: 123 };
        expectedResult = service.addDetailsStockToCollectionIfMissing([], detailsStock);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailsStock);
      });

      it('should not add a DetailsStock to an array that contains it', () => {
        const detailsStock: IDetailsStock = { id: 123 };
        const detailsStockCollection: IDetailsStock[] = [
          {
            ...detailsStock,
          },
          { id: 456 },
        ];
        expectedResult = service.addDetailsStockToCollectionIfMissing(detailsStockCollection, detailsStock);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailsStock to an array that doesn't contain it", () => {
        const detailsStock: IDetailsStock = { id: 123 };
        const detailsStockCollection: IDetailsStock[] = [{ id: 456 }];
        expectedResult = service.addDetailsStockToCollectionIfMissing(detailsStockCollection, detailsStock);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailsStock);
      });

      it('should add only unique DetailsStock to an array', () => {
        const detailsStockArray: IDetailsStock[] = [{ id: 123 }, { id: 456 }, { id: 40826 }];
        const detailsStockCollection: IDetailsStock[] = [{ id: 123 }];
        expectedResult = service.addDetailsStockToCollectionIfMissing(detailsStockCollection, ...detailsStockArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailsStock: IDetailsStock = { id: 123 };
        const detailsStock2: IDetailsStock = { id: 456 };
        expectedResult = service.addDetailsStockToCollectionIfMissing([], detailsStock, detailsStock2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailsStock);
        expect(expectedResult).toContain(detailsStock2);
      });

      it('should accept null and undefined values', () => {
        const detailsStock: IDetailsStock = { id: 123 };
        expectedResult = service.addDetailsStockToCollectionIfMissing([], null, detailsStock, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailsStock);
      });

      it('should return initial array if no DetailsStock is added', () => {
        const detailsStockCollection: IDetailsStock[] = [{ id: 123 }];
        expectedResult = service.addDetailsStockToCollectionIfMissing(detailsStockCollection, undefined, null);
        expect(expectedResult).toEqual(detailsStockCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
