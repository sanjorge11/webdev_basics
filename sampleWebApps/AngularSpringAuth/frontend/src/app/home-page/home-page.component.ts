import { Component, OnInit } from '@angular/core';

import { BaseService } from '../base/base.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private greeting : string;

  constructor(
    private baseService: BaseService
  ) { 
    this.greeting = ''; 
  }

  ngOnInit() {
    this.baseService.getGreeting().subscribe((data: any) => {
      this.greeting = data;
    });
  }

}
