package com.ol.decathlon;

import com.ol.csv.CsvReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class ParameterCache {

    private final static Logger LOG = Logger.getLogger(ParameterCache.class.getName());

    private Map<EventType, ParameterRecord> typeParameterRecordMap = new HashMap<>();

    public void initialize(String parameterFile) throws IOException {
        CsvReader reader = new CsvReader(parameterFile, "", true);
        reader.consumeAll(strings -> {
            Optional<ParameterRecord> parameterRecord = getParameterRecord(strings);
            if (parameterRecord.isPresent()) {
                getEventType(strings).ifPresent(
                        type -> typeParameterRecordMap.put(type, parameterRecord.get()));
            }
        });
        if (typeParameterRecordMap.size() < EventType.values().length) {
            LOG.warning(format("Expected event types count %d, actual count %d",
                    EventType.values().length,
                    typeParameterRecordMap.size()));
        }
    }

    Optional<EventType> getEventType(String[] strings) {
        if (strings.length < 1) {
            return Optional.empty();
        }
        return EventType.getByName(strings[0]);
    }

    Optional<ParameterRecord> getParameterRecord(String[] strings) {
        try {
            double a = Double.valueOf(strings[1]);
            double b = Double.valueOf(strings[2]);
            double c = Double.valueOf(strings[3]);
            return Optional.of(new ParameterRecord(a, b, c));
        } catch (NumberFormatException|ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.WARNING, "Something went wrong with parameters extracting", e);
        }
        return Optional.empty();
    }

    public Optional<ParameterRecord> get(EventType eventType) {
        return Optional.ofNullable(typeParameterRecordMap.get(eventType));
    }

    public Map<EventType, ParameterRecord> getTypeParameterRecordMap() {
        return typeParameterRecordMap;
    }
}
