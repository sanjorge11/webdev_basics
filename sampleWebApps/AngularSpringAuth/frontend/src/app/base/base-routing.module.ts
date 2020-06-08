import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePageComponent } from '../home-page/home-page.component';
import { BaseComponent } from './base.component';
import { StudentComponent } from '../student/student.component'; 
import { AdminComponent } from '../admin/admin.component'; 
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';

let baseUrl = 'test-auth-app'; 

const routes: Routes = [
  { 
    path: baseUrl, 
    component: BaseComponent,
    children: [
      { path: '', component: HomePageComponent },
      { path: 'students', component: StudentComponent },
      { path: 'admin', component: AdminComponent }
    ]
  },
  
  { path: '**', component: PageNotFoundComponent }  //wildcard route
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BaseRoutingModule { }
