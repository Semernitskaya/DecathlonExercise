package com.ol.decathlon;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class MeasurementUtil {

    public static final int CENTIMETER_MULTIPLICAND = 100;

    private MeasurementUtil(){

    }

    public static double meterToCentimeter(double meter) {
        return meter * CENTIMETER_MULTIPLICAND;
    }
}
