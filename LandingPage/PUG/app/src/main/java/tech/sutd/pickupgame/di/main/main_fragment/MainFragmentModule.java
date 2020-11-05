package tech.sutd.pickupgame.di.main.main_fragment;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;

@Module
public class MainFragmentModule {

    @MainFragmentScope
    @Provides
    static UpcomingActivityAdapter provideUpcomingActivityAdapter() {
        return new UpcomingActivityAdapter();
    }

    @MainFragmentScope
    @Provides
    static NewActivityAdapter provideNewActivityAdapter() {
        return new NewActivityAdapter();
    }
}
