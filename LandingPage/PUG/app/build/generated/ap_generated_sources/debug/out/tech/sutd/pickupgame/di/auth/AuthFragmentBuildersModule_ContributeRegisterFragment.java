package tech.sutd.pickupgame.di.auth;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;

@Module(
  subcomponents =
      AuthFragmentBuildersModule_ContributeRegisterFragment.RegisterFragmentSubcomponent.class
)
public abstract class AuthFragmentBuildersModule_ContributeRegisterFragment {
  private AuthFragmentBuildersModule_ContributeRegisterFragment() {}

  @Binds
  @IntoMap
  @ClassKey(RegisterFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      RegisterFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface RegisterFragmentSubcomponent extends AndroidInjector<RegisterFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<RegisterFragment> {}
  }
}
