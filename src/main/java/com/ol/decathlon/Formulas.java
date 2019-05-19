package com.ol.decathlon;

import com.ol.decathlon.parameter.ParameterRecord;

import java.util.function.BiFunction;

import static java.lang.Math.pow;
import static java.lang.Math.round;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public interface Formulas {

    BiFunction<ParameterRecord, Double, Integer> TRACK_EVENT_FORMULA =
            (parameters, value) ->
                    (int) round(parameters.getA() * pow((parameters.getB() - value), parameters.getC()));

    BiFunction<ParameterRecord, Double, Integer> FIELD_EVENT_FORMULA =
            (parameters, value) ->
                    (int) round(parameters.getA() * pow(( value - parameters.getB()), parameters.getC()));

}
