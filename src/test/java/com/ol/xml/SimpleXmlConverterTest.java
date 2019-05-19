package com.ol.xml;

import com.ol.decathlon.Range;
import com.ol.decathlon.ResultRecord;
import org.testng.annotations.Test;

import java.util.AbstractMap;

import static com.ol.decathlon.EventType.DISTANCE_100_M;
import static com.ol.decathlon.EventType.HIGH_JUMP;
import static com.ol.decathlon.EventType.LONG_JUMP;
import static com.ol.decathlon.ResultRecordUtil.getResultRecord;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class SimpleXmlConverterTest {


    @Test
    public void testConvertToXmlString() {
        ResultRecord record = getResultRecord(
                new AbstractMap.SimpleEntry<>(DISTANCE_100_M, 11.756),
                new AbstractMap.SimpleEntry<>(LONG_JUMP, 7.76),
                new AbstractMap.SimpleEntry<>(HIGH_JUMP, 7.76)
        );
        record.setTotalResult(1700);
        record.setPlaces(new Range(1, 1));
        String xmlString = new SimpleXmlConverter().convertToXmlString(record);
        System.out.println(xmlString);
    }
}