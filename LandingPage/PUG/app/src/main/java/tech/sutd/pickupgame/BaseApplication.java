package tech.sutd.pickupgame;

import android.content.SharedPreferences;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import tech.sutd.pickupgame.di.AppComponent;
import tech.sutd.pickupgame.di.DaggerAppComponent;
import tech.sutd.pickupgame.di.Provider;

public class BaseApplication extends DaggerApplication {

    public static BaseApplication instance;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent =  DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        Provider.setAppComponent(appComponent);
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

}