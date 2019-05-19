package com.ol.decathlon;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.ol.decathlon.EventType.DISTANCE_100_M;
import static com.ol.decathlon.EventType.LONG_JUMP;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.testng.Assert.assertEquals;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class EventTypeTest {

    @DataProvider()
    public Object[][] getData() {
        return new Object[][]{
                new Object[] {"", empty()},
                new Object[] {null, empty()},
                new Object[] {"Long jump", of(LONG_JUMP)},
                new Object[] {"100 m", of(DISTANCE_100_M)},
        };
    }

    @Test(dataProvider="getData")
    public void testGetByName(String name, Optional<EventType> expectedEventType) throws Exception {
        assertEquals(EventType.getByName(name), expectedEventType);
    }
}