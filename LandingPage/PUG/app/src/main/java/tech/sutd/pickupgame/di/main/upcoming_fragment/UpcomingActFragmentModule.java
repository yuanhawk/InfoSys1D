package tech.sutd.pickupgame.di.main.upcoming_fragment;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.PastActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.YourActivityAdapter;

@Module
public class UpcomingActFragmentModule {

    @UpcomingActFragmentScope
    @Provides
    static YourActivityAdapter provideYourActivityAdapter() {
        return new YourActivityAdapter();
    }

    @UpcomingActFragmentScope
    @Provides
    static PastActivityAdapter providePastActivityAdapter() {
        return new PastActivityAdapter();
    }
}
