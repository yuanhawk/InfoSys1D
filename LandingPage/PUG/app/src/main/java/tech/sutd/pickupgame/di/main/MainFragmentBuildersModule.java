package tech.sutd.pickupgame.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.main.main_fragment.MainFragmentScope;
import tech.sutd.pickupgame.di.main.main_fragment.MainFragmentViewModelModule;
import tech.sutd.pickupgame.di.main.upcoming_fragment.UpcomingActFragmentScope;
import tech.sutd.pickupgame.di.main.upcoming_fragment.UpcomingActFragmentViewModelModule;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.upcoming.UpcomingActFragment;
import tech.sutd.pickupgame.ui.main.user.UserFragment;

@Module
public abstract class MainFragmentBuildersModule {

    @MainFragmentScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentViewModelModule.class
            }
    )
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract BookingFragment contributeBookingFragment();

    @UpcomingActFragmentScope
    @ContributesAndroidInjector(
            modules = {
                    UpcomingActFragmentViewModelModule.class
            }
    )
    abstract UpcomingActFragment contributeUpcomingActFragment();
}
