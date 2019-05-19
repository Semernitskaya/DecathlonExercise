package com.ol.decathlon;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.ol.decathlon.Formulas.FIELD_EVENT_FORMULA;
import static com.ol.decathlon.Formulas.TRACK_EVENT_FORMULA;
import static java.util.Arrays.stream;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public enum EventType {

    DISTANCE_100_M("100 m", TRACK_EVENT_FORMULA, false),
    LONG_JUMP("Long jump", FIELD_EVENT_FORMULA, true),
    SHOT_PUT("Shot put", FIELD_EVENT_FORMULA, false),
    HIGH_JUMP("High jump", FIELD_EVENT_FORMULA, true),
    DISTANCE_400_M("400 m", TRACK_EVENT_FORMULA, false),
    DISTANCE_110_M_HURDLES("110 m hurdles", TRACK_EVENT_FORMULA, false),
    DISCUS_THROW("Discus throw", FIELD_EVENT_FORMULA, false),
    POLE_VAULT("Pole vault", FIELD_EVENT_FORMULA, true),
    JAVELIN_THROW("Javelin throw", FIELD_EVENT_FORMULA, false),
    DISTANCE_1500_M("1500 m", TRACK_EVENT_FORMULA, false);

    EventType(String name, BiFunction<ParameterRecord, Double, Integer> formula, boolean useCentimeters) {
        this.name = name;
        this.formula = formula;
        this.useCentimeters = useCentimeters;
    }

    private final String name;

    private final BiFunction<ParameterRecord, Double, Integer> formula;

    private final boolean useCentimeters;

    public static Optional<EventType> getByName(String name) {
        return stream(values())
                .filter(type -> Objects.equals(type.getName(), name))
                .findAny();
    }

    public String getName() {
        return name;
    }

    public BiFunction<ParameterRecord, Double, Integer> getFormula() {
        return formula;
    }

    public boolean isUseCentimeters() {
        return useCentimeters;
    }
}
