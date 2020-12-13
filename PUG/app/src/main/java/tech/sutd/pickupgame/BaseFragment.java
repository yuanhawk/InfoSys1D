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
import androidx.work.Worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.PastActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.data.worker.YourActivitiesWorker;

public abstract class BaseFragment extends DaggerFragment {

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

    private void enqueueTask(List<OneTimeWorkRequest> requests) {
        WorkManager.getInstance(requireContext())
                .enqueue(requests);
    }

    @SafeVarargs
    private final void buildOneTimeWorkRequest(Class<? extends Worker>... classNameList) {

        List<OneTimeWorkRequest> requests = new ArrayList<>();
        for (Class<? extends Worker> className: classNameList) {
            requests.add(new OneTimeWorkRequest.Builder(className)
                    .build());
        }
        enqueueTask(requests);
    }

    public void pullMainAct() {
        buildOneTimeWorkRequest(UpcomingActivitiesWorker.class, NewActivitiesWorker.class);
    }

    public void pullNewAct() {
        buildOneTimeWorkRequest(NewActivitiesWorker.class);
    }

    public void pullUpcomingAct() {
        buildOneTimeWorkRequest(UpcomingActivitiesWorker.class, YourActivitiesWorker.class,
                PastActivitiesWorker.class);
    }


}
