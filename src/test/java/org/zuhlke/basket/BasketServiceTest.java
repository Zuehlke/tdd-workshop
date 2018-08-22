package org.zuhlke.basket;

import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;
import org.zuhlke.item.Item;
import org.zuhlke.item.ItemRepository;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.zuhlke.basket.Basket.SGD;

public class BasketServiceTest {

    @Test
    public void getBasket_notFound_emptyBasketWithoutId() {
        // Given
        BasketDao dao = mock(BasketDao.class);
        BasketService basketService = new BasketService(dao, new ItemRepository());

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
        BasketService basketService = new BasketService(dao, new ItemRepository());

        // When
        Basket basket = basketService.getBasket(id);

        // Then
        assertEquals(new Integer(1), basket.getId());
    }

    @Test
    public void getBasket_foundWithItems_converted() {
        // Given
        HashMap<String, Integer> itemMap = new HashMap<>();
        String barcode = "10101";
        int amount = 3;
        itemMap.put(barcode, amount);
        int id = 1;
        BasketPojo pojo = new BasketPojo(id, itemMap);
        BasketDao dao = mock(BasketDao.class);
        when(dao.getBasket(id)).thenReturn(pojo);
        ItemRepository itemRepository = new ItemRepository();
        itemRepository.add(barcode, "Wine", "6.66");
        BasketService basketService = new BasketService(dao, itemRepository);

        // When
        Basket basket = basketService.getBasket(id);

        // Then
        assertEquals(new Integer(1), basket.getId());
        assertEquals(new Integer(amount), basket.getItemMap().get(new Item("Wine", "6.66")));
    }

    @Test
    public void persistBasket_newBasket_stored() {
        // Given
        Basket basket = new Basket();
        BasketDao dao = mock(BasketDao.class);
        int assignedId = 1;
        doAnswer((Answer<Void>) invocation -> {
            BasketPojo argument = invocation.getArgument(0);
            argument.id = assignedId;
            return null;
        }).when(dao).persistBasket(any());
        BasketService basketService = new BasketService(dao, new ItemRepository());

        // When
        int id = basketService.persistBasket(basket);

        // Then
        assertEquals(assignedId, id);
    }
}