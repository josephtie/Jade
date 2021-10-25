jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDocumentVente, DocumentVente } from '../document-vente.model';
import { DocumentVenteService } from '../service/document-vente.service';

import { DocumentVenteRoutingResolveService } from './document-vente-routing-resolve.service';

describe('DocumentVente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DocumentVenteRoutingResolveService;
  let service: DocumentVenteService;
  let resultDocumentVente: IDocumentVente | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DocumentVenteRoutingResolveService);
    service = TestBed.inject(DocumentVenteService);
    resultDocumentVente = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumentVente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentVente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentVente).toEqual({ id: 123 });
    });

    it('should return new IDocumentVente if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentVente = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumentVente).toEqual(new DocumentVente());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DocumentVente })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentVente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentVente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
