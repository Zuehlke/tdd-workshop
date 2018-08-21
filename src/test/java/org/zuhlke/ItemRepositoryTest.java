package org.zuhlke;

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
        ItemRepository iR = new ItemRepository();
        iR.add("0101", "ItemName", "1.00");

        Item item = new Item("ItemName", "1.00");
        Assert.assertEquals(item, iR.getItem("0101"));
    }
}