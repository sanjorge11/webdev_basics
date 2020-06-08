import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl : string = 'http://localhost:8080/'; 

  constructor(
    private http: HttpClient,
    private router: Router
    ) { }

  registerUser(user : User) {
    return this.http.post(this.baseUrl + 'register', user, { responseType: 'text' }); 
  }

  loginUser(user : User) { //used observe : 'response' to get full response that had null content, we wanted to read the headers
    return this.http.post(this.baseUrl + 'login', user, { observe: 'response' });  
  }

  logoutUser() { 
    return this.http.get(this.baseUrl + 'logout', { responseType: 'text' });     //Spring Boot is configured to re-direct to localhost:8080/
  }

}
