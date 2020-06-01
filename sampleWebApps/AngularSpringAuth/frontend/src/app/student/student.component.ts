import { Component, OnInit } from '@angular/core';

import { StudentService } from './student.service'; 

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {

  private jsonOutput : string; 

  constructor(
    private studentService: StudentService
  ) {
    this.jsonOutput = ''; 
   }

  ngOnInit() {
    this.getStudents(); 
  }

  getStudents() { 
    this.studentService.getStudents().subscribe((data : any) => {

      console.log(data); 
      this.jsonOutput = data;

      if(this.jsonOutput.length == 0) { 
        this.jsonOutput = null; 
      } else { 
        this.jsonOutput = JSON.stringify(this.jsonOutput, null, "\t"); // stringify with tabs inserted at each level
      }

    }); 
  }

}
