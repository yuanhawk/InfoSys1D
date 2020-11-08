package tech.sutd.pickupgame.di.main;

import android.os.Handler;
import android.os.Looper;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;

@Module
public class MainActivityModule {

    @MainScope
    @Provides
    static Handler provideHander() {
        return new Handler(Looper.getMainLooper());
    }

    @MainScope
    @Provides
    static UpcomingActivityAdapter<UpcomingActivity> provideUpcomingActivityAdapter() {
        return new UpcomingActivityAdapter<>();
    }

    @MainScope
    @Provides
    static NewActivityAdapter<NewActivity> provideNewActivityAdapter() {
        return new NewActivityAdapter<>();
    }
}
