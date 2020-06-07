import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl : string = "http://localhost:8080/"; 

  constructor(private http: HttpClient) { }

  getStudentsForAdmin() {
    return this.http.get(this.baseUrl + "admin");
  }


}
