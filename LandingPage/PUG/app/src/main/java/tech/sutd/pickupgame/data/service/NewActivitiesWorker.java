package tech.sutd.pickupgame.data.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sutd.pickupgame.di.Provider;

@Singleton
public class NewActivitiesWorker extends Worker {

    @Inject
    public NewActivitiesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Provider.getAppComponent().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {

        return null;
    }
}
