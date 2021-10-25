import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypeProduit, TypeProduit } from '../type-produit.model';

import { TypeProduitService } from './type-produit.service';

describe('TypeProduit Service', () => {
  let service: TypeProduitService;
  let httpMock: HttpTestingController;
  let elemDefault: ITypeProduit;
  let expectedResult: ITypeProduit | ITypeProduit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypeProduitService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      libelle: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a TypeProduit', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TypeProduit()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypeProduit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libelle: 'BBBBBB',
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypeProduit', () => {
      const patchObject = Object.assign(
        {
          libelle: 'BBBBBB',
        },
        new TypeProduit()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypeProduit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libelle: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a TypeProduit', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTypeProduitToCollectionIfMissing', () => {
      it('should add a TypeProduit to an empty array', () => {
        const typeProduit: ITypeProduit = { id: 123 };
        expectedResult = service.addTypeProduitToCollectionIfMissing([], typeProduit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeProduit);
      });

      it('should not add a TypeProduit to an array that contains it', () => {
        const typeProduit: ITypeProduit = { id: 123 };
        const typeProduitCollection: ITypeProduit[] = [
          {
            ...typeProduit,
          },
          { id: 456 },
        ];
        expectedResult = service.addTypeProduitToCollectionIfMissing(typeProduitCollection, typeProduit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypeProduit to an array that doesn't contain it", () => {
        const typeProduit: ITypeProduit = { id: 123 };
        const typeProduitCollection: ITypeProduit[] = [{ id: 456 }];
        expectedResult = service.addTypeProduitToCollectionIfMissing(typeProduitCollection, typeProduit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeProduit);
      });

      it('should add only unique TypeProduit to an array', () => {
        const typeProduitArray: ITypeProduit[] = [{ id: 123 }, { id: 456 }, { id: 94390 }];
        const typeProduitCollection: ITypeProduit[] = [{ id: 123 }];
        expectedResult = service.addTypeProduitToCollectionIfMissing(typeProduitCollection, ...typeProduitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typeProduit: ITypeProduit = { id: 123 };
        const typeProduit2: ITypeProduit = { id: 456 };
        expectedResult = service.addTypeProduitToCollectionIfMissing([], typeProduit, typeProduit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeProduit);
        expect(expectedResult).toContain(typeProduit2);
      });

      it('should accept null and undefined values', () => {
        const typeProduit: ITypeProduit = { id: 123 };
        expectedResult = service.addTypeProduitToCollectionIfMissing([], null, typeProduit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeProduit);
      });

      it('should return initial array if no TypeProduit is added', () => {
        const typeProduitCollection: ITypeProduit[] = [{ id: 123 }];
        expectedResult = service.addTypeProduitToCollectionIfMissing(typeProduitCollection, undefined, null);
        expect(expectedResult).toEqual(typeProduitCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
