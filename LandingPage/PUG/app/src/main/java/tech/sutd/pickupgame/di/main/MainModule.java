package tech.sutd.pickupgame.di.main;

import android.os.Handler;
import android.os.Looper;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @MainScope
    @Provides
    static Handler provideHander() {
        return new Handler(Looper.getMainLooper());
    }
}
