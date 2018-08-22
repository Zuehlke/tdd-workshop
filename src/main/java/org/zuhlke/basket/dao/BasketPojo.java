package org.zuhlke.basket.dao;

import javax.persistence.*;
import java.util.Map;

@Entity
public class BasketPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ElementCollection
    @MapKeyColumn(name = "item")
    @Column(name = "amount")
    @CollectionTable(name = "basket_attributes", joinColumns = @JoinColumn(name = "basket_id"))
    public Map<String, Integer> itemMap; // maps from attribute name to value


    public BasketPojo() {
        // Default constructor for JPA
    }

    public BasketPojo(Integer id, Map<String, Integer> itemMap) {
        this.id = id;
        this.itemMap = itemMap;
    }
}
