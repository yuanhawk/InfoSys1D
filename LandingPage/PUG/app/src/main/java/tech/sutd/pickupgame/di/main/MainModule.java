package tech.sutd.pickupgame.di.main;

import android.os.Handler;
import android.os.Looper;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;

@Module
public class MainModule {

    @MainScope
    @Provides
    static Handler provideHander() {
        return new Handler(Looper.getMainLooper());
    }
}
