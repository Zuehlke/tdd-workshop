package org.zuhlke.basket.dao;

import org.zuhlke.item.Item;

import javax.persistence.*;
import java.util.Map;

@Entity
public class BasketPojo {
    @Id
    public Integer id;

    @ElementCollection
    @MapKeyColumn(name = "item")
    @Column(name = "amount")
    @CollectionTable(name = "basket_attributes", joinColumns = @JoinColumn(name = "basket_id"))
    public Map<Item, Integer> itemMap; // maps from attribute name to value

    public BasketPojo(Integer id, Map<Item, Integer> itemMap) {
        this.id = id;
        this.itemMap = itemMap;
    }
}
