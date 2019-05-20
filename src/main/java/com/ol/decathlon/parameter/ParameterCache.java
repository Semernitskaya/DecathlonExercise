package com.ol.decathlon.parameter;

import com.ol.csv.CsvReader;
import com.ol.decathlon.EventType;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class ParameterCache {

    private final static Logger LOG = Logger.getLogger(ParameterCache.class.getName());

    public static final String PARAMETER_SEPARATOR = ";";

    private Map<EventType, ParameterRecord> typeParameterRecordMap = new HashMap<>();

    public void initialize(String parameterFile) throws IOException {
        CsvReader reader = isValidParameterFile(parameterFile) ?
                new CsvReader(parameterFile, PARAMETER_SEPARATOR, true) :
                new CsvReader(getInputStreamSupplier(), PARAMETER_SEPARATOR, true);
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

    private Supplier<InputStream> getInputStreamSupplier() {
        LOG.info("Parameter file not found or invalid, using default");
        return () -> ClassLoader.class.getResourceAsStream("/parameters.csv");
    }

    boolean isValidParameterFile(String parameterFile) {
        return parameterFile != null
                && !parameterFile.isEmpty()
                && new File(parameterFile).exists();
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
