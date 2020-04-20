import { Component, OnInit } from '@angular/core';

import { Item } from '../item/item';
import { ItemService } from '../item/item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {

  constructor(
    private itemService: ItemService
  ) { }

  ngOnInit() {
    this.itemService.getItems().then((data) => {
      console.log(data);
    });
  }

}
