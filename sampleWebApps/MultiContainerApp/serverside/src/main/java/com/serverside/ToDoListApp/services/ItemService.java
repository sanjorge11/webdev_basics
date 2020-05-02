package com.serverside.ToDoListApp.services;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverside.ToDoListApp.dao.ItemDAO;
import com.serverside.ToDoListApp.domain.Item;

@Service
public class ItemService {
	
	private final Logger _log = Logger.getLogger(ItemService.class);
	
	@Autowired
	ItemDAO itemDAO;
	
	public List<Item> getAllItems() { 
		
		List<Item> res = itemDAO.getAllItems();
		
		return res;
	}
	
	public Item getItem(int id) { 
		
		Item res = itemDAO.getItem(id);
		
		return res;
	}
	
	public Item createItem(boolean completed, String description) { 
		int uniqueId = itemDAO.getNextVal(); 
		
		itemDAO.createItem(uniqueId, completed, description);
		
		Item newItem = new Item(uniqueId, completed, description); 
		return newItem;
	}

	public void updateItemCompletion(int id, boolean completed) throws Exception { 
		
		int res = itemDAO.updateItemCompletion(id, completed);
		
		//Exception handling example
		if(res != 1) { 	
			_log.error("Error updating Faculty with ID: " + id);
			throw new Exception("Error updating Faculty with ID: " + id);
		}
		
	}
	
	public void deleteItem(int id) { 
		itemDAO.deleteItem(id);
	}
}
