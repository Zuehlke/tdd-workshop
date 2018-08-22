package org.zuhlke.item;

import java.util.HashMap;
import java.util.Map;

public class ItemRepository {

    private Map<String, Item> repo;

    // Test only
    public ItemRepository() {
        this.repo = new HashMap<>();
    }

    public static ItemRepository getProductiveInstance() {
        ItemRepository itemRepository = new ItemRepository();
        // TODO: This should be read from DB. Assume this constructor is awkward!
        itemRepository.add("10101", "Wine", "6.66");
        return itemRepository;
    }

    public void add(String barcode, String name, String price) {
        repo.put(barcode, new Item(name, price, barcode));
    }

    public Item getItem(String barcode) {
        return repo.get(barcode);
    }
}
