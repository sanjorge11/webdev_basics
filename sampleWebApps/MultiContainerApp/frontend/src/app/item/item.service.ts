import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Item } from './item';

// let itemList = [
//   new Item(1, false, "item 1"),
//   new Item(2, false, "item 2"),
//   new Item(3, false, "item 3")
// ];

// let itemsPromise = Promise.resolve(itemList);

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private baseUrl : string = "http://localhost:8080/"; 
  private itemsUrl : string = "items"; 

  constructor(private http: HttpClient) { }

  getItems() {
    return this.http.get(this.baseUrl + this.itemsUrl);
  }
  
  addItem(item : Item) { 
    let requestUrl = this.itemsUrl + "/create"; 
    return this.http.post(this.baseUrl + requestUrl, item); 
  }

  updateItem(item : Item) { 
    let requestUrl = this.itemsUrl + "/" + item.id; 
    return this.http.put(this.baseUrl + requestUrl, item);
  }

  deleteItem(item : Item) {
    let requestUrl = this.itemsUrl + "/" + item.id; 
    return this.http.delete(this.baseUrl + requestUrl); 
  }

}
