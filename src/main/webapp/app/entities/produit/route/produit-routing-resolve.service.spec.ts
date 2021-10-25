jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProduit, Produit } from '../produit.model';
import { ProduitService } from '../service/produit.service';

import { ProduitRoutingResolveService } from './produit-routing-resolve.service';

describe('Produit routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProduitRoutingResolveService;
  let service: ProduitService;
  let resultProduit: IProduit | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ProduitRoutingResolveService);
    service = TestBed.inject(ProduitService);
    resultProduit = undefined;
  });

  describe('resolve', () => {
    it('should return IProduit returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProduit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProduit).toEqual({ id: 123 });
    });

    it('should return new IProduit if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProduit = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProduit).toEqual(new Produit());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Produit })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProduit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProduit).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
