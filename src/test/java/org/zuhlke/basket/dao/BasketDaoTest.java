package org.zuhlke.basket.dao;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasketDaoTest {

    private static final String BARCODE = "10101";
    private EntityManager em;

    @Before
    public void setup() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example");
        em = emf.createEntityManager();
    }

    @Test
    public void getBasket_notFound_null() {
        // Given
        BasketDao basketDao = new BasketDao(em);

        // When
        BasketPojo basket = basketDao.getBasket(1);

        // Then
        assertNull(basket);
    }

    @Test
    public void persist_ordinaryPojo_noException() {
        // Given
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        BasketDao basketDao = new BasketDao(em);
        BasketPojo basketPojo = new BasketPojo(null, new HashMap<>());

        // When
        basketDao.persistBasket(basketPojo);

        // Then (PoJo ID was assigned)
        assertEquals(new Integer(1), basketPojo.id);
        tx.rollback();
    }

    @Test
    public void getBasket_found_correctObjectReturned() {
        // Given
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        BasketDao basketDao = new BasketDao(em);
        BasketPojo initialPojo = getSingleItemBasketPojo();
        basketDao.persistBasket(initialPojo);

        // When
        BasketPojo restoredPojo = basketDao.getBasket(initialPojo.id);

        // Then
        assertEquals(new Integer(1), restoredPojo.itemMap.get(BARCODE));
        tx.rollback();
    }

    private BasketPojo getSingleItemBasketPojo() {
        HashMap<String, Integer> itemMap = new HashMap<>();
        itemMap.put(BARCODE, 1);
        return new BasketPojo(null, itemMap);
    }

    @Test
    public void persist_updatePojo_noException() {
        // Given
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        BasketDao basketDao = new BasketDao(em);
        BasketPojo pojo = getSingleItemBasketPojo();
        basketDao.persistBasket(pojo);
        pojo.itemMap.put("AnotherBarcode", 5);

        // When
        basketDao.persistBasket(pojo);

        // Then (no exception)
        tx.rollback();
    }
}