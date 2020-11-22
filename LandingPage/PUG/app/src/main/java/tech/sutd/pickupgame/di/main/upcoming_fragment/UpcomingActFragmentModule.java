package tech.sutd.pickupgame.di.main.upcoming_fragment;

import com.bumptech.glide.RequestManager;

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
    static YourActivityAdapter provideYourActivityAdapter(RequestManager requestManager) {
        return new YourActivityAdapter(requestManager);
    }

    @UpcomingActFragmentScope
    @Provides
    static PastActivityAdapter providePastActivityAdapter(RequestManager requestManager) {
        return new PastActivityAdapter(requestManager);
    }
}
