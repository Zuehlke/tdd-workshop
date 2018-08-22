package org.zuhlke.mapping;

import org.eclipse.persistence.oxm.annotations.XmlVariableNode;
import org.zuhlke.item.Item;
import org.zuhlke.item.ItemRepository;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapAdapter.AdaptedMap, Map<Item, Integer>> {

    @Override
    public AdaptedMap marshal(Map<Item, Integer> map) {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<Item, Integer> entry : map.entrySet()) {
            AdaptedEntry adaptedEntry = new AdaptedEntry();
            adaptedEntry.key = entry.getKey().getBarcode();
            adaptedEntry.value = entry.getValue();
            adaptedMap.entries.add(adaptedEntry);
        }
        return adaptedMap;
    }

    @Override
    public Map<Item, Integer> unmarshal(AdaptedMap adaptedMap) {
        ItemRepository itemRepository = ItemRepository.getProductiveInstance();
        List<AdaptedEntry> adaptedEntries = adaptedMap.entries;
        Map<Item, Integer> map = new HashMap<>(adaptedEntries.size());
        for (AdaptedEntry adaptedEntry : adaptedEntries) {
            map.put(itemRepository.getItem(adaptedEntry.key), adaptedEntry.value);
        }
        return map;
    }

    public static class AdaptedMap {
        @XmlVariableNode("key")
        List<AdaptedEntry> entries = new ArrayList<>();
    }

    public static class AdaptedEntry {
        @XmlTransient
        public String key;

        @XmlValue
        public Integer value;
    }

}

