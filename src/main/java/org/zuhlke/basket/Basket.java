package org.zuhlke.basket;

import org.zuhlke.converter.CurrencyConverter;
import org.zuhlke.item.Item;
import org.zuhlke.promotion.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    public static final Currency SGD = Currency.getInstance("SGD");
    private Integer id;
    private Map<Item, Integer> itemMap = new HashMap<>();
    private CurrencyConverter converter;

    public Basket() {
        this(new CurrencyConverter());
        this.id = null;
        this.itemMap = new HashMap<>();
    }

    Basket(Integer id, Map<Item, Integer> itemMap) {
        this(new CurrencyConverter());
        this.id = id;
        this.itemMap = itemMap;
    }

    // For tests
    Basket(CurrencyConverter converter) {
        this.converter = converter;
    }

    public String getSummary(Currency targetCurrency, Promotion... promotions) {
        StringBuilder sb = new StringBuilder();
        BigDecimal sum = new BigDecimal("0.00");

        for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
            Item item = entry.getKey();
            Integer numberOfItems = entry.getValue();

            boolean anyMatches = false;
            BigDecimal itemStackPrice = null;
            for (Promotion promotion : promotions) {
                if (promotion.matches(item)) {
                    anyMatches = true;
                    itemStackPrice = promotion.getStackPrice(numberOfItems);
                }
            }
            if (!anyMatches) {
                itemStackPrice = item.getPriceBigDecimal().multiply(new BigDecimal(numberOfItems));
            }

            sum = sum.add(itemStackPrice);
            if (numberOfItems > 1) {
                sb.append(numberOfItems).append(" ");
            }
            sb.append(item.getName())
                    .append(": ")
                    .append(itemStackPrice.toPlainString())
                    .append("\n");
        }

        sb.append("Total: ")
                .append(sum.toPlainString());

        if (!targetCurrency.equals(SGD)) {
            sb.append("\nConverted to ")
                    .append(targetCurrency.getSymbol())
                    .append(": ")
                    .append(sum.multiply(converter.getConversionRate(SGD, targetCurrency)).setScale(2, RoundingMode.DOWN));
        }

        return sb.toString();
    }

    public void add(Item item) {
        Integer prevValue = itemMap.putIfAbsent(item, 1);
        if (prevValue != null) {
            itemMap.put(item, itemMap.get(item) + 1);
        }
    }

    public void remove(Item item) {
        Integer currentCount = itemMap.get(item);
        if (currentCount == 1) {
            itemMap.remove(item);
        } else {
            itemMap.put(item, currentCount - 1);
        }
    }

    Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public Integer getId() {
        return id;
    }

    // Need setter for JSON serialization
    public void setId(Integer id) {
        this.id = id;
    }
}
