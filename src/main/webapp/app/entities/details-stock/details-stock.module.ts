import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetailsStockComponent } from './list/details-stock.component';
import { DetailsStockDetailComponent } from './detail/details-stock-detail.component';
import { DetailsStockUpdateComponent } from './update/details-stock-update.component';
import { DetailsStockDeleteDialogComponent } from './delete/details-stock-delete-dialog.component';
import { DetailsStockRoutingModule } from './route/details-stock-routing.module';

@NgModule({
  imports: [SharedModule, DetailsStockRoutingModule],
  declarations: [DetailsStockComponent, DetailsStockDetailComponent, DetailsStockUpdateComponent, DetailsStockDeleteDialogComponent],
  entryComponents: [DetailsStockDeleteDialogComponent],
})
export class DetailsStockModule {}
