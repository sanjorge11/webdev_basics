import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl : string = "http://localhost:8080/"; 

  constructor(private http: HttpClient) { }

  registerUser(user : User) {
    return this.http.post(this.baseUrl + "register", user);
  }

  loginUser(user : User) { //used observe : 'response' to get full response that had null content, we wanted to read the headers
    return this.http.post(this.baseUrl + "login", user, { observe: 'response' } ) ; 
  }

}
