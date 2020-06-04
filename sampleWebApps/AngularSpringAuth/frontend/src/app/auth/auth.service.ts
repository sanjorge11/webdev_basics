import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl : string = "http://localhost:8080/"; 

  constructor(
    private http: HttpClient,
    private router: Router
    ) { }

  registerUser(user : User) {
    this.http.post(this.baseUrl + "register", user, { responseType: 'text' }).subscribe((data : any) => {
      this.router.navigate(["/"]);
    }, (error : any) => {
      console.log('Username already exists.');
    });
  }

  loginUser(user : User) { //used observe : 'response' to get full response that had null content, we wanted to read the headers
    this.http.post(this.baseUrl + "login", user, { observe: 'response' }).subscribe((data : any) => {
      //Auth is stored in cookie instead
      //console.log(data.headers.get('Authorization'));   
      this.router.navigate(["/"]);
    });  
  }

  logoutUser() { 
    return this.http.post('logout', {}); 
    //return this.http.get(this.baseUrl + "logout") ; 
  }

}
