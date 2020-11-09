package tech.sutd.pickupgame.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static String clockConverter(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, ha", Locale.getDefault());
        return sdf.format(new Date(Long.parseLong(s)));
    }

    public static String endClockConverter(String s) {
        SimpleDateFormat sdfEnd = new SimpleDateFormat("ha", Locale.getDefault());
        return sdfEnd.format(new Date(Long.parseLong(s)));
    }
}
