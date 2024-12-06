package model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class InventoryManager {
	private LinkedList<InventoryItem> inventory;
	private PriorityQueue<InventoryItem> expirationQueue;
	
	private static final Comparator<InventoryItem> EXPIRATION_COMPARATOR = Comparator.comparing(InventoryItem::getExpirationDate);
	
	//Constructors
	public InventoryManager() {
		this.inventory = new LinkedList<>();
		this.expirationQueue = new PriorityQueue<>(EXPIRATION_COMPARATOR);
	}
	
	
	public InventoryManager(LinkedList<InventoryItem> inventoryItems) {
		this.inventory = inventoryItems;
		this.expirationQueue = new PriorityQueue<>(EXPIRATION_COMPARATOR);
		this.expirationQueue.addAll(inventoryItems);
	}
	
	public LinkedList<InventoryItem> getInventory() {
		return inventory;
	}
	
	public PriorityQueue<InventoryItem> getExpirationQueue() {
		return expirationQueue;
	}
	
	//Class Methods
	public void addItem(InventoryItem item) {
		inventory.add(item);
		expirationQueue.add(item);
	}
	
	public void removeItem(String name) {
		//Remove items from inventory
	    Iterator<InventoryItem> inventoryIterator = inventory.iterator();
	    while (inventoryIterator.hasNext()) {
	        InventoryItem item = inventoryIterator.next();
	        if (item.getItemName().equalsIgnoreCase(name)) {
	            inventoryIterator.remove();
	        }
	    }

	    //Remove items from expirationQueue
	    Iterator<InventoryItem> expirationIterator = expirationQueue.iterator();
	    while (expirationIterator.hasNext()) {
	        InventoryItem item = expirationIterator.next();
	        if (item.getItemName().equalsIgnoreCase(name)) {
	            expirationIterator.remove();
	        }
	    }
	}
	
	public void displayInventory() {
		if (inventory.isEmpty()) {
			System.out.println("Inventory is empty.");
		}
		else {
			for (InventoryItem item : inventory) {
				System.out.println(item.displayItem());
			}
		}
	}
	
	public LinkedList<String> checkExpirations(int daysThreshold) {
		//Checks for items expiring within a certain amount of days
		LocalDate today = LocalDate.now();
		LinkedList<String> expiringItems = new LinkedList<>();
		
		for (InventoryItem item : expirationQueue) {
	        if (item.getExpirationDate().isBefore(today.plusDays(daysThreshold))) {
	            expiringItems.add("Expiring Soon:\n" + item.displayItem());
	        }
	    }
		
		return expiringItems;
	}
	
	public void sortInventory() {
		//Sort using insertion sort
		for (int i=1; i<inventory.size(); i++) {
			InventoryItem key = inventory.get(i);
			int j = i - 1;
			
			while (j >= 0 && inventory.get(j).getItemName().compareTo(key.getItemName()) > 0) {
				inventory.set(j + 1,  inventory.get(j));
				j--;
			}
			inventory.set(j+1, key);
		}
	}
}
