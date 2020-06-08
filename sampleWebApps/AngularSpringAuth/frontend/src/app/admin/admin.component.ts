import { Component, OnInit } from '@angular/core';

import { AdminService } from './admin.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  private jsonOutput : string; 

  constructor(
    private adminService: AdminService
  ) { 
    this.jsonOutput = ''; 
  }

  ngOnInit() {
    this.getStudentsForAdmin();
  }

  getStudentsForAdmin() { 
    this.adminService.getStudentsForAdmin().subscribe((data : any) => {

      this.jsonOutput = data;

      if(this.jsonOutput.length == 0) { 
        this.jsonOutput = null; 
      } else { 
        this.jsonOutput = JSON.stringify(this.jsonOutput, null, '\t'); // stringify with tabs inserted at each level
      }

    }); 
  }

}
