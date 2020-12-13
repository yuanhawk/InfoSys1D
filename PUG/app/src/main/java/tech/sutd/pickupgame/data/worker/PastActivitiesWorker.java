package tech.sutd.pickupgame.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import javax.inject.Inject;
import javax.inject.Provider;

import tech.sutd.pickupgame.di.worker.ChildWorkerFactory;
import tech.sutd.pickupgame.ui.main.main.viewmodel.PastActViewModel;

public class PastActivitiesWorker extends Worker {

    private final PastActViewModel viewModel;

    @Inject
    public PastActivitiesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,
                                PastActViewModel viewModel) {
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

        private final Provider<PastActViewModel> viewModelProvider;

        @Inject
        public Factory(Provider<PastActViewModel> viewModelProvider) {
            this.viewModelProvider = viewModelProvider;
        }

        @Override
        public ListenableWorker create(Context appContext, WorkerParameters workerParameters) {
            return new PastActivitiesWorker(appContext,
                    workerParameters,
                    viewModelProvider.get());
        }
    }
}
