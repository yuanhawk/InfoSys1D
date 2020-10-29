package tech.sutd.pickupgame.di;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.auth.AuthFragmentBuildersModule;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.di.auth.AuthViewModelModule;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

@Module(
  subcomponents = ActivityBuildersModule_ContributeAuthActivity.AuthActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeAuthActivity {
  private ActivityBuildersModule_ContributeAuthActivity() {}

  @Binds
  @IntoMap
  @ClassKey(AuthActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      AuthActivitySubcomponent.Factory builder);

  @Subcomponent(modules = {AuthFragmentBuildersModule.class, AuthViewModelModule.class})
  @AuthScope
  public interface AuthActivitySubcomponent extends AndroidInjector<AuthActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<AuthActivity> {}
  }
}
