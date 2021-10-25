jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDetailDocVte, DetailDocVte } from '../detail-doc-vte.model';
import { DetailDocVteService } from '../service/detail-doc-vte.service';

import { DetailDocVteRoutingResolveService } from './detail-doc-vte-routing-resolve.service';

describe('DetailDocVte routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DetailDocVteRoutingResolveService;
  let service: DetailDocVteService;
  let resultDetailDocVte: IDetailDocVte | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DetailDocVteRoutingResolveService);
    service = TestBed.inject(DetailDocVteService);
    resultDetailDocVte = undefined;
  });

  describe('resolve', () => {
    it('should return IDetailDocVte returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailDocVte = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailDocVte).toEqual({ id: 123 });
    });

    it('should return new IDetailDocVte if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailDocVte = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDetailDocVte).toEqual(new DetailDocVte());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DetailDocVte })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailDocVte = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailDocVte).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
