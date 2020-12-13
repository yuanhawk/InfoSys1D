package tech.sutd.pickupgame.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentBookingBinding;

public class DateConverter {

    public static String clockConverter(String s) {
        if (s == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, h:mma", Locale.getDefault());
        return sdf.format(new Date(Long.parseLong(s)));
    }

    public static String endClockConverter(String s) {
        if (s == null)
            return "";
        SimpleDateFormat sdfEnd = new SimpleDateFormat("h:mma", Locale.getDefault());
        return sdfEnd.format(new Date(Long.parseLong(s)));
    }

    @SuppressLint("DefaultLocale")
    public static String timeConverter(int hour, int min) {
        String time;
        if (hour < 12)
            time = "AM";
        else if (hour == 12)
            time = "PM";
        else {
            time = "PM";
            hour -= 12;
        }


        String hourStr = checkString(hour);
        String minStr = checkString(min);

        return String.format("%s : %s %s", hourStr, minStr, time);
    }

    public static String formatDate(int day, int month, int year) {

        String dayStr = checkString(day);
        String monthStr = checkString(month);

        return dayStr + "/" + monthStr + "/" + year;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String epochConverter(String date, String hour, String min) {
        if (hour.length() == 1)
            hour = "0" + hour;

        if (min.length() == 1)
            min = "0" + min;

        String timeDateStr = String.format("%s %s:%s", date, hour, min);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dt = LocalDateTime.parse(timeDateStr, dtf);

        return String.valueOf(dt.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
    }

    private static String checkString(int val) {
        String str;
        if (val < 10)
            str = "0" + val;
        else
            str = String.valueOf(val);
        return str;
    }
}
