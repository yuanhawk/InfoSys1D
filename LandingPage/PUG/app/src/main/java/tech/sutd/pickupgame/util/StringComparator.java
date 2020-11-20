package tech.sutd.pickupgame.util;

import tech.sutd.pickupgame.R;

public class StringComparator {

    public static boolean equals(final String s1, final String s2) {
        return s1 != null && s2 != null && s1.hashCode() == s2.hashCode() && s1.equals(s2);
    }

    public static int caseImage(String s) {
        if (equals(s, "Badminton"))
            return R.drawable.ic_badminton;
        else if (equals(s, "Cycling"))
            return R.drawable.ic_cycling;
        return 0;
    }

}
