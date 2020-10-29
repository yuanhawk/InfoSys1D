package tech.sutd.pickupgame.di.auth;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;

@Module(
  subcomponents = AuthFragmentBuildersModule_ContributeLoginFragment.LoginFragmentSubcomponent.class
)
public abstract class AuthFragmentBuildersModule_ContributeLoginFragment {
  private AuthFragmentBuildersModule_ContributeLoginFragment() {}

  @Binds
  @IntoMap
  @ClassKey(LoginFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      LoginFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface LoginFragmentSubcomponent extends AndroidInjector<LoginFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<LoginFragment> {}
  }
}
