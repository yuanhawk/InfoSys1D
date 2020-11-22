package tech.sutd.pickupgame.di.main.new_fragment;

import com.bumptech.glide.RequestManager;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.ui.main.main.adapter.FilterAdapter;

@Module
public class NewActFragmentModule {

    @NewActFragmentScope
    @Provides
    static FilterAdapter provideFilterAdapter(RequestManager requestManager) {
        return new FilterAdapter(requestManager);
    }
}
