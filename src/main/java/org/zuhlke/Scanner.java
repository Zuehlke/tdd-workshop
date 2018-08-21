package org.zuhlke;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Scanner {
    private final ItemRepository repo;
    private final Basket basket;

    public Scanner(ItemRepository repo) {
        this.repo = repo;
        this.basket = new Basket();
    }

    public String addItem(String barCode) {
        basket.add(repo.getItem(barCode));

        return basket.getSummary();
    }

    public String removeItem(String barCode) {
        basket.remove(repo.getItem(barCode));

        return basket.getSummary();
    }
}
