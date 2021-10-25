jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDocumentAchat, DocumentAchat } from '../document-achat.model';
import { DocumentAchatService } from '../service/document-achat.service';

import { DocumentAchatRoutingResolveService } from './document-achat-routing-resolve.service';

describe('DocumentAchat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DocumentAchatRoutingResolveService;
  let service: DocumentAchatService;
  let resultDocumentAchat: IDocumentAchat | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DocumentAchatRoutingResolveService);
    service = TestBed.inject(DocumentAchatService);
    resultDocumentAchat = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumentAchat returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentAchat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentAchat).toEqual({ id: 123 });
    });

    it('should return new IDocumentAchat if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentAchat = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumentAchat).toEqual(new DocumentAchat());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DocumentAchat })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentAchat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentAchat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
