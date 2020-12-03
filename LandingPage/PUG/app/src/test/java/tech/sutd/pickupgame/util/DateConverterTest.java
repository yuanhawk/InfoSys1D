package tech.sutd.pickupgame.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class DateConverterTest {

    @Test
    public void convertStartClock() {
        assertEquals(DateConverter.clockConverter("1606965036000"), "03 Dec, 11:10AM");
    }

    @Test
    public void convertEndClock() {
        assertEquals(DateConverter.endClockConverter("1606965212000"), "11:13AM");
    }

    @Test
    public void timeConverter() {
        assertEquals(DateConverter.timeConverter(5, 10), "05 : 10 AM");
    }

    @Test
    public void formatDate() {
        assertEquals(DateConverter.epochConverter("01/03/2021", "5", "10"), "1614546600000");
    }

}