import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { FacultyComponent } from './faculty.component';

const routes: Routes = [
  { path: 'faculty', component: FacultyComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacultyRoutingModule { }
