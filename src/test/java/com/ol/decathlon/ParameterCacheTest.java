package com.ol.decathlon;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.ol.decathlon.EventType.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.testng.Assert.*;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class ParameterCacheTest {

    public static final double DELTA = 0.0000001;

    private ParameterCache parameterCache;

    @BeforeMethod
    public void init() {
        parameterCache = new ParameterCache();
    }

    @DataProvider()
    public Object[][] getEventTypeData() {
        return new Object[][]{
                new Object[] {new String[]{""}, empty()},
                new Object[] {new String[]{}, empty()},
                new Object[] {new String[]{"", "", ""}, empty()},
                new Object[] {new String[]{"", "Long jump", ""}, empty()},
                new Object[] {new String[]{"Long jump"}, of(LONG_JUMP)},
                new Object[] {new String[]{"Long jump", "100 m"}, of(LONG_JUMP)},
                new Object[] {new String[]{"Long jump", "", ""}, of(LONG_JUMP)},
                new Object[] {new String[]{"100 m"}, of(DISTANCE_100_M)},
        };
    }

    @DataProvider()
    public Object[][] getParameterRecordData() {
        return new Object[][]{
                new Object[] {new String[]{""}, empty()},
                new Object[] {new String[]{}, empty()},
                new Object[] {new String[]{"", "", "", ""}, empty()},
                new Object[] {new String[]{"", "1.1", "2.22", "not a number"}, empty()},
                new Object[] {new String[]{"", "1.1", "2.22", ""}, empty()},
                new Object[] {new String[]{"", "", "2.22", "3.333"}, empty()},
                new Object[] {new String[]{"", "1.1", "2.22", "3.333"},
                        of(new ParameterRecord(1.1, 2.22, 3.333))},
        };
    }

    @Test(dataProvider= "getEventTypeData")
    public void testGetEventType(String[] strings, Optional<EventType> expectedEventType) {
        assertEquals(parameterCache.getEventType(strings), expectedEventType);
    }


    @Test(dataProvider= "getParameterRecordData")
    public void testGetParameterRecord(String[] strings, Optional<ParameterRecord> expectedParameterRecord) {
        Optional<ParameterRecord> actualParameterRecord = parameterCache.getParameterRecord(strings);
        if (expectedParameterRecord.isPresent()) {
            assertTrue(actualParameterRecord.isPresent());
            assertEquals(actualParameterRecord.get().getA(), expectedParameterRecord.get().getA(), DELTA);
            assertEquals(actualParameterRecord.get().getB(), expectedParameterRecord.get().getB(), DELTA);
            assertEquals(actualParameterRecord.get().getC(), expectedParameterRecord.get().getC(), DELTA);
        } else {
            assertFalse(actualParameterRecord.isPresent());
        }
    }

    @Test
    public void testGet() {
        parameterCache.getTypeParameterRecordMap().put(DISTANCE_100_M, new ParameterRecord(2, 300, 1));
        parameterCache.getTypeParameterRecordMap().put(LONG_JUMP, new ParameterRecord(2, 300, 1));

        assertTrue(parameterCache.get(LONG_JUMP).isPresent());
        assertTrue(parameterCache.get(DISTANCE_100_M).isPresent());

        assertFalse(parameterCache.get(DISTANCE_400_M).isPresent());
        assertFalse(parameterCache.get(null).isPresent());
    }
}