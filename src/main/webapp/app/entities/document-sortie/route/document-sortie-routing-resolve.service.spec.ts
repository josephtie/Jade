jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDocumentSortie, DocumentSortie } from '../document-sortie.model';
import { DocumentSortieService } from '../service/document-sortie.service';

import { DocumentSortieRoutingResolveService } from './document-sortie-routing-resolve.service';

describe('DocumentSortie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DocumentSortieRoutingResolveService;
  let service: DocumentSortieService;
  let resultDocumentSortie: IDocumentSortie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DocumentSortieRoutingResolveService);
    service = TestBed.inject(DocumentSortieService);
    resultDocumentSortie = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumentSortie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentSortie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentSortie).toEqual({ id: 123 });
    });

    it('should return new IDocumentSortie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentSortie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumentSortie).toEqual(new DocumentSortie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DocumentSortie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentSortie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentSortie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
