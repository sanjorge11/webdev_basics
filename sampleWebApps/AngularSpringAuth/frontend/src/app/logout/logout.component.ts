import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
  //   console.log('logout');
  //   //logout is a GET request because csrf is disabled
  //   this.authService.logoutUser().subscribe((data : any) => {
  //     console.log(data);
  // }); 
  }

}
