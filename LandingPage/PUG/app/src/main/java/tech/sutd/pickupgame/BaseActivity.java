package tech.sutd.pickupgame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.PastActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.data.worker.YourActivitiesWorker;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    public void pull() {
        buildOneTimeWorkRequest(UpcomingActivitiesWorker.class, NewActivitiesWorker.class,
                YourActivitiesWorker.class, PastActivitiesWorker.class);
    }

    private void enqueueTask(List<OneTimeWorkRequest> requests) {
        WorkManager.getInstance(getApplicationContext())
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
}
