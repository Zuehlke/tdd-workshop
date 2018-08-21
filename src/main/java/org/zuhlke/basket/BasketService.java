package org.zuhlke.basket;

import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;

public class BasketService {

    public Basket getBasket(int id) {
        // TODO implement
        BasketPojo pojo = new BasketDao().getBasket(id);
        return convert(pojo);
    }

    private Basket convert(BasketPojo pojo) {
        // TODO implement
        return null;
    }
}
