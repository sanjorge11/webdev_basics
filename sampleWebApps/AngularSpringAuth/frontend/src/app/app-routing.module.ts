import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

let baseUrl = 'test-auth-app'; 

const routes: Routes = [
  { path: '', redirectTo: baseUrl, pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  // Use [RouterModule.forRoot(routes, {useHash: true})] in imports for Hash Strategy
  // Must configure Spring Boot to support urls with no hash
  // Read about why hash is required: https://angular.io/guide/router#locationstrategy-and-browser-url-styles
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// Note about invoking router redirection to /login: 
// The browser and the API does not know if you are logged in or not. 
// The API relies on the fact you you have a JWT to access any data. 
// The browser does not care if you delete the cookie with the JWT. 
// Displaying UI is not necessarily bad as long as no newly requested 
// information is displayed. Any information that is already displayed 
// got there from previous authorization. If you navigate to a new page 
// that will make requests for more data to display, the server will 
// reject it and then you handle that in the front-end to re-direct to 
// a login page. If you navigate to a page or just simply use the UI, 
// but it does not make HTTP requests for more data -- then there's 
// no urgency to re-direct to the /login page. This is the way most 
// websites work from my experience. 