package org.zuhlke;

import org.junit.Assert;
import org.junit.Test;

public class BasketTest {

    @Test
    public void getSummary_emptyBasket_0Total() {
        // Given
        Basket b = new Basket();

        // When
        String summary = b.getSummary();

        // Then
        Assert.assertEquals("Total: 0.00", summary);
    }

    @Test
    public void getList_addItem_firstEntryAndTotal() {
        Basket b = new Basket();

        b.add(new Item("Wine", "6.66"));
        Assert.assertEquals("Wine: 6.66\nTotal: 6.66", b.getSummary());
    }

    @Test
    public void getList_addTwoItem_allEntriesAndTotal() {
        Basket b = new Basket();

        b.add(new Item("Rösti", "2.33"));
        b.add(new Item("Wine", "6.66"));
        Assert.assertEquals("Rösti: 2.33\nWine: 6.66\nTotal: 8.99", b.getSummary());
    }

    @Test
    public void getList_addSameItemTwice_itemsTotaled() {
        Basket b = new Basket();

        b.add(new Item("Wine", "6.66"));
        b.add(new Item("Wine", "6.66"));
        Assert.assertEquals("2 Wine: 13.32\nTotal: 13.32", b.getSummary());
    }

    @Test
    public void getList_addAndRemoveItem_empty() {
        Basket b = new Basket();
        ItemRepository iR = new ItemRepository();
        iR.add("01002", "Wine", "6.66");

        // TODO: Refactor design here
        b.add(iR.getItem("01002"));
        b.remove(iR.getItem("01002"));
        Assert.assertEquals("Total: 0.00", b.getSummary());
    }

    @Test(expected = Exception.class)
    public void getList_removeItem_error() {
        Basket b = new Basket();
        ItemRepository iR = new ItemRepository();
        iR.add("01002", "Wine", "6.66");

        // TODO: Refactor design here
        b.remove(iR.getItem("01002"));
    }

    @Test(expected = Exception.class)
    public void getList_discount_discountedPrice() {
        Basket b = new Basket();
        ItemRepository iR = new ItemRepository();
        iR.add("01002", "Wine", "6.66");

        // TODO: Refactor design here
        b.add(iR.getItem("01002"));
        b.add(iR.getItem("01002"));
        b.add(iR.getItem("01002"));
        Assert.assertEquals("3 Wine: 16.65\nTotal: 16.65", b.getSummary());
    }
}
