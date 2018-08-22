package org.zuhlke.basket;

import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;
import org.zuhlke.item.Item;
import org.zuhlke.item.ItemRepository;

import java.util.HashMap;
import java.util.Map;

public class BasketService {

    private BasketDao dao;
    private ItemRepository itemRepository;

    public BasketService(BasketDao dao, ItemRepository itemRepository) {
        this.dao = dao;
        this.itemRepository = itemRepository;
    }

    public Basket getBasket(int id) {
        BasketPojo pojo = dao.getBasket(id);
        if (pojo == null) {
            // Not found, return empty basket
            return new Basket();
        }
        return convert(pojo);
    }

    private Basket convert(BasketPojo pojo) {
        Map<String, Integer> barcodeMap = pojo.itemMap;
        HashMap<Item, Integer> itemMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : barcodeMap.entrySet()) {
            itemMap.put(itemRepository.getItem(entry.getKey()), entry.getValue());
        }

        return new Basket(pojo.id, itemMap);
    }

    public int persistBasket(Basket basket) {
        // TODO: This implementation is not yet finished (Basket ItemMap is not mapped to BasketPojo barcodeMap). Why not do a refactoring Kata on that?
        BasketPojo basketPojo = new BasketPojo(basket.getId(), new HashMap<>());
        dao.persistBasket(basketPojo);

        return basketPojo.id;
    }
}
