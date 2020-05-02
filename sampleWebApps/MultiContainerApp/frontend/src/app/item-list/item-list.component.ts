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
    this.itemService.getItems().subscribe((data : Item[]) => {
      this.itemsArr = data; 
      console.log(this.itemsArr);
    });

    this.textInput = '';
  }

  onAdd() { 
    //add item to local array after serverside 
    //accepts item and provides unique id
    let newItem = new Item(-1, false, this.textInput); 

    this.itemService.addItem(newItem).subscribe((data : Item) => {
      this.itemsArr.push(data);
      console.log(data); 

      this.textInput = '';
    });

  }

  onItemCompletionChange(item, event) {

    for(let a=0; a<this.itemsArr.length; a++) { 
      if(this.itemsArr[a].id === item.id) {

        item.completed = event.target.checked;

        this.itemService.updateItem(item).subscribe((data : any) => {
          console.log(data);
          this.itemsArr[a].completed = item.completed;
      }); 
      }
    }
    console.log(this.itemsArr);
  }

  onDelete(item) { 
    console.log(item); 

    this.itemService.deleteItem(item).subscribe((data : any) => {
      let tempArr = []; 
      for(let a=0; a<this.itemsArr.length; a++) { 
        if(this.itemsArr[a].id !== item.id) tempArr.push(this.itemsArr[a]); 
      }

      this.itemsArr = tempArr;

      console.log(data);
    });

  }

}
