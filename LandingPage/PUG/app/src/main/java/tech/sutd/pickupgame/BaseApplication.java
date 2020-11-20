package tech.sutd.pickupgame;

import androidx.work.Configuration;
import androidx.work.WorkManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import tech.sutd.pickupgame.di.worker.SimpleWorkerFactory;
import tech.sutd.pickupgame.di.AppComponent;
import tech.sutd.pickupgame.di.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {

    private static BaseApplication instance;

    @Inject SimpleWorkerFactory factory;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent =  DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        configureWorkManager();
    }

    private void configureWorkManager() {
        Configuration config = new Configuration.Builder()
                .setWorkerFactory(factory)
                .build();

        WorkManager.initialize(this, config);
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}