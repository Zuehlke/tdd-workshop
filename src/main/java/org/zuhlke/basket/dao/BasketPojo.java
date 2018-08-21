package org.zuhlke.basket.dao;

import org.zuhlke.item.Item;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class BasketPojo {

    @Id
    public int id;

    @ElementCollection
    @MapKeyColumn(name = "item")
    @Column(name = "amount")
    @CollectionTable(name = "basket_attributes", joinColumns = @JoinColumn(name = "basket_id"))
    Map<Item, Integer> attributes = new HashMap<>(); // maps from attribute name to value
}
