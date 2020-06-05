import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { User } from '../auth/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private usernameInput : string; 
  private passwordInput : string; 
  private showErrorMessage : boolean;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.usernameInput = ''; 
    this.passwordInput = ''; 
    this.showErrorMessage = false;
  }

  login() { 
    this.showErrorMessage = false; 

    let user = new User(this.usernameInput, this.passwordInput, undefined);

    this.authService.loginUser(user).subscribe((data : any) => {
      //Auth is stored in cookie instead
      //console.log(data.headers.get('Authorization'));   
      this.router.navigate(["/"]);
    }, (error: any) => { 
      this.showErrorMessage = true;
    });
  }

}
