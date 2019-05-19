package com.ol.decathlon;

import com.ol.decathlon.data.ResultRecord;
import com.ol.decathlon.parameter.ParameterCache;
import com.ol.decathlon.parameter.ParameterRecord;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.Optional;

import static com.ol.decathlon.EventType.*;
import static com.ol.decathlon.ResultRecordUtil.getResultRecord;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class TotalResultCalculatorTest {

    @DataProvider()
    public Object[][] getData() {
        return new Object[][]{
                new Object[] {getResultRecord(DISTANCE_100_M, 10.395), 1000},
                new Object[] {getResultRecord(DISTANCE_100_M, 11.756), 700},

                new Object[] { getResultRecord(LONG_JUMP, 7.76), 1000},
                new Object[] { getResultRecord(LONG_JUMP, 6.51), 700},

                new Object[] {getResultRecord(HIGH_JUMP, 6.51), 0},
                new Object[] {getResultRecord(DISTANCE_400_M, 6.51), 0},

                new Object[] { getResultRecord(
                        new AbstractMap.SimpleEntry<>(DISTANCE_100_M, 11.756),
                        new AbstractMap.SimpleEntry<>(LONG_JUMP, 7.76),
                        new AbstractMap.SimpleEntry<>(HIGH_JUMP, 7.76)
                ), 1700},
        };
    }

    @Test(dataProvider= "getData")
    public void testCalculateTotalResult(ResultRecord record, int expectedTotalResult) {
        ParameterCache parameterCache = mock(ParameterCache.class);
        when(parameterCache.get(LONG_JUMP))
                .thenReturn(Optional.of(new ParameterRecord(0.14354, 220, 1.4)));
        when(parameterCache.get(EventType.DISTANCE_100_M))
                .thenReturn(Optional.of(new ParameterRecord(25.4347, 18, 1.81)));

        TotalResultCalculator resultCalculator = new TotalResultCalculator(parameterCache);
        assertEquals(resultCalculator.calculateTotalResult(record), expectedTotalResult);
    }
}