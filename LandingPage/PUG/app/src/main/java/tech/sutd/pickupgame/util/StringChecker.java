package tech.sutd.pickupgame.util;

import tech.sutd.pickupgame.R;

public class StringChecker {

    public static int caseImage(String s) {
        if (s != null) {
            switch (s) {
                case "Badminton":
                    return R.drawable.ic_badminton;
                case "Cycling":
                    return R.drawable.ic_cycling;
                case "Table Tennis":
                    return R.drawable.ic_ping_pong;
                case "Volleyball":
                    return R.drawable.ic_voll;
                case "Running":
                    return R.drawable.ic_running;
            }
            return 0;
        }
        return 0;
    }

    public static String partHolder(String s1, String s2) {
        return s1 + " | " + s2;
    }

    public static String add(String integer) {
        return String.valueOf(Integer.parseInt(integer) + 1);
    }

    public static String sub(String integer) {
        return String.valueOf(Integer.parseInt(integer) - 1);
    }
}
