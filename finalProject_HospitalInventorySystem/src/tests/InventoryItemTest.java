package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.InventoryItem;

class InventoryItemTest {
	InventoryItem item1 = new InventoryItem("Ozempic", 5, LocalDate.of(2026, 5, 23), "Diabetic Medicine");
	
	@Test
	void testDisplayItem() {
		assertEquals("Item Name: Ozempic\nItem Quantity: 5\nItem Category: Diabetic Medicine\nExpiration Date: 23-May-2026",
				item1.displayItem());
	}
	
	@Test
    void testFormatStringSingleWord() {
        //Single word, expects proper capitalization
        assertEquals("Ozempic", item1.formatString("ozempic"));
    }

    @Test
    void testFormatStringTwoWords() {
        //Two words, expects proper formatting
        assertEquals("Diabetic Medicine", item1.formatString("diabetic meDicine"));
    }

}
