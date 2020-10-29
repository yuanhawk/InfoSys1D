package tech.sutd.pickupgame.di.main;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.main.main_fragment.MainFragmentScope;
import tech.sutd.pickupgame.di.main.main_fragment.MainFragmentViewModelModule;
import tech.sutd.pickupgame.ui.main.main.MainFragment;

@Module(
  subcomponents = MainFragmentBuildersModule_ContributeMainFragment.MainFragmentSubcomponent.class
)
public abstract class MainFragmentBuildersModule_ContributeMainFragment {
  private MainFragmentBuildersModule_ContributeMainFragment() {}

  @Binds
  @IntoMap
  @ClassKey(MainFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      MainFragmentSubcomponent.Factory builder);

  @Subcomponent(modules = MainFragmentViewModelModule.class)
  @MainFragmentScope
  public interface MainFragmentSubcomponent extends AndroidInjector<MainFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<MainFragment> {}
  }
}
