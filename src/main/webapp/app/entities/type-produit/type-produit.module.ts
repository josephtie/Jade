import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeProduitComponent } from './list/type-produit.component';
import { TypeProduitDetailComponent } from './detail/type-produit-detail.component';
import { TypeProduitUpdateComponent } from './update/type-produit-update.component';
import { TypeProduitDeleteDialogComponent } from './delete/type-produit-delete-dialog.component';
import { TypeProduitRoutingModule } from './route/type-produit-routing.module';

@NgModule({
  imports: [SharedModule, TypeProduitRoutingModule],
  declarations: [TypeProduitComponent, TypeProduitDetailComponent, TypeProduitUpdateComponent, TypeProduitDeleteDialogComponent],
  entryComponents: [TypeProduitDeleteDialogComponent],
})
export class TypeProduitModule {}
