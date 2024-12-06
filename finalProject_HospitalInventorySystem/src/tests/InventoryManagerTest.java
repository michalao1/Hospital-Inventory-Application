package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.InventoryItem;
import model.InventoryManager;

class InventoryManagerTest {
	InventoryManager manager;
	InventoryItem item1;
	InventoryItem item2;

	@BeforeEach
	void setUp() {
		//Create an inventory manager and two items before each test
		manager = new InventoryManager();
		item1 = new InventoryItem("Ozempic", 5, LocalDate.of(2026, 5, 23), "Diabetic Medicine");
        item2 = new InventoryItem("Cetirizine", 10, LocalDate.of(2024, 12, 30), "Medicine");
	}
	
	@Test
	void testAddItem() {
		//Check that the item is added to the inventory list and the expirationQueue
		manager.addItem(item1);
		manager.addItem(item2);
		
		assertTrue(manager.getInventory().contains(item1));
		assertTrue(manager.getInventory().contains(item2));
		assertTrue(manager.getExpirationQueue().contains(item1));
        assertTrue(manager.getExpirationQueue().contains(item2));
	}
	
	@Test
	void testRemoveItem() {
		//Test that the removed item is not present in the inventory list or the expirationQueue, but
		//the other item is still there
		manager.addItem(item1);
        manager.addItem(item2);

        manager.removeItem("Ozempic");

        assertFalse(manager.getInventory().contains(item1));
        assertFalse(manager.getExpirationQueue().contains(item1));
        assertTrue(manager.getInventory().contains(item2));
        assertTrue(manager.getExpirationQueue().contains(item2));
	}

	@Test
	void testDisplayInventoryEmpty() {
		//Test that the inventory isn't displayed when empty
		assertDoesNotThrow(() -> manager.displayInventory());
	}
	
	@Test
	void testDisplayInventory() {
        manager.addItem(item1);
        manager.addItem(item2);

        
        assertTrue(manager.getInventory().size() > 0);
	}

	@Test
	void testCheckExpirations() {
		//Check if items are expiring within 30 days
		//Test currently does not pass
		manager.addItem(item1);
        manager.addItem(item2);

        LinkedList<String> expiringItems = manager.checkExpirations(30);

        assertTrue(expiringItems.contains("Ozempic"));
        assertFalse(expiringItems.contains("Cetirizine"));
	}

	@Test
	void testSortInventory() {
		//Check if the items are sorted
		manager.addItem(item2);
        manager.addItem(item1);

        manager.sortInventory();

        assertEquals("Cetirizine", manager.getInventory().get(0).getItemName());
        assertEquals("Ozempic", manager.getInventory().get(1).getItemName());
	}

}
