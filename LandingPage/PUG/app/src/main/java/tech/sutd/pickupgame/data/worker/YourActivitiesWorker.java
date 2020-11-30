package tech.sutd.pickupgame.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Provider;

import tech.sutd.pickupgame.di.worker.ChildWorkerFactory;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;

public class YourActivitiesWorker extends Worker {

    private final YourActViewModel viewModel;

    @Inject
    public YourActivitiesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, YourActViewModel viewModel) {
        super(context, workerParams);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public Result doWork() {
        viewModel.pull();
        return Result.success();
    }

    public static class Factory implements ChildWorkerFactory {

        private final Provider<YourActViewModel> viewModelProvider;

        @Inject
        public Factory(Provider<YourActViewModel> viewModelProvider) {
            this.viewModelProvider = viewModelProvider;
        }

        @Override
        public ListenableWorker create(Context appContext, WorkerParameters workerParameters) {
            return new YourActivitiesWorker(appContext,
                    workerParameters,
                    viewModelProvider.get());
        }
    }
}
