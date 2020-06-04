import { Component, OnInit } from '@angular/core';

import { AuthService } from '../auth/auth.service';
import { User } from '../auth/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private usernameInput : string; 
  private passwordInput : string; 
  private roleInput : string; 

  constructor(
    private authService: AuthService
  ) {
    this.usernameInput = ''; 
    this.passwordInput = ''; 
    this.roleInput = ''; 
   }

  ngOnInit() {
  }

  register() { 
    let user = new User(this.usernameInput, this.passwordInput, this.roleInput);

    this.authService.registerUser(user); 
  }

}
