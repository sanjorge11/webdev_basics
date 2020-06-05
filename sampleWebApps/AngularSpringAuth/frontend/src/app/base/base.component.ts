import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() { }

  logout() { 
    console.log('logout');
    //logout is a GET request because csrf is disabled
    this.authService.logoutUser().subscribe((data: any) => {
      console.log('logged out');
    });
  }

}
