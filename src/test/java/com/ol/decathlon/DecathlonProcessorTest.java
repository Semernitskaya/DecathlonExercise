package com.ol.decathlon;

import com.ol.decathlon.data.Range;
import com.ol.decathlon.data.ResultRecord;
import org.assertj.core.util.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.ol.decathlon.EventType.DISTANCE_100_M;
import static com.ol.decathlon.EventType.LONG_JUMP;
import static com.ol.decathlon.ResultRecordUtil.getResultRecord;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.util.Lists.list;
import static org.testng.Assert.*;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class DecathlonProcessorTest {

    @DataProvider()
    public Object[][] getFillPlacesData() {
        return new Object[][]{
                new Object[] {list(), list()},

                new Object[] {list(getResultRecord(100)), list(new Range(1, 1))},

                new Object[] {
                        list(
                                getResultRecord(100),
                                getResultRecord(100)
                        ),
                        list(new Range(1, 2), new Range(1, 2))},

                new Object[] {
                        list(
                                getResultRecord(100),
                                getResultRecord(100),
                                getResultRecord(100)
                        ),
                        list(new Range(1, 3), new Range(1, 3), new Range(1, 3))},

                new Object[] {
                        list(
                                getResultRecord(200),
                                getResultRecord(100)
                        ),
                        list(new Range(1, 1), new Range(2, 2))},

                new Object[] {
                        list(
                                getResultRecord(300),
                                getResultRecord(200),
                                getResultRecord(100)
                        ),
                        list(new Range(1, 1), new Range(2, 2), new Range(3, 3))},

                new Object[] {
                        list(
                                getResultRecord(500),
                                getResultRecord(500),
                                getResultRecord(400),
                                getResultRecord(300),
                                getResultRecord(200),
                                getResultRecord(200),
                                getResultRecord(200),
                                getResultRecord(100),
                                getResultRecord(100)
                        ),
                        list(
                                new Range(1, 2), new Range(1, 2), new Range(3, 3),
                                new Range(4, 4), new Range(5, 7), new Range(5, 7),
                                new Range(7, 7), new Range(8, 9), new Range(8, 9)
                        )
                },

        };
    }


    @Test(dataProvider = "getFillPlacesData")
    public void testFillPlaces(List<ResultRecord> records, List<Range> expectedRanges) {
        new DecathlonProcessor().fillPlaces(records);
        for (int i = 0; i < records.size(); i++) {
            assertEquals(records.get(i).getPlaces(), expectedRanges.get(i));
        }
    }
}