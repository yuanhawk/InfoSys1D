package tech.sutd.pickupgame.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tech.sutd.pickupgame.databinding.FragmentBookingBinding;

public class DateConverter {

    public static String clockConverter(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, ha", Locale.getDefault());
        return sdf.format(new Date(Long.parseLong(s)));
    }

    public static String endClockConverter(String s) {
        SimpleDateFormat sdfEnd = new SimpleDateFormat("ha", Locale.getDefault());
        return sdfEnd.format(new Date(Long.parseLong(s)));
    }

    @SuppressLint("DefaultLocale")
    public static String timeConverter(FragmentBookingBinding binding, int hour, int min) {
        String time;
        if (hour < 12)
            time = "AM";
        else if (hour == 12)
            time = "PM";
        else {
            time = "PM";
            hour -= 12;
        }

        String timeFormat;
        if (min < 10) {
            timeFormat = String.format("%d : 0%d %s", hour, min, time);
            binding.timeSpinner.setText(timeFormat);
        } else {
            timeFormat = String.format("%d : %d %s", hour, min, time);
            binding.timeSpinner.setText(timeFormat);
        }
        return timeFormat;
    }
}
