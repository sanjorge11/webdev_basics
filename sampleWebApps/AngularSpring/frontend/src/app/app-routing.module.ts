import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePageComponent } from './home-page/home-page.component';
import { DiagramComponent } from './diagram/diagram.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component'; 

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'diagram', component: DiagramComponent },
  { path: '**', component: PageNotFoundComponent }  //wildcard route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
