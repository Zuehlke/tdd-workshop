package org.zuhlke.item;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final String itemName;
    private final BigDecimal priceBigDecimal;
    private String barcode;

    public Item(String itemName, String price, String barcode) {
        this.itemName = itemName;
        this.priceBigDecimal = new BigDecimal(price);
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemName, item.itemName) &&
                Objects.equals(priceBigDecimal, item.priceBigDecimal) &&
                Objects.equals(barcode, item.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, priceBigDecimal, barcode);
    }

    public String getName() {
        return itemName;
    }

    public BigDecimal getPriceBigDecimal() {
        return priceBigDecimal;
    }

    public String getBarcode() {
        return barcode;
    }
}
