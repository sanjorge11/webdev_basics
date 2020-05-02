package com.serverside.ToDoListApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.ToDoListApp.domain.Item;
import com.serverside.ToDoListApp.services.ItemService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/items")
public class ItemContoller {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Item> geItems_all() { 
		
		List<Item> itemList = itemService.getAllItems();
		
		return itemList; 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Item getItem(@PathVariable int id) { 
		
		Item item = itemService.getItem(id);
		
		return item; 
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Item createItem(@RequestBody Item item) {
		return itemService.createItem(item.getCompleted(), item.getDescription());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateItem(@PathVariable int id, @RequestBody Item item) throws Exception {
		itemService.updateItemCompletion(id, item.getCompleted());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteItem(@PathVariable int id) {
		itemService.deleteItem(id);
	}
}
