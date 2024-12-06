package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryItem {
	private String itemName;
	private int quantity;
	private LocalDate expirationDate;
	private String itemCategory;
	
	
	
	//Constructors
	public InventoryItem() {
		this.itemName="None";
		this.quantity=0;
		this.expirationDate = LocalDate.of(1900, 12, 31);
	}
	
	/**
	 * @param itemName
	 * @param quantity
	 * @param expirationDate
	 * @param itemCategory
	 */
	public InventoryItem(String itemName, int quantity, LocalDate expirationDate, String itemCategory) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.expirationDate = expirationDate;
		this.itemCategory = itemCategory;
	}
	
	
	//Getters and Setters
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the expirationDate
	 */
	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the itemCategory
	 */
	public String getItemCategory() {
		return itemCategory;
	}

	/**
	 * @param itemCategory the itemCategory to set
	 */
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	
	//Class Methods
	public String displayItem() {
		//Returns a string that contains the item information to be displayed
		DateTimeFormatter formatExpDate = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String displayString;
		String formattedExpDate;
		
		formattedExpDate = expirationDate.format(formatExpDate);
		
		displayString = "Item Name: " + formatString(itemName) + "\nItem Quantity: " + quantity 
						+ "\nItem Category: " + formatString(itemCategory) + "\nExpiration Date: " + formattedExpDate;
		
		return displayString;
	}
	
	public String formatString(String inString) {
		//Formats a string to have an upper case first letter and lower case letters after that
		//Also able to format the second word if there is one, but not set up for more than two words
		String formattedString;
		
		if (inString.contains(" ")) {
			int spaceIndex = inString.indexOf(" ");
			String firstString;
			String secondString;
			firstString = inString.substring(0, 1).toUpperCase() + inString.substring(1, spaceIndex).toLowerCase();
			secondString = inString.substring(spaceIndex + 1, spaceIndex + 2).toUpperCase() + inString.substring(spaceIndex + 2).toLowerCase();
			formattedString = firstString + " " + secondString;
		}
		else {
			formattedString = inString.substring(0,1).toUpperCase() + inString.substring(1).toLowerCase();
		}
		
		return formattedString;
	}
	
}
