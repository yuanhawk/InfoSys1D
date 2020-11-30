package tech.sutd.pickupgame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.data.worker.YourActivitiesWorker;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObserver();
    }

    public void subscribeObserver() {
        sessionManager.observeAuthState().observe(this, firebaseAuthAuthResource -> {
            if (firebaseAuthAuthResource.status == AuthResource.AuthStatus.NOT_AUTHENTICATED)
                navLoginScreen();
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    public void pull() {
        OneTimeWorkRequest upcomingRequest = new OneTimeWorkRequest.Builder(UpcomingActivitiesWorker.class)
                .build();

        OneTimeWorkRequest newRequest = new OneTimeWorkRequest.Builder(NewActivitiesWorker.class)
                .build();

        OneTimeWorkRequest yourRequest = new OneTimeWorkRequest.Builder(YourActivitiesWorker.class)
                .build();

        WorkManager.getInstance(getApplicationContext())
                .beginWith(upcomingRequest)
                .then(newRequest)
                .then(yourRequest)
                .enqueue();
    }
}
