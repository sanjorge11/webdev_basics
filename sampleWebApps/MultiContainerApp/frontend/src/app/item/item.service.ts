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
  //Sources: 
  //How to Resolve Issue
  // https://stackoverflow.com/questions/55274801/docker-compose-resolve-hostname-in-url
  // https://stackoverflow.com/questions/46987726/linked-docker-compose-containers-making-http-requests
  //
  //What is etc/hosts? 
  //The /etc/hosts file contains a mapping of IP addresses to URLs. Your browser uses entries in the /etc/hosts file 
  //to override the IP-address-to-URL mapping returned by a DNS server. This is useful for testing DNS (domain name system) 
  //changes and the SSL configuration before making a website live.
  //https://support.acquia.com/hc/en-us/articles/360004175973-Using-an-etc-hosts-file-for-custom-domains-during-development
  //
  //Summary: 
  //Unresolved hostname problem occured when trying to use container name as hostname, this can be solved by adding 
  //an entry to /etc/hosts or using document.location.hostname property -- 
  //issue occurs because request is done from browser (which doesn't know about container ip) and not done 
  //from nginx server container (which does know about their ip addresses)
  
  //private baseUrl : string = "http://spring-boot-app:3000/"; 
  private baseUrl : string = "http://localhost:3000/";  //or use "http://" + document.location.hostname + ":3000/"; 
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
