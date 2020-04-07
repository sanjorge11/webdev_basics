import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomePageComponent } from './home-page/home-page.component';

import { FacultyModule } from './faculty/faculty.module';
import { CoursesModule } from './courses/courses.module';
import { AboutUsModule } from './about-us/about-us.module';
import { AboutUsComponent } from './about-us/about-us.component';
import { DiagramComponent } from './diagram/diagram.component'; 

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    HomePageComponent,
    AboutUsComponent,
    DiagramComponent
  ],
  imports: [
    BrowserModule,
    
    FacultyModule,      //note that these modules are not lazily loaded
    CoursesModule,
    AboutUsModule,

    AppRoutingModule    //this must be called last
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
