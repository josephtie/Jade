jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDetailsStock, DetailsStock } from '../details-stock.model';
import { DetailsStockService } from '../service/details-stock.service';

import { DetailsStockRoutingResolveService } from './details-stock-routing-resolve.service';

describe('DetailsStock routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DetailsStockRoutingResolveService;
  let service: DetailsStockService;
  let resultDetailsStock: IDetailsStock | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DetailsStockRoutingResolveService);
    service = TestBed.inject(DetailsStockService);
    resultDetailsStock = undefined;
  });

  describe('resolve', () => {
    it('should return IDetailsStock returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailsStock = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailsStock).toEqual({ id: 123 });
    });

    it('should return new IDetailsStock if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailsStock = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDetailsStock).toEqual(new DetailsStock());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DetailsStock })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailsStock = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailsStock).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
