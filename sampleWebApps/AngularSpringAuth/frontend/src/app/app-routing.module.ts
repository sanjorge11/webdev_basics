import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component'; 

let baseUrl = 'test-auth-app'; 

const routes: Routes = [
  { path: '', redirectTo: baseUrl, pathMatch: 'full' },


  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'register', component: RegisterComponent },

  // { path: '/**', component: PageNotFoundComponent }  //wildcard route
];

@NgModule({
  // Use [RouterModule.forRoot(routes, {useHash: true})] in imports for Hash Strategy
  // Must configure Spring Boot to support urls with no hash
  // Read about why hash is required: https://angular.io/guide/router#locationstrategy-and-browser-url-styles
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
