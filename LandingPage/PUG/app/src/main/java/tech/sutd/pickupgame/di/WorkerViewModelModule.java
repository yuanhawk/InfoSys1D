package tech.sutd.pickupgame.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.di.worker.ChildWorkerFactory;
import tech.sutd.pickupgame.di.worker.WorkerKey;

@Module
public interface WorkerViewModelModule {

    @Singleton
    @Binds
    @IntoMap
    @WorkerKey(NewActivitiesWorker.class)
    ChildWorkerFactory bindNewActivitiesWorker(NewActivitiesWorker.Factory factory);

}