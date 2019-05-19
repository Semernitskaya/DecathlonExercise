package com.ol.decathlon;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class MeasurementUtil {

    public static final int CENTIMETER_IN_METER = 100;

    public static final int SECOND_IN_MINUTE =60;

    private MeasurementUtil(){

    }

    public static double meterToCentimeter(double meter) {
        return meter * CENTIMETER_IN_METER;
    }

    public static int minuteToSecond(int minute) {
        return minute * SECOND_IN_MINUTE;
    }
}
