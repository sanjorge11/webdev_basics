import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class BaseService {

  private baseUrl : string = "http://localhost:8080/base/"; 

  constructor(
    private http: HttpClient,
    private router: Router
    ) { }

  getGreeting() {
    return this.http.get(this.baseUrl + "home", { responseType: 'text' }); 
  }

  getRole() {
    return this.http.get(this.baseUrl + "currentUserRole", { responseType: 'text' }); 
  }

}
