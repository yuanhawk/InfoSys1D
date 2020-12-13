package tech.sutd.pickupgame.util;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputEditText;

public class TextInputEditTextNoAutofill extends TextInputEditText {

    public TextInputEditTextNoAutofill(@NonNull Context context) {
        super(context);
    }

    public TextInputEditTextNoAutofill(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputEditTextNoAutofill(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_NONE;
    }
}
