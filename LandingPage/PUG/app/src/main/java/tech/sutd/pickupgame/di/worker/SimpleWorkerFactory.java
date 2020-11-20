package tech.sutd.pickupgame.di.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import tech.sutd.pickupgame.di.worker.ChildWorkerFactory;
import tech.sutd.pickupgame.di.worker.CollectionsUtils;

public class SimpleWorkerFactory extends WorkerFactory {

    private final Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> workersFactories;

    @Inject
    public SimpleWorkerFactory(Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> workersFactories) {
        this.workersFactories = workersFactories;
    }

    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        Provider<ChildWorkerFactory> factoryProvider = CollectionsUtils.getWorkerFactoryProviderByKey(workersFactories, workerClassName);
        assert factoryProvider != null;
        return factoryProvider.get().create(appContext, workerParameters);
    }
}
