package org.zuhlke.item;

import org.junit.Assert;
import org.junit.Test;

public class ItemRepositoryTest {

    @Test
    public void getItem_noItemFound_null() {
        ItemRepository repo = new ItemRepository();
        Assert.assertNull(repo.getItem("01023"));
    }

    @Test
    public void getItem_itemInserted_itemFound() {
        // Given
        ItemRepository iR = new ItemRepository();
        String barcode = "0101";
        String itemName = "ItemName";
        String price = "1.00";
        iR.add(barcode, itemName, price);
        Item item = new Item(itemName, price, barcode);

        // When
        Item retrievedItem = iR.getItem(barcode);

        // Then
        Assert.assertEquals(item, retrievedItem);
    }
}