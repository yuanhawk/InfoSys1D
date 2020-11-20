package tech.sutd.pickupgame.util;

import tech.sutd.pickupgame.R;

public class StringComparator {

    public static int caseImage(String s) {
        if (s.equals("Badminton"))
            return R.drawable.ic_badminton;
        else if (s.equals("Cycling"))
            return R.drawable.ic_cycling;
        return 0;
    }

}
