package org.zuhlke;

import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;

public class BasketTest {

    private static final Currency SGD = Currency.getInstance("SGD");

    @Test
    public void getSummary_emptyBasket_0Total() {
        // Given
        Basket b = new Basket();

        // When
        String summary = b.getSummary(SGD);

        // Then
        Assert.assertEquals("Total: 0.00", summary);
    }

    @Test
    public void add_singleItemAdded_totalShowsItem() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Wine", "6.66"));

        // Then
        Assert.assertEquals("Wine: 6.66\nTotal: 6.66", b.getSummary(SGD));
    }

    @Test
    public void add_twoItemsAdded_totalShowsBothItems() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Rösti", "2.33"));
        b.add(new Item("Wine", "6.66"));

        // Then
        Assert.assertEquals("Rösti: 2.33\nWine: 6.66\nTotal: 8.99", b.getSummary(SGD));
    }

    @Test
    public void add_sameItemAddedTwice_bothItemsSummarized() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Wine", "6.66"));
        b.add(new Item("Wine", "6.66"));

        // Then
        Assert.assertEquals("2 Wine: 13.32\nTotal: 13.32", b.getSummary(SGD));
    }

    @Test
    public void remove_itemOnceInBasketRemoved_itemNoLongerOnSummary() {
        // Given
        Basket b = new Basket();
        b.add(new Item("Wine", "6.66"));

        // When
        b.remove(new Item("Wine", "6.66"));

        // Then
        Assert.assertEquals("Total: 0.00", b.getSummary(SGD));
    }

    @Test
    public void remove_itemTwiceInBasketRemoved_itemNoLongerOnSummary() {
        // Given
        Basket b = new Basket();
        Item wine = new Item("Wine", "6.66");
        b.add(wine);
        b.add(wine);

        // When
        b.remove(wine);

        // Then
        Assert.assertEquals("Wine: 6.66\nTotal: 6.66", b.getSummary(SGD));
    }

    @Test(expected = Exception.class)
    public void remove_itemNotInBasketRemoved_throws() {
        // Given
        Basket b = new Basket();

        // When
        b.remove(new Item("Wine", "6.66"));

        // Then (exception)
    }

    @Test
    public void getSummary_promoFulfilled_promoPriceShown() {
        // Given
        Item wine = new Item("Wine", "6.66");
        Promotion promotion = new Promotion(wine, 2, "10.00");
        Basket basket = new Basket();
        // Need to add at least two wines for the promotion to trigger
        basket.add(wine);
        basket.add(wine);

        // When
        String summary = basket.getSummary(SGD, promotion);

        // Then
        Assert.assertEquals("2 Wine: 10.00\nTotal: 10.00", summary);
    }

    @Test
    public void getSummary_promoNotFulfilled_ordinaryPriceShown() {
        // Given
        Item wine = new Item("Wine", "6.66");
        Promotion promotion = new Promotion(wine, 2, "10.00");
        Basket basket = new Basket();
        basket.add(wine);

        // When
        String summary = basket.getSummary(SGD, promotion);

        // Then
        Assert.assertEquals("Wine: 6.66\nTotal: 6.66", summary);
    }

    @Test
    public void getSummary_promoForWrongItem_ordinaryPriceShown() {
        // Given
        Item wine = new Item("Wine", "6.66");
        Item roesti = new Item("Rösti", "2.33");
        Promotion promotion = new Promotion(wine, 2, "10.00");
        Basket basket = new Basket();
        basket.add(roesti);

        // When
        String summary = basket.getSummary(SGD, promotion);

        // Then
        Assert.assertEquals("Rösti: 2.33\nTotal: 2.33", summary);
    }

    @Test
    public void getSummary_promoFulfilledWithLeftoverStack_promoPriceShown() {
        // Given
        Item wine = new Item("Wine", "6.66");
        Promotion promotion = new Promotion(wine, 2, "10.00");
        Basket basket = new Basket();
        basket.add(wine);
        basket.add(wine);
        basket.add(wine);

        // When
        String summary = basket.getSummary(SGD, promotion);

        // Then
        Assert.assertEquals("3 Wine: 16.66\nTotal: 16.66", summary);
    }
}
