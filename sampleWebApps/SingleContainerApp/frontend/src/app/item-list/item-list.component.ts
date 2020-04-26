import { Component, OnInit } from '@angular/core';

import { Item } from '../item/item';
import { ItemService } from '../item/item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {

  public itemsArr: Item[];
  public textInput: string; 

  constructor(
    private itemService: ItemService
  ) { }

  ngOnInit() {
    this.itemService.getItems().then((data) => {
      this.itemsArr = data;
      console.log(data);
    });

    this.textInput = '';
  }

  onAdd() { 
    //will need to add item to local array after serverside 
    //accepts item and provides unique id
    let newItem = new Item(-1, false, this.textInput); 
    this.itemsArr.push(newItem);
  }

  onItemCompletionChange(item, event) {

    for(let a=0; a<this.itemsArr.length; a++) { 
      if(this.itemsArr[a].id === item.id) {
        this.itemsArr[a].completed = event.target.checked;
      }
    }

    console.log(this.itemsArr);
  }

  onDelete(item) { 
    console.log(item); 
    
    let tempArr = []; 
    for(let a=0; a<this.itemsArr.length; a++) { 
      if(this.itemsArr[a].id !== item.id) tempArr.push(this.itemsArr[a]); 
    }

    this.itemsArr = tempArr;
  }

}
