jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITypeProduit, TypeProduit } from '../type-produit.model';
import { TypeProduitService } from '../service/type-produit.service';

import { TypeProduitRoutingResolveService } from './type-produit-routing-resolve.service';

describe('TypeProduit routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TypeProduitRoutingResolveService;
  let service: TypeProduitService;
  let resultTypeProduit: ITypeProduit | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TypeProduitRoutingResolveService);
    service = TestBed.inject(TypeProduitService);
    resultTypeProduit = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeProduit returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeProduit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeProduit).toEqual({ id: 123 });
    });

    it('should return new ITypeProduit if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeProduit = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeProduit).toEqual(new TypeProduit());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TypeProduit })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeProduit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeProduit).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
