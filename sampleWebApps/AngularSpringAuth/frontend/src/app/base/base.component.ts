import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

import { Router } from '@angular/router';
import { BaseService } from './base.service';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  private isAdmin : boolean;
  private isAdminTrainee : boolean;
  private isStudent : boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private baseService: BaseService
  ) { 
    this.isAdmin = false; 
    this.isAdminTrainee = false;
    this.isStudent = false;
  }

  ngOnInit() { 
    this.baseService.getRole().subscribe((data: any) => {
      this.isAdmin = (data === 'ROLE_ADMIN');
      this.isAdminTrainee = (data === 'ROLE_ADMINTRAINEE');
      this.isStudent = (data === 'ROLE_STUDENT'); 
    });
  }

  logout() { 
    //logout is a GET request because csrf is disabled
    this.authService.logoutUser().subscribe((data: any) => {
      //console.log('logged out');
      this.router.navigate(['/login']);

      this.isAdmin = false; 
      this.isAdminTrainee = false;
      this.isStudent = false;
    });
  }

}
