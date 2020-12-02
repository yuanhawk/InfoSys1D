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
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;

public class UpcomingActivitiesWorker extends Worker {

    public static final String KEY_TASK_OUTPUT = "key_task_output";

    private final UpcomingActViewModel viewModel;

    @Inject
    public UpcomingActivitiesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,
                                    UpcomingActViewModel viewModel) {
        super(context, workerParams);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public Result doWork() {
        viewModel.pull();
        viewModel.deleteByClock(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        return Result.success();
    }

    public static class Factory implements ChildWorkerFactory {

        private final Provider<UpcomingActViewModel> viewModelProvider;

        @Inject
        public Factory(Provider<UpcomingActViewModel> viewModelProvider) {
            this.viewModelProvider = viewModelProvider;
        }

        @Override
        public ListenableWorker create(Context context, WorkerParameters workerParameters) {
            return new UpcomingActivitiesWorker(context,
                    workerParameters,
                    viewModelProvider.get());
        }
    }

}
