package com.ol.decathlon;

import java.io.Serializable;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class Range {

    private final int min;

    private final int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
