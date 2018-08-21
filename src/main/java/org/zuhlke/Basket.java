package org.zuhlke;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Map<Item, Integer> itemMap = new HashMap<>();

    public String getSummary(Promotion... promotions) {
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
        return sb.toString();
    }

    public void add(Item item) {
        Integer prevValue = itemMap.putIfAbsent(item, 1);
        if (prevValue != null) {
            itemMap.put(item, itemMap.get(item)+1);
        }
    }

    public void remove(Item item) {
        Integer currentCount = itemMap.get(item);
        if (currentCount == 1) {
            itemMap.remove(item);
        } else {
            itemMap.put(item, currentCount-1);
        }
    }
}
