package com.ol.decathlon;

import com.ol.decathlon.data.ResultRecord;
import com.ol.decathlon.parameter.ParameterCache;
import com.ol.decathlon.parameter.ParameterRecord;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ol.decathlon.MeasurementUtil.meterToCentimeter;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class TotalResultCalculator {

    private final ParameterCache parameterCache;

    public TotalResultCalculator(ParameterCache parameterCache) {
        this.parameterCache = parameterCache;
    }

    public int calculateTotalResult(ResultRecord resultRecord) {
        return resultRecord.getResults()
                .entrySet()
                .stream()
                .mapToInt(entry -> {
                    EventType eventType = entry.getKey();
                    BigDecimal bigDecimal = entry.getValue();
                    final double value = eventType.isUseCentimeters() ?
                            meterToCentimeter(bigDecimal.doubleValue()) : bigDecimal.doubleValue();
                    Optional<ParameterRecord> parameterRecord = parameterCache.get(eventType);
                    return parameterRecord.map(r -> eventType.getFormula()
                            .apply(r, value))
                            .orElse(0);
                })
                .sum();
    }
}
