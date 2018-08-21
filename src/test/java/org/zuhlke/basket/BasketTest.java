package org.zuhlke.basket;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.zuhlke.converter.CurrencyConverter;
import org.zuhlke.item.Item;
import org.zuhlke.promotion.Promotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.zuhlke.basket.Basket.SGD;

public class BasketTest {

    @Test
    public void getSummary_emptyBasket_0Total() {
        // Given
        Basket b = new Basket();

        // When
        String summary = b.getSummary(SGD);

        // Then
        assertEquals("Total: 0.00", summary);
    }

    @Test
    public void add_singleItemAdded_totalShowsItem() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Wine", "6.66"));

        // Then
        assertEquals("Wine: 6.66\nTotal: 6.66", b.getSummary(SGD));
    }

    @Test
    public void add_twoItemsAdded_totalShowsBothItems() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Rösti", "2.33"));
        b.add(new Item("Wine", "6.66"));

        // Then
        assertEquals("Rösti: 2.33\nWine: 6.66\nTotal: 8.99", b.getSummary(SGD));
    }

    @Test
    public void add_sameItemAddedTwice_bothItemsSummarized() {
        // Given
        Basket b = new Basket();

        // When
        b.add(new Item("Wine", "6.66"));
        b.add(new Item("Wine", "6.66"));

        // Then
        assertEquals("2 Wine: 13.32\nTotal: 13.32", b.getSummary(SGD));
    }

    @Test
    public void remove_itemOnceInBasketRemoved_itemNoLongerOnSummary() {
        // Given
        Basket b = new Basket();
        b.add(new Item("Wine", "6.66"));

        // When
        b.remove(new Item("Wine", "6.66"));

        // Then
        assertEquals("Total: 0.00", b.getSummary(SGD));
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
        assertEquals("Wine: 6.66\nTotal: 6.66", b.getSummary(SGD));
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
        assertEquals("2 Wine: 10.00\nTotal: 10.00", summary);
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
        assertEquals("Wine: 6.66\nTotal: 6.66", summary);
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
        assertEquals("Rösti: 2.33\nTotal: 2.33", summary);
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
        assertEquals("3 Wine: 16.66\nTotal: 16.66", summary);
    }

    @Test
    public void getSummary_nonSGDCurrency_conversionRateApplied() {
        // Given
        CurrencyConverter converter = new CurrencyConverter() {
            @Override
            public BigDecimal getConversionRate(Currency from, Currency to) {
                return new BigDecimal("0.5");
            }
        };
        Item wine = new Item("Wine", "6.66");
        Basket basket = new Basket(converter);
        basket.add(wine);

        // When
        String summary = basket.getSummary(Currency.getInstance("USD"));

        // Then
        assertEquals("Wine: 6.66\nTotal: 6.66\nConverted to $: 3.33", summary);
    }

    @Test
    public void getSummary_nonSGDCurrency_conversionRateApplied_MockitoWhenThenReturn() {
        // Given
        Currency usd = Currency.getInstance("USD");
        CurrencyConverter converter = mock(CurrencyConverter.class);
        when(converter.getConversionRate(SGD, usd)).thenReturn(new BigDecimal("0.5"));

        Item wine = new Item("Wine", "6.66");
        Basket basket = new Basket(converter);
        basket.add(wine);

        // When
        String summary = basket.getSummary(usd);

        // Then
        assertEquals("Wine: 6.66\nTotal: 6.66\nConverted to $: 3.33", summary);
        // Can also assert mock was called (not needed here, as we are only interested in the output)
        verify(converter, atLeastOnce()).getConversionRate(SGD, usd);
    }

    @Test
    public void getSummary_nonSGDCurrency_conversionRateApplied_MockitoDoReturnWhen() {
        // Given
        Currency usd = Currency.getInstance("USD");
        CurrencyConverter converter = spy(new CurrencyConverter());
        doReturn(new BigDecimal("0.5")).when(converter).getConversionRate(SGD, usd);

        Item wine = new Item("Wine", "6.66");
        Basket basket = new Basket(converter);
        basket.add(wine);

        // When
        String summary = basket.getSummary(usd);

        // Then
        assertEquals("Wine: 6.66\nTotal: 6.66\nConverted to $: 3.33", summary);
        // Can also assert spy was called (not needed here, as we are only interested in the output)
        verify(converter, atLeastOnce()).getConversionRate(SGD, usd);
    }

    @Test
    public void getSummary_nonSGDCurrency_conversionRateApplied_MockitoArgumentCaptors() {
        // Given
        Currency usd = Currency.getInstance("USD");
        CurrencyConverter converter = mock(CurrencyConverter.class);
        when(converter.getConversionRate(SGD, usd)).thenReturn(new BigDecimal("0.5"));

        Item wine = new Item("Wine", "6.66");
        Basket basket = new Basket(converter);
        basket.add(wine);

        // When
        String summary = basket.getSummary(usd);

        // Then
        assertEquals("Wine: 6.66\nTotal: 6.66\nConverted to $: 3.33", summary);
        // Can also assert mock was called with correct arguments (not needed here, as we are only interested in the output)
        ArgumentCaptor<Currency> captor = ArgumentCaptor.forClass(Currency.class);
        List<Currency> expected = Arrays.asList(SGD, usd);
        verify(converter).getConversionRate(captor.capture(), captor.capture());
        assertEquals(expected, captor.getAllValues());
    }

}
