package org.zuhlke.basket.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BasketDao {
    private EntityManager em;

    public BasketDao(EntityManager em) {
        this.em = em;
    }

    public List<BasketPojo> getBaskets() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BasketPojo> cq = cb.createQuery(BasketPojo.class);
        Root<BasketPojo> rootEntry = cq.from(BasketPojo.class);
        CriteriaQuery<BasketPojo> all = cq.select(rootEntry);
        TypedQuery<BasketPojo> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public BasketPojo getBasket(int id) {
        return em.find(BasketPojo.class, id);
    }

    public void persistBasket(BasketPojo basketPojo) {
        em.persist(basketPojo);
    }

    public void deleteBasket(int id) {
        em.remove(getBasket(id));
    }
}
