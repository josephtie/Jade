import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'societe',
        data: { pageTitle: 'jadeApp.societe.home.title' },
        loadChildren: () => import('./societe/societe.module').then(m => m.SocieteModule),
      },
      {
        path: 'type-produit',
        data: { pageTitle: 'jadeApp.typeProduit.home.title' },
        loadChildren: () => import('./type-produit/type-produit.module').then(m => m.TypeProduitModule),
      },
      {
        path: 'produit',
        data: { pageTitle: 'jadeApp.produit.home.title' },
        loadChildren: () => import('./produit/produit.module').then(m => m.ProduitModule),
      },
      {
        path: 'document-achat',
        data: { pageTitle: 'jadeApp.documentAchat.home.title' },
        loadChildren: () => import('./document-achat/document-achat.module').then(m => m.DocumentAchatModule),
      },
      {
        path: 'document-vente',
        data: { pageTitle: 'jadeApp.documentVente.home.title' },
        loadChildren: () => import('./document-vente/document-vente.module').then(m => m.DocumentVenteModule),
      },
      {
        path: 'document-sortie',
        data: { pageTitle: 'jadeApp.documentSortie.home.title' },
        loadChildren: () => import('./document-sortie/document-sortie.module').then(m => m.DocumentSortieModule),
      },
      {
        path: 'detail-doc-ach',
        data: { pageTitle: 'jadeApp.detailDocAch.home.title' },
        loadChildren: () => import('./detail-doc-ach/detail-doc-ach.module').then(m => m.DetailDocAchModule),
      },
      {
        path: 'detail-doc-vte',
        data: { pageTitle: 'jadeApp.detailDocVte.home.title' },
        loadChildren: () => import('./detail-doc-vte/detail-doc-vte.module').then(m => m.DetailDocVteModule),
      },
      {
        path: 'details-stock',
        data: { pageTitle: 'jadeApp.detailsStock.home.title' },
        loadChildren: () => import('./details-stock/details-stock.module').then(m => m.DetailsStockModule),
      },
      {
        path: 'fournisseur',
        data: { pageTitle: 'jadeApp.fournisseur.home.title' },
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.FournisseurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
