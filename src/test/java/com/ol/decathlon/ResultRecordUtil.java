package com.ol.decathlon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.util.Maps.newHashMap;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class ResultRecordUtil {

    private ResultRecordUtil() {
    }

    public static ResultRecord getResultRecord(EventType eventType, double value) {
        return new ResultRecord("Name", newHashMap(eventType, BigDecimal.valueOf(value)));
    }

    public static ResultRecord getResultRecord(Map.Entry<EventType, Double>... entries) {
        Map<EventType, BigDecimal> map = new HashMap<>(entries.length);
        for (Map.Entry<EventType, Double> entry : entries) {
            map.put(entry.getKey(), BigDecimal.valueOf(entry.getValue()));
        }
        return new ResultRecord("Name", map);
    }

}
