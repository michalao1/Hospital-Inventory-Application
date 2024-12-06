package controller;

import java.time.LocalDate;
import java.util.Scanner;

import model.InventoryItem;
import model.InventoryManager;

public class UserMenu {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		boolean runApplication = true;
		InventoryManager manager = new InventoryManager();
		String itemName = "None";
		int quantity = 0;
		boolean validQuantity = false;
		String dateString = "";
		LocalDate expirationDate;
		String category = "None";

		while(runApplication == true) {
			System.out.println("1. View Inventory");
			System.out.println("2. Add Item");
			System.out.println("3. Remove Item");
			System.out.println("4. Check Expirations");
			System.out.println("5. Sort Inventory");
			System.out.println("6. Exit");
			
			System.out.print("Choose an option:\n-> ");
			int choice = scnr.nextInt();
			scnr.nextLine();
			System.out.println();
			
			switch (choice) {
				case 1: 
					//display inventory
					manager.displayInventory();
					break;
					
				case 2:
					//Add an item to the inventory
					//Get item name
					System.out.print("Enter Item Name:\n-> ");
					itemName = scnr.nextLine();
					
					//Get item quantity
					//TODO add validation for quantity
					System.out.print("\nEnter Quantity:\n-> ");
					quantity = scnr.nextInt();
					scnr.nextLine();
					
					if (quantity > 0) {
						validQuantity = true;
					}
					while (validQuantity == false) {
						System.out.print("\nQuantity must be greater than zero.\nTry again:\n-> ");
						quantity = scnr.nextInt();
						scnr.nextLine();
						if (quantity > 0) {
							validQuantity = true;
						}
					}
					
					//Get expiration date
					System.out.print("\nEnter Expiration Date Month:\n-> ");
					//TODO add validation for the date (maybe make it possible to enter a number month or a string month
					int expirationMonth = scnr.nextInt();
					scnr.nextLine();
					System.out.print("\nDay:\n-> ");
					int expirationDay = scnr.nextInt();
					scnr.nextLine();
					System.out.print("\nYear:\n-> ");
					int expirationYear = scnr.nextInt();
					scnr.nextLine();
					
					String formattedMonth = String.format("%02d", expirationMonth);
					String formattedDay = String.format("%02d", expirationDay);
					
					dateString = expirationYear + "-" + formattedMonth + "-" + formattedDay;
					expirationDate = LocalDate.parse(dateString);
					
					//Get item category
					//TODO add input validation maybe have a select number of categories
					System.out.print("\nEnter Item Category:\n-> ");
					category = scnr.nextLine();
					
					//Create InventoryItem and add to manager
					InventoryItem newItem = new InventoryItem(itemName, quantity, expirationDate, category);
					manager.addItem(newItem);
					
					System.out.println("Item added successfully!\n");
					break;
					
				case 3:
					//Remove an item
					System.out.print("Displaying current inventory...");
					manager.displayInventory();
					System.out.print("Enter Item to Remove:\n-> ");
					String removeName = scnr.nextLine();
					manager.removeItem(removeName);
					break;
					
				case 4:
					//View expiration dates that are within a certain number of days
					System.out.print("\nEnter number of days to check for expiring items:\n-> ");
					int daysThreshold = scnr.nextInt();
					scnr.nextLine();
					//TODO add inputValidation
					System.out.println(manager.checkExpirations(daysThreshold));
					break;
					
				case 5:
					//Sort the inventory
					manager.sortInventory();
					System.out.println("Inventory sorted!\nView inventory items? (yes/no)\n-> ");
					String userChoice = scnr.nextLine();
					//TODO add input validation
					if (userChoice.equalsIgnoreCase("yes")) {
						manager.displayInventory();
					}
					break;
					
				case 6:
					System.out.println("Exiting...\n");
					runApplication = false;
					break;
					
				default:
					System.out.println("Invalid choice!\n");
			}
		}
		scnr.close();
	}
}
