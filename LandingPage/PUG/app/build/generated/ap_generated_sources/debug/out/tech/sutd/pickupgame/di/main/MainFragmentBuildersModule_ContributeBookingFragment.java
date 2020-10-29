package tech.sutd.pickupgame.di.main;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ui.main.BookingFragment;

@Module(
  subcomponents =
      MainFragmentBuildersModule_ContributeBookingFragment.BookingFragmentSubcomponent.class
)
public abstract class MainFragmentBuildersModule_ContributeBookingFragment {
  private MainFragmentBuildersModule_ContributeBookingFragment() {}

  @Binds
  @IntoMap
  @ClassKey(BookingFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      BookingFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface BookingFragmentSubcomponent extends AndroidInjector<BookingFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<BookingFragment> {}
  }
}
