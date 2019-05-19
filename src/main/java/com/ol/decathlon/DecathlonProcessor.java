package com.ol.decathlon;

import com.ol.csv.CsvReader;
import com.ol.xml.SimpleXmlConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static com.ol.decathlon.EventType.*;
import static java.lang.String.format;
import static java.util.Collections.sort;
import static java.util.logging.Level.WARNING;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class DecathlonProcessor {

    private final static Logger LOG = Logger.getLogger(DecathlonProcessor.class.getName());

    public void process(String inputFile, String outputFile, String parameterFile) {
        try {
            if (!isValidParameterFile(parameterFile)) {
                LOG.info("Parameter file not found or invalid, using default");
                parameterFile = "";
            }
            ParameterCache parameterCache = new ParameterCache();
            parameterCache.initialize(parameterFile);
            TotalResultCalculator calculator = new TotalResultCalculator(parameterCache);

            List<ResultRecord> resultRecords = new CsvReader(inputFile, "", false)
                    .readAll(strings -> getResultRecord(strings));
            resultRecords.forEach(record -> record.setTotalResult(calculator.calculateTotalResult(record)));
            sort(resultRecords);
            fillPlaces(resultRecords);

            String xmlString = new SimpleXmlConverter().convertToXmlString(resultRecords);
            System.out.println(xmlString);
        } catch (IOException e) {
            LOG.log(WARNING, "Something went wrong while processing decathlon data", e);
        }
    }

    Optional<ResultRecord> getResultRecord(String[] strings) {
        if (strings.length < 11) {
            LOG.warning(format("Can't extract results from string %s, invalid length", strings));
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
            results.put(DISTANCE_1500_M, new BigDecimal(strings[i++]));
            return Optional.of(new ResultRecord(name, results));
        } catch (Exception e) {
            LOG.log(WARNING, format("Can't extract results from string %s", strings), e);
        }
        return Optional.empty();
    }

    boolean isValidParameterFile(String parameterFile) {
        return parameterFile != null
                && !parameterFile.isEmpty()
                && new File(parameterFile).exists();
    }

    private static void fillPlaces(List<ResultRecord> records) {
//        for (int i = 0; i < records.size(); i++) {
//             = array[i];
//
//        }
    }
}
