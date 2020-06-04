import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BaseRoutingModule } from './base-routing.module';
import { BaseComponent } from './base.component';
import { StudentComponent } from '../student/student.component'; 
import { AdminComponent } from '../admin/admin.component'; 

import { HomePageComponent } from '../home-page/home-page.component';

@NgModule({
  declarations: [
    BaseComponent,
    HomePageComponent,
    StudentComponent,
    AdminComponent
  ],
  imports: [
    CommonModule,
    BaseRoutingModule
  ]
})
export class BaseModule { }
