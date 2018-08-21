package org.zuhlke.scanner;

import org.zuhlke.basket.Basket;
import org.zuhlke.item.ItemRepository;

import java.util.Currency;

public class Scanner {
    private final ItemRepository repo;
    private final Basket basket;
    private static final Currency SGD = Currency.getInstance("SGD");

    public Scanner(ItemRepository repo) {
        this.repo = repo;
        this.basket = new Basket();
    }

    public String addItem(String barCode) {
        basket.add(repo.getItem(barCode));

        return basket.getSummary(SGD);
    }

    public String removeItem(String barCode) {
        basket.remove(repo.getItem(barCode));

        return basket.getSummary(SGD);
    }
}
