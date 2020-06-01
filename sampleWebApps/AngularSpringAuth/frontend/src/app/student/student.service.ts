import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseUrl : string = "http://localhost:8080/"; 

  constructor(private http: HttpClient) { }

  getStudents() {
    return this.http.get(this.baseUrl + "students");
  }


}
