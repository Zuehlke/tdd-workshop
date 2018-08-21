package org.zuhlke;

import java.math.BigDecimal;

public class Promotion {
    private final Item item;
    private final int amount;
    private final String promoPrice;

    public Promotion(Item item, int amount, String promoPrice) {
        this.item = item;
        this.amount = amount;
        this.promoPrice = promoPrice;
    }

    public BigDecimal getStackPrice(Integer numberOfItems) {
        int promoApplications = numberOfItems / amount;
        int promoLeftover = numberOfItems % amount;
        BigDecimal promoCost = new BigDecimal(promoPrice).multiply(new BigDecimal(promoApplications));
        BigDecimal leftoverCost = item.getPriceBigDecimal().multiply(new BigDecimal(promoLeftover));
        return promoCost.add(leftoverCost);
    }

    public boolean matches(Item item) {
        return this.item.equals(item);
    }
}
