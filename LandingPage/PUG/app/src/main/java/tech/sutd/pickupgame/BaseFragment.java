package tech.sutd.pickupgame;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            disableAutoFill();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void disableAutoFill() {

        Window window = getActivity().getWindow();
        if (window != null)
            window.getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
    }

}
