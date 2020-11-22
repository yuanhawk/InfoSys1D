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
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;

public class NewActivitiesWorker extends Worker {

    public static final String KEY_TASK_OUTPUT = "key_task_output";

    private final NewActViewModel viewModel;

    @Inject
    public NewActivitiesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,
                               NewActViewModel viewModel) {
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

        private final Provider<NewActViewModel> viewModelProvider;

        @Inject
        public Factory(Provider<NewActViewModel> viewModelProvider) {
            this.viewModelProvider = viewModelProvider;
        }

        @Override
        public ListenableWorker create(Context context, WorkerParameters workerParameters) {
            return new NewActivitiesWorker(context,
                    workerParameters,
                    viewModelProvider.get());
        }
    }

}
