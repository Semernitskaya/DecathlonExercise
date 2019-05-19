package com.ol.decathlon.data;

import com.ol.decathlon.EventType;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"name", "totalResult", "placesStr", "results"})
public class ResultRecord {

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

    @XmlElement
    public void setResults(Map<EventType, BigDecimal> results) {
        this.results = results;
    }

    public int getTotalResult() {
        return totalResult;
    }

    @XmlElement
    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public Range getPlaces() {
        return places;
    }

    @XmlTransient
    public void setPlaces(Range places) {
        this.places = places;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getPlacesStr() {
        return places.isEmpty() ?
                String.valueOf(places.getMin()) : places.getMin() + "-" + places.getMax();
    }

    @XmlElement(name = "place")
    public void setPlacesStr(String placesStr) {
        throw new UnsupportedOperationException();
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
