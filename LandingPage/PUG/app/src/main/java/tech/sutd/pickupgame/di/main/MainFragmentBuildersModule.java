package tech.sutd.pickupgame.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.main.new_fragment.NewActFragmentModule;
import tech.sutd.pickupgame.di.main.new_fragment.NewActFragmentScope;
import tech.sutd.pickupgame.di.main.upcoming_fragment.UpcomingActFragmentModule;
import tech.sutd.pickupgame.di.main.upcoming_fragment.UpcomingActFragmentScope;
import tech.sutd.pickupgame.di.main.upcoming_fragment.UpcomingActFragmentViewModelModule;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.ui.main.user.UserFragment;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract BookingFragment contributeBookingFragment();

    @UpcomingActFragmentScope
    @ContributesAndroidInjector(
            modules = {
                    UpcomingActFragmentViewModelModule.class, UpcomingActFragmentModule.class
            }
    )
    abstract UpcomingActFragment contributeUpcomingActFragment();

    @NewActFragmentScope
    @ContributesAndroidInjector(
            modules = {NewActFragmentModule.class}
    )
    abstract NewActFragment contributeNewActFragment();
}
