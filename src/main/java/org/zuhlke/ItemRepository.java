package org.zuhlke;

import java.util.HashMap;
import java.util.Map;

public class ItemRepository {

    private Map<String, Item> repo = new HashMap<>();

    public void add(String barcode, String name, String price) {
        repo.put(barcode, new Item(name, price));
    }

    public Item getItem(String barcode) {
        return repo.get(barcode);
    }
}
