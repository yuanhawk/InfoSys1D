package tech.sutd.pickupgame.util;

import tech.sutd.pickupgame.R;

public class StringChecker {

    public static int caseImage(String s) {
        if (s != null) {
            if (s.equals("Badminton"))
                return R.drawable.ic_badminton;
            else if (s.equals("Cycling"))
                return R.drawable.ic_cycling;
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
