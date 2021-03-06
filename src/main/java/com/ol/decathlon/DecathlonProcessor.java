package com.ol.decathlon;

import com.ol.csv.CsvReader;
import com.ol.decathlon.data.Range;
import com.ol.decathlon.data.ResultRecord;
import com.ol.decathlon.data.ResultRecordsWrapper;
import com.ol.decathlon.parameter.ParameterCache;
import com.ol.decathlon.xml.SimpleXmlWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static com.ol.decathlon.EventType.*;
import static com.ol.decathlon.MeasurementUtil.minuteToSecond;
import static java.lang.String.format;
import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.logging.Level.WARNING;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class DecathlonProcessor {

    private final static Logger LOG = Logger.getLogger(DecathlonProcessor.class.getName());

    public static final String RESULTS_SEPARATOR = ";";

    public static final int RESULTS_LENGTH = 11;

    private final Comparator<ResultRecord> totalResultComparator = comparingInt(ResultRecord::getTotalResult);

    public void process(String inputFile, String outputFile, String parameterFile) {
        try {
            ParameterCache parameterCache = new ParameterCache();
            parameterCache.initialize(parameterFile);
            TotalResultCalculator calculator = new TotalResultCalculator(parameterCache);

            List<ResultRecord> resultRecords = new CsvReader(inputFile, RESULTS_SEPARATOR, false)
                    .readAll(strings -> getResultRecord(strings));

            resultRecords.forEach(record -> record.setTotalResult(calculator.calculateTotalResult(record)));
            sort(resultRecords, totalResultComparator);
            fillPlaces(resultRecords);

            new SimpleXmlWriter().writeAsXmlString(new ResultRecordsWrapper(resultRecords),
                    () -> Files.newBufferedWriter(Paths.get(outputFile)));
        } catch (IOException e) {
            LOG.log(WARNING, "Something went wrong while processing decathlon data", e);
        }
    }

    Optional<ResultRecord> getResultRecord(String[] strings) {
        if (strings.length < RESULTS_LENGTH) {
            LOG.warning(format("Can't extract results from string expected length %d, actual length %d",
                    11, strings.length));
            return Optional.empty();
        }
        String name = strings[0];
        Map<EventType, BigDecimal> results = new HashMap<>(10);
        try {
            int i = 1;
            results.put(DISTANCE_100_M, new BigDecimal(strings[i++]));
            results.put(LONG_JUMP, new BigDecimal(strings[i++]));
            results.put(SHOT_PUT, new BigDecimal(strings[i++]));
            results.put(HIGH_JUMP, new BigDecimal(strings[i++]));
            results.put(DISTANCE_400_M, new BigDecimal(strings[i++]));
            results.put(DISTANCE_110_M_HURDLES, new BigDecimal(strings[i++]));
            results.put(DISCUS_THROW, new BigDecimal(strings[i++]));
            results.put(POLE_VAULT, new BigDecimal(strings[i++]));
            results.put(JAVELIN_THROW, new BigDecimal(strings[i++]));
            results.put(DISTANCE_1500_M, getSeconds(strings[i++]));
            return Optional.of(new ResultRecord(name, results));
        } catch (Exception e) {
            LOG.log(WARNING, format("Can't extract results from string for %s", name), e);
        }
        return Optional.empty();
    }

    BigDecimal getSeconds(String str) {
        int i = str.indexOf(".");
        int minutes = Integer.parseInt(str.substring(0, i));
        return new BigDecimal(str.substring(i + 1))
                .add(new BigDecimal(minuteToSecond(minutes)));
    }

    void fillPlaces(List<ResultRecord> records) {
        int minPlace = 1;
        int startIndex = 0;
        int previousResult = -1;
        for (int i = 0; i < records.size(); i++) {
            ResultRecord record = records.get(i);
            if (record.getTotalResult() < previousResult) {
                int maxPlace = minPlace + i - startIndex - 1;
                Range places = new Range(minPlace, maxPlace);
                for (int j = startIndex; j < i; j++) {
                    records.get(j).setPlaces(places);
                }
                minPlace = maxPlace + 1;
                startIndex = i;
            }
            previousResult = record.getTotalResult();
        }

        int maxPlace = minPlace + records.size() - startIndex - 1;
        Range places = new Range(minPlace, maxPlace);
        for (int j = startIndex; j < records.size(); j++) {
            records.get(j).setPlaces(places);
        }

    }
}
