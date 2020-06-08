import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

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
  private showErrorMessage : boolean; 

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.usernameInput = ''; 
    this.passwordInput = ''; 
    this.roleInput = ''; 
    this.showErrorMessage = false;
   }

  ngOnInit() { }

  register() { 
    this.showErrorMessage = false; 

    let user = new User(this.usernameInput, this.passwordInput, this.roleInput);

    this.authService.registerUser(user).subscribe((data : any) => {
      this.router.navigate(['/']);
      }, (error : any) => {
      this.showErrorMessage = true; 
    });
  }

}
