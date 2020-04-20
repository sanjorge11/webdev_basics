import { Injectable } from '@angular/core';

import { Item } from './item';

let itemList = [
  new Item(1, false, "item 1"),
  new Item(2, false, "item 2"),
  new Item(3, false, "item 3")
];

let itemsPromise = Promise.resolve(itemList);

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor() { }

  getItems() {
		return itemsPromise;
	}
}
