package com.ol.decathlon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
@XmlRootElement
public class ResultRecord implements Comparable<ResultRecord> {

    private String name;

    private Map<EventType, BigDecimal> results;

    private int totalResult;

    private Range places;

    public ResultRecord() {
    }

    public ResultRecord(String name, Map<EventType, BigDecimal> results) {
        this.name = name;
        this.results = results;
    }

    @Override
    public int compareTo(ResultRecord o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultRecord that = (ResultRecord) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Map<EventType, BigDecimal> getResults() {
        return results;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    @XmlElement
    public Range getPlaces() {
        return places;
    }

    public void setPlaces(Range places) {
        this.places = places;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ResultRecord{" +
                "name='" + name + '\'' +
                ", results=" + results +
                ", totalResult=" + totalResult +
                '}';
    }
}
