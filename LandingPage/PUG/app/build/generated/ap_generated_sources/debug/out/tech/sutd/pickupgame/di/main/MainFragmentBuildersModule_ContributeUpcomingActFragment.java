package tech.sutd.pickupgame.di.main;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.main.main_fragment.UpcomingActFragmentViewModelModule;
import tech.sutd.pickupgame.ui.main.main.upcoming.UpcomingActFragment;

@Module(
  subcomponents =
      MainFragmentBuildersModule_ContributeUpcomingActFragment.UpcomingActFragmentSubcomponent.class
)
public abstract class MainFragmentBuildersModule_ContributeUpcomingActFragment {
  private MainFragmentBuildersModule_ContributeUpcomingActFragment() {}

  @Binds
  @IntoMap
  @ClassKey(UpcomingActFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      UpcomingActFragmentSubcomponent.Factory builder);

  @Subcomponent(modules = UpcomingActFragmentViewModelModule.class)
  public interface UpcomingActFragmentSubcomponent extends AndroidInjector<UpcomingActFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<UpcomingActFragment> {}
  }
}
