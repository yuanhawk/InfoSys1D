package tech.sutd.pickupgame;

import android.content.SharedPreferences;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import tech.sutd.pickupgame.di.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {

    public static SharedPreferences instance;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = getSharedPreferences(getString(R.string.shared_pref_file), MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPref() {
        return instance;
    }
}