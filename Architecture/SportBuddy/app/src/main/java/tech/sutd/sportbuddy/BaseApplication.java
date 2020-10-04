package tech.sutd.sportbuddy;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import tech.sutd.sportbuddy.di.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
