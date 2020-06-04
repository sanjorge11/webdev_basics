import { Component, OnInit } from '@angular/core';
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

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.usernameInput = ''; 
    this.passwordInput = ''; 
  }

  login() { 
    let user = new User(this.usernameInput, this.passwordInput, undefined);

    this.authService.loginUser(user);

  }

}
