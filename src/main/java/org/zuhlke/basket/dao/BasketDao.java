package org.zuhlke.basket.dao;

import javax.persistence.EntityManager;

public class BasketDao {
    private EntityManager em;

    public BasketDao(EntityManager em) {
        this.em = em;
    }

    public BasketPojo getBasket(int id) {
        return em.find(BasketPojo.class, id);
    }

    public void persistBasket(BasketPojo basketPojo) {
        em.persist(basketPojo);
    }
}
