package tech.sutd.pickupgame.di.main.new_fragment;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.ui.main.main.adapter.FilterAdapter;

@Module
public class NewActFragmentModule {

    @NewActFragmentScope
    @Provides
    static FilterAdapter provideFilterAdapter() {
        return new FilterAdapter();
    }
}
