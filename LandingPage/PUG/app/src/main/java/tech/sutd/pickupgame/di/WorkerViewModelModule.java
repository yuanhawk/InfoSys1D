package tech.sutd.pickupgame.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.data.worker.NewActivitiesWorker;
import tech.sutd.pickupgame.data.worker.PastActivitiesWorker;
import tech.sutd.pickupgame.data.worker.UpcomingActivitiesWorker;
import tech.sutd.pickupgame.data.worker.YourActivitiesWorker;
import tech.sutd.pickupgame.di.worker.ChildWorkerFactory;
import tech.sutd.pickupgame.di.worker.WorkerKey;

@Module
public interface WorkerViewModelModule {

    @Singleton
    @Binds
    @IntoMap
    @WorkerKey(UpcomingActivitiesWorker.class)
    ChildWorkerFactory bindUpcomingActivitiesWorker(UpcomingActivitiesWorker.Factory factory);

    @Singleton
    @Binds
    @IntoMap
    @WorkerKey(NewActivitiesWorker.class)
    ChildWorkerFactory bindNewActivitiesWorker(NewActivitiesWorker.Factory factory);

    @Singleton
    @Binds
    @IntoMap
    @WorkerKey(YourActivitiesWorker.class)
    ChildWorkerFactory bindYourActivitiesWorker(YourActivitiesWorker.Factory factory);

    @Singleton
    @Binds
    @IntoMap
    @WorkerKey(PastActivitiesWorker.class)
    ChildWorkerFactory bindPastActivitiesWorker(PastActivitiesWorker.Factory factory);

}
