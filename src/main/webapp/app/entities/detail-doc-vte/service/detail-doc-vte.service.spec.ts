import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetailDocVte, DetailDocVte } from '../detail-doc-vte.model';

import { DetailDocVteService } from './detail-doc-vte.service';

describe('DetailDocVte Service', () => {
  let service: DetailDocVteService;
  let httpMock: HttpTestingController;
  let elemDefault: IDetailDocVte;
  let expectedResult: IDetailDocVte | IDetailDocVte[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailDocVteService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      prixUnit: 0,
      prixunitnet: 0,
      montligne: 0,
      qteUnit: 0,
      remise: 0,
      quantitecolis: 0,
      designation: 'AAAAAAA',
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

    it('should create a DetailDocVte', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DetailDocVte()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailDocVte', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          prixUnit: 1,
          prixunitnet: 1,
          montligne: 1,
          qteUnit: 1,
          remise: 1,
          quantitecolis: 1,
          designation: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetailDocVte', () => {
      const patchObject = Object.assign(
        {
          prixunitnet: 1,
          montligne: 1,
          quantitecolis: 1,
          designation: 'BBBBBB',
        },
        new DetailDocVte()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetailDocVte', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          prixUnit: 1,
          prixunitnet: 1,
          montligne: 1,
          qteUnit: 1,
          remise: 1,
          quantitecolis: 1,
          designation: 'BBBBBB',
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

    it('should delete a DetailDocVte', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDetailDocVteToCollectionIfMissing', () => {
      it('should add a DetailDocVte to an empty array', () => {
        const detailDocVte: IDetailDocVte = { id: 123 };
        expectedResult = service.addDetailDocVteToCollectionIfMissing([], detailDocVte);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDocVte);
      });

      it('should not add a DetailDocVte to an array that contains it', () => {
        const detailDocVte: IDetailDocVte = { id: 123 };
        const detailDocVteCollection: IDetailDocVte[] = [
          {
            ...detailDocVte,
          },
          { id: 456 },
        ];
        expectedResult = service.addDetailDocVteToCollectionIfMissing(detailDocVteCollection, detailDocVte);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailDocVte to an array that doesn't contain it", () => {
        const detailDocVte: IDetailDocVte = { id: 123 };
        const detailDocVteCollection: IDetailDocVte[] = [{ id: 456 }];
        expectedResult = service.addDetailDocVteToCollectionIfMissing(detailDocVteCollection, detailDocVte);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDocVte);
      });

      it('should add only unique DetailDocVte to an array', () => {
        const detailDocVteArray: IDetailDocVte[] = [{ id: 123 }, { id: 456 }, { id: 79134 }];
        const detailDocVteCollection: IDetailDocVte[] = [{ id: 123 }];
        expectedResult = service.addDetailDocVteToCollectionIfMissing(detailDocVteCollection, ...detailDocVteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailDocVte: IDetailDocVte = { id: 123 };
        const detailDocVte2: IDetailDocVte = { id: 456 };
        expectedResult = service.addDetailDocVteToCollectionIfMissing([], detailDocVte, detailDocVte2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDocVte);
        expect(expectedResult).toContain(detailDocVte2);
      });

      it('should accept null and undefined values', () => {
        const detailDocVte: IDetailDocVte = { id: 123 };
        expectedResult = service.addDetailDocVteToCollectionIfMissing([], null, detailDocVte, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDocVte);
      });

      it('should return initial array if no DetailDocVte is added', () => {
        const detailDocVteCollection: IDetailDocVte[] = [{ id: 123 }];
        expectedResult = service.addDetailDocVteToCollectionIfMissing(detailDocVteCollection, undefined, null);
        expect(expectedResult).toEqual(detailDocVteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
