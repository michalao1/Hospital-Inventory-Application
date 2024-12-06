package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.InventoryItem;
import model.InventoryManager;

public class InventoryApp {

	public static void main(String[] args) {
		InventoryManager manager = new InventoryManager();
		//Create the main frame
		JFrame frame = new JFrame("Inventory Manager");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(6, 1));
		
		//Create buttons
		JButton viewInventoryButton = new JButton("View Inventory");
		JButton addItemButton = new JButton("Add Item");
		JButton removeItemButton = new JButton("Remove Item");
		JButton checkExpirationsButton = new JButton("Check Expirations");
		JButton sortInventoryButton = new JButton("Sort Inventory");
		JButton exitButton = new JButton("Exit");
		
		//Add buttons
		frame.add(viewInventoryButton);
        frame.add(addItemButton);
        frame.add(removeItemButton);
        frame.add(checkExpirationsButton);
        frame.add(sortInventoryButton);
        frame.add(exitButton);
		
		//Make the frame visible
		frame.setVisible(true);
		
		viewInventoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Displaying inventory...");
				displayInventory(frame, manager);
			}
		});
		
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Open a new window to add an item
				addItem(frame, manager);
			}
		});
		
		removeItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeItem(frame, manager);
			}
		});
		
		checkExpirationsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkExpirations(frame, manager);
			}
		});
		
		sortInventoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortInventory(manager);
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	//Method to open a new window to display the inventory
	private static void displayInventory(JFrame parentFrame, InventoryManager manager) {
		// Create a dialog to display the inventory
	    JDialog dialog = new JDialog(parentFrame, "Display Inventory", true);
	    dialog.setSize(700, 700);

	    // Create a JPanel to hold the inventory items
	    JPanel inventoryPanel = new JPanel();
	    inventoryPanel.setLayout(new GridLayout(0, 4));

	    // Get the list of inventory items
	    for (InventoryItem item : manager.getInventory()) {
	        // Add each inventory item's data to the panel
	        inventoryPanel.add(new JLabel("Name: " + item.getItemName()));
	        inventoryPanel.add(new JLabel("Quantity: " + item.getQuantity()));
	        inventoryPanel.add(new JLabel("Expiration Date: " + item.getExpirationDate()));
	        inventoryPanel.add(new JLabel("Category: " + item.getItemCategory()));
	    }

	    // Add the panel to the dialog
	    dialog.add(new JScrollPane(inventoryPanel));

	    // Set the dialog visible
	    dialog.setLocationRelativeTo(parentFrame);
	    dialog.setVisible(true);
	}
	
	// Method to open a new window for adding an item
    private static void addItem(JFrame parentFrame, InventoryManager manager) {
        JDialog dialog = new JDialog(parentFrame, "Add Item", true);
        dialog.setSize(500, 500);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        //Item name
        dialog.add(new JLabel("Item Name:"));
        JTextField itemNameField = new JTextField();
        dialog.add(itemNameField);
        
        //Quantity
        dialog.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        dialog.add(quantityField);

        //Expiration Date
        dialog.add(new JLabel("Expiration Date (YYYY-MM-DD):"));
        JTextField expirationDateField = new JTextField();
        dialog.add(expirationDateField);

        //Item Category
        dialog.add(new JLabel("Category:"));
        JTextField categoryField = new JTextField();
        dialog.add(categoryField);

        //Buttons
        JButton addButton = new JButton("Add Item");
        dialog.add(addButton);
        JButton cancelButton = new JButton("Cancel");
        dialog.add(cancelButton);
        
        //Button Listeners
        addButton.addActionListener(e -> {
        	String itemName = itemNameField.getText();
        	String quantityText = quantityField.getText();
        	String expirationText = expirationDateField.getText();
        	String category = categoryField.getText();
        	
        	//Validate quantity
        	int quantity = 0;
        	boolean valid = true;
        	
        	try {
        		quantity = Integer.parseInt(quantityText);
        		if (quantity <= 0) {
        			JOptionPane.showMessageDialog(dialog, "Quantity must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        			valid = false;
        		}
        	}
        	catch (NumberFormatException ex) {
    			JOptionPane.showMessageDialog(dialog, "Quantity must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    			valid = false;
    		}
        	
        	//Validate expiration date
        	LocalDate expirationDate = null;
        	try {
        		expirationDate = LocalDate.parse(expirationText);
        	}
        	catch (Exception ex) {
        		JOptionPane.showMessageDialog(dialog, "Invalid date format! Use YYYY-MM-DD.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        		valid = false;
        	}
        	
        	//If all input is valid, add the item
        	if (valid) {
        		InventoryItem newItem = new InventoryItem(itemName, quantity, expirationDate, category);
        		manager.addItem(newItem);
        		JOptionPane.showMessageDialog(dialog, "Item added successfully!");
        		dialog.dispose();
        	}
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());

        // Set dialog properties and show it
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    
    //Method to remove item
    private static void removeItem(JFrame parentFrame, InventoryManager manager) {
    	JDialog dialog = new JDialog(parentFrame, "Remove Item", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(5, 5, 5, 5));
        
      //Item name to remove
        dialog.add(new JLabel("Item to Remove:"));
        JTextField itemNameField = new JTextField();
        dialog.add(itemNameField);
        
        JButton removeButton = new JButton("Remove Item");
        dialog.add(removeButton);
        JButton cancelButton = new JButton("Cancel");
        dialog.add(cancelButton);
        
        removeButton.addActionListener(e -> {
        	String itemName = itemNameField.getText().trim();
        	
        	//Check if the inventory is empty
        	if(manager.getInventory().isEmpty()) {
        		JOptionPane.showMessageDialog(dialog, "Inventory is empty. Cannot remove items.", "Error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	//Check if the item exists in the inventory
        	boolean itemExists = false;
        	for (InventoryItem item : manager.getInventory()) {
        		if (item.getItemName().equalsIgnoreCase(itemName)) {
        			itemExists = true;
        			break;
        		}
        	}
        	
        	if(!itemExists) {
        		JOptionPane.showMessageDialog(dialog,  "Item not found. Please enter a valid item name.", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	else {
        		manager.removeItem(itemName);
        		JOptionPane.showMessageDialog(dialog,  "Item '" + itemName + "' has been removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        		dialog.dispose();
        	}
        });
        
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    
    //Method to view the items that are expiring within a certain time frame
    private static void checkExpirations(JFrame parentFrame, InventoryManager manager) {
    	JDialog dialog = new JDialog(parentFrame, "Check Expirations", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2, 5, 5));
        
      
        dialog.add(new JLabel("Number of days from now to check expiration:"));
        JTextField numberDaysField = new JTextField();
        dialog.add(numberDaysField);
        
        JButton checkButton = new JButton("Check Expirations");
        dialog.add(checkButton);
        JButton cancelButton = new JButton("Cancel");
        dialog.add(cancelButton);
        
        checkButton.addActionListener(e -> {
        	String numberDaysString = numberDaysField.getText();
        	boolean valid = true;
        	int numberDays = 0;
        	
        	try {
        		numberDays = Integer.parseInt(numberDaysString);
        	}
        	catch (NumberFormatException ex) {
        		JOptionPane.showMessageDialog(dialog, "Input must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        		valid = false;
        	}
        	
        	if (valid) {
                LinkedList<String> expiringItems = manager.checkExpirations(numberDays);
                if (expiringItems.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No items are expiring within the specified period.", "No Expirations", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder result = new StringBuilder("Items Expiring Soon:\n");
                    for (String item : expiringItems) {
                        result.append(item).append("\n");
                    }
                    JOptionPane.showMessageDialog(dialog, result.toString(), "Expiring Items", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> {
        	dialog.dispose();
        });
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    
    //Method to sort inventory
    private static void sortInventory(InventoryManager manager) {
    	manager.sortInventory();
    }
}
