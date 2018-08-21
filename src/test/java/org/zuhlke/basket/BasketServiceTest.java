package org.zuhlke.basket;

import org.junit.Test;
import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zuhlke.basket.Basket.SGD;

public class BasketServiceTest {

    @Test
    public void getBasket_notFound_emptyBasketWithoutId() {
        // Given
        BasketDao dao = mock(BasketDao.class);
        BasketService basketService = new BasketService(dao);

        // When
        Basket basket = basketService.getBasket(1);

        // Then
        assertNull(basket.getId());
        assertEquals("Total: 0.00", basket.getSummary(SGD));
    }

    @Test
    public void getBasket_found_converted() {
        // Given
        BasketDao dao = mock(BasketDao.class);
        int id = 1;
        BasketPojo pojo = new BasketPojo(id, new HashMap<>());
        when(dao.getBasket(id)).thenReturn(pojo);
        BasketService basketService = new BasketService(dao);

        // When
        Basket basket = basketService.getBasket(id);

        // Then
        assertEquals(new Integer(1), basket.getId());
    }
}