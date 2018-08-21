package org.zuhlke;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final String itemName;
    private final BigDecimal priceBigDecimal;

    public Item(String itemName, String price) {

        this.itemName = itemName;
        this.priceBigDecimal = new BigDecimal(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemName, item.itemName) &&
                Objects.equals(priceBigDecimal, item.priceBigDecimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, priceBigDecimal);
    }

    public String getName() {
        return itemName;
    }

    public BigDecimal getPriceBigDecimal() {
        return priceBigDecimal;
    }
}
