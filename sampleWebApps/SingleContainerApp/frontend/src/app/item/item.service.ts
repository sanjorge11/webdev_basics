import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Item } from './item';

let itemList = [
  new Item(1, false, "item 1"),
  new Item(2, false, "item 2"),
  new Item(3, false, "item 3")
];

let itemsGETPromise = Promise.resolve(itemList);
let itemsPOSTPromise = Promise.resolve("Successfully added Item");
let itemsPUTPromise = Promise.resolve("Successfully updated Item");
let itemsDELETEPromise = Promise.resolve("Successfully deleted Item");


@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor() { }

  getItems() {
    return itemsGETPromise;
  }
  
  addItem(item : Item) { 
    return itemsPOSTPromise;
  }

  updateItem(item : Item) { 
    return itemsPUTPromise;
  }

  deleteItem(item : Item) {
    return itemsDELETEPromise; 
  }

}
