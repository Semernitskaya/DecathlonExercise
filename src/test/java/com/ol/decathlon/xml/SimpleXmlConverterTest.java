package com.ol.decathlon.xml;

import com.ol.decathlon.data.Range;
import com.ol.decathlon.data.ResultRecord;
import com.ol.decathlon.data.ResultRecordsWrapper;
import org.assertj.core.util.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractMap;

import static com.ol.decathlon.EventType.DISTANCE_100_M;
import static com.ol.decathlon.EventType.LONG_JUMP;
import static com.ol.decathlon.ResultRecordUtil.getResultRecord;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
//TODO: different line endings !!!
public class SimpleXmlConverterTest {

    public static final String EXPECTED_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<resultRecords>\n" +
            "    <resultRecord>\n" +
            "        <name>Name</name>\n" +
            "        <totalResult>1700</totalResult>\n" +
            "        <place>1</place>\n" +
            "        <results>\n" +
            "            <entry>\n" +
            "                <key>DISTANCE_100_M</key>\n" +
            "                <value>11.756</value>\n" +
            "            </entry>\n" +
            "        </results>\n" +
            "    </resultRecord>\n" +
            "    <resultRecord>\n" +
            "        <name>Name</name>\n" +
            "        <totalResult>1500</totalResult>\n" +
            "        <place>2-4</place>\n" +
            "        <results>\n" +
            "            <entry>\n" +
            "                <key>LONG_JUMP</key>\n" +
            "                <value>7.76</value>\n" +
            "            </entry>\n" +
            "        </results>\n" +
            "    </resultRecord>\n" +
            "</resultRecords>";

    @Test
    public void testConvertToXmlString() {
        ResultRecord record1 = getResultRecord(
                new AbstractMap.SimpleEntry<>(DISTANCE_100_M, 11.756)
        );
        record1.setTotalResult(1700);
        record1.setPlaces(new Range(1, 1));

        ResultRecord record2 = getResultRecord(
                new AbstractMap.SimpleEntry<>(LONG_JUMP, 7.76)
        );
        record2.setTotalResult(1500);
        record2.setPlaces(new Range(2, 4));
        ResultRecordsWrapper resultRecordsWrapper = new ResultRecordsWrapper(Lists.list(record1, record2));
        String actualString = new SimpleXmlConverter().convertToXmlString(resultRecordsWrapper);
        Assert.assertEquals(prepareString(actualString), prepareString(EXPECTED_STRING));
    }

    String prepareString(String actualString) {
        return actualString.replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .replaceAll("\\s+", "");
    }
}