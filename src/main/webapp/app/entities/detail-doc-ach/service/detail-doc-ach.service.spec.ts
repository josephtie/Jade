import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetailDocAch, DetailDocAch } from '../detail-doc-ach.model';

import { DetailDocAchService } from './detail-doc-ach.service';

describe('DetailDocAch Service', () => {
  let service: DetailDocAchService;
  let httpMock: HttpTestingController;
  let elemDefault: IDetailDocAch;
  let expectedResult: IDetailDocAch | IDetailDocAch[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailDocAchService);
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

    it('should create a DetailDocAch', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DetailDocAch()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailDocAch', () => {
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

    it('should partial update a DetailDocAch', () => {
      const patchObject = Object.assign(
        {
          montligne: 1,
          qteUnit: 1,
          designation: 'BBBBBB',
        },
        new DetailDocAch()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetailDocAch', () => {
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

    it('should delete a DetailDocAch', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDetailDocAchToCollectionIfMissing', () => {
      it('should add a DetailDocAch to an empty array', () => {
        const detailDocAch: IDetailDocAch = { id: 123 };
        expectedResult = service.addDetailDocAchToCollectionIfMissing([], detailDocAch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDocAch);
      });

      it('should not add a DetailDocAch to an array that contains it', () => {
        const detailDocAch: IDetailDocAch = { id: 123 };
        const detailDocAchCollection: IDetailDocAch[] = [
          {
            ...detailDocAch,
          },
          { id: 456 },
        ];
        expectedResult = service.addDetailDocAchToCollectionIfMissing(detailDocAchCollection, detailDocAch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailDocAch to an array that doesn't contain it", () => {
        const detailDocAch: IDetailDocAch = { id: 123 };
        const detailDocAchCollection: IDetailDocAch[] = [{ id: 456 }];
        expectedResult = service.addDetailDocAchToCollectionIfMissing(detailDocAchCollection, detailDocAch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDocAch);
      });

      it('should add only unique DetailDocAch to an array', () => {
        const detailDocAchArray: IDetailDocAch[] = [{ id: 123 }, { id: 456 }, { id: 95760 }];
        const detailDocAchCollection: IDetailDocAch[] = [{ id: 123 }];
        expectedResult = service.addDetailDocAchToCollectionIfMissing(detailDocAchCollection, ...detailDocAchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailDocAch: IDetailDocAch = { id: 123 };
        const detailDocAch2: IDetailDocAch = { id: 456 };
        expectedResult = service.addDetailDocAchToCollectionIfMissing([], detailDocAch, detailDocAch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDocAch);
        expect(expectedResult).toContain(detailDocAch2);
      });

      it('should accept null and undefined values', () => {
        const detailDocAch: IDetailDocAch = { id: 123 };
        expectedResult = service.addDetailDocAchToCollectionIfMissing([], null, detailDocAch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDocAch);
      });

      it('should return initial array if no DetailDocAch is added', () => {
        const detailDocAchCollection: IDetailDocAch[] = [{ id: 123 }];
        expectedResult = service.addDetailDocAchToCollectionIfMissing(detailDocAchCollection, undefined, null);
        expect(expectedResult).toEqual(detailDocAchCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
