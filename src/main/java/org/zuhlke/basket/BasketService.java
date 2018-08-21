package org.zuhlke.basket;

import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;

public class BasketService {

    private BasketDao dao;

    public BasketService(BasketDao dao) {
        this.dao = dao;
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
        return new Basket(pojo.id, pojo.itemMap);
    }

    public int persistBasket(Basket basket) {
        return dao.persistBasket(new BasketPojo(basket.getId(), basket.getItemMap()));
    }
}
