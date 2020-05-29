import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl : string = "http://localhost:8080/"; 

  constructor(private http: HttpClient) { }

  registerUser(user : User) {
    return this.http.post(this.baseUrl + "/register", user);
  }

  loginUser(user : User) {
    return this.http.post(this.baseUrl + "/login", user);
  }

}
