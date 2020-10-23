package tech.sutd.pickupgame.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClickState {

    public static final int NONE = 0;
    public static final int CLICKED = 1;

    @IntDef({NONE, CLICKED})
    @Retention(RetentionPolicy.SOURCE)

    public @interface State {}
}
