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
        if (viewModel == null)
            return Result.failure();
        viewModel.pull();
        viewModel.deleteByChecked(1);
        viewModel.deleteByClock(String.valueOf(Calendar.getInstance().getTimeInMillis()));
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
