package com.ol.decathlon;

import org.testng.annotations.Test;

import static com.ol.decathlon.MeasurementUtil.meterToCentimeter;
import static org.testng.Assert.assertEquals;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class MeasurementUtilTest {

    @Test
    public void testMeterToCentimeter() {
        assertEquals(meterToCentimeter(0), 0, 0.00001);
        assertEquals(meterToCentimeter(1.11), 111, 0.00001);
        assertEquals(meterToCentimeter(10), 1000, 0.00001);
    }
}