package org.zuhlke.basket;

import org.zuhlke.basket.dao.BasketDao;
import org.zuhlke.basket.dao.BasketPojo;
import org.zuhlke.item.Item;
import org.zuhlke.item.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasketService {

    private BasketDao dao;
    private ItemRepository itemRepository;
    private EntityManager em;

    public BasketService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example");
        em = emf.createEntityManager();
        this.dao = new BasketDao(em);
        this.itemRepository = ItemRepository.getProductiveInstance();
    }

    // Test only
    BasketService(EntityManager em, BasketDao dao, ItemRepository itemRepository) {
        this.em = em;
        this.dao = dao;
        this.itemRepository = itemRepository;
    }

    public List<Basket> getBaskets() {
        List<BasketPojo> baskets = dao.getBaskets();
        return baskets.stream().map(this::convert).collect(Collectors.toList());
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
        em.getTransaction().begin();

        Map<String, Integer> barcodeMap = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : basket.getItemMap().entrySet()) {
            barcodeMap.put(entry.getKey().getBarcode(), entry.getValue());
        }
        BasketPojo basketPojo = new BasketPojo(basket.getId(), barcodeMap);
        dao.persistBasket(basketPojo);

        em.getTransaction().commit();

        return basketPojo.id;
    }

    public void deleteBasket(int id) {
        em.getTransaction().begin();

        dao.deleteBasket(id);

        em.getTransaction().commit();
    }
}
