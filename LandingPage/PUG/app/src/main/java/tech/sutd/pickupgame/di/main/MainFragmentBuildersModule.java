package tech.sutd.pickupgame.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.ui.main.BookingFragment;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.user.UserFragment;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract BookingFragment contributeBookingFragment();
}
