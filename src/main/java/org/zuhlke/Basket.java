package org.zuhlke;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Map<Item, Integer> itemMap = new HashMap<>();

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        BigDecimal sum = new BigDecimal("0.00");
        for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
            BigDecimal itemStackPrice = entry.getKey().getPriceBigDecimal().multiply(new BigDecimal(entry.getValue()));
            sum = sum.add(itemStackPrice);
            if (entry.getValue() > 1) {
                sb.append(entry.getValue()).append(" ");
            }
            sb.append(entry.getKey().getName())
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
