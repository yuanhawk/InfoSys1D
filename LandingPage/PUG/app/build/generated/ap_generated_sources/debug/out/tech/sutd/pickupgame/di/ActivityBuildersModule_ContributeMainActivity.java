package tech.sutd.pickupgame.di;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.main.MainFragmentBuildersModule;
import tech.sutd.pickupgame.di.main.MainScope;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Module(
  subcomponents = ActivityBuildersModule_ContributeMainActivity.MainActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeMainActivity {
  private ActivityBuildersModule_ContributeMainActivity() {}

  @Binds
  @IntoMap
  @ClassKey(MainActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      MainActivitySubcomponent.Factory builder);

  @Subcomponent(modules = MainFragmentBuildersModule.class)
  @MainScope
  public interface MainActivitySubcomponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<MainActivity> {}
  }
}
