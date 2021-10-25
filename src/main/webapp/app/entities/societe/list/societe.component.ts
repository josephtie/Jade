import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISociete } from '../societe.model';
import { SocieteService } from '../service/societe.service';
import { SocieteDeleteDialogComponent } from '../delete/societe-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-societe',
  templateUrl: './societe.component.html',
})
export class SocieteComponent implements OnInit {
  societes?: ISociete[];
  isLoading = false;

  constructor(protected societeService: SocieteService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.societeService.query().subscribe(
      (res: HttpResponse<ISociete[]>) => {
        this.isLoading = false;
        this.societes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISociete): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(societe: ISociete): void {
    const modalRef = this.modalService.open(SocieteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.societe = societe;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
