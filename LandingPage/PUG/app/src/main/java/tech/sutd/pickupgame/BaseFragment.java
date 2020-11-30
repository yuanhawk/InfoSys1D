package tech.sutd.pickupgame;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Objects;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.data.worker.YourActivitiesWorker;

public class BaseFragment extends DaggerFragment {

    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            disableAutoFill();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void disableAutoFill() {

        Window window = requireActivity().getWindow();
        if (window != null)
            window.getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
    }

    public NavController getNavController() {
        return navController;
    }

    public Dialog setDialog(int layout) {
        Dialog dialog = new Dialog(getContext());

        dialog.setContentView(layout);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        return dialog;
    }

    public void pull() {
        OneTimeWorkRequest upcomingRequest = new OneTimeWorkRequest.Builder(UpcomingActivitiesWorker.class)
                .build();

        OneTimeWorkRequest newRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .build();

        OneTimeWorkRequest yourRequest = new OneTimeWorkRequest.Builder(YourActivitiesWorker.class)
                .build();

        WorkManager.getInstance(requireContext())
                .beginWith(newRequest)
                .then(upcomingRequest)
                .then(yourRequest)
                .enqueue();
    }

    public void pullNewAct() {
        OneTimeWorkRequest newRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .build();

        WorkManager.getInstance(requireContext())
                .enqueue(newRequest);
    }

    public void pullUpcomingAct() {
        OneTimeWorkRequest upcomingRequest = new OneTimeWorkRequest.Builder(UpcomingActivitiesWorker.class)
                .build();

        OneTimeWorkRequest yourRequest = new OneTimeWorkRequest.Builder(YourActivitiesWorker.class)
                .build();

        WorkManager.getInstance(requireContext())
                .beginWith(upcomingRequest)
                .then(yourRequest)
                .enqueue();
    }


}
