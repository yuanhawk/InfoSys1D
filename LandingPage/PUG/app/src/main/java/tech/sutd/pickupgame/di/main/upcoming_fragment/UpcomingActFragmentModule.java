package tech.sutd.pickupgame.di.main.upcoming_fragment;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;

@Module
public class UpcomingActFragmentModule {

    @UpcomingActFragmentScope
    @Provides
    static UpcomingActivityAdapter provideUpcomingActivityAdapter() {
        return new UpcomingActivityAdapter();
    }
}
