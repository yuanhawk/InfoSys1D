package tech.sutd.pickupgame.di.auth;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ui.auth.gettingstarted.GetStartedFragment;

@Module(
  subcomponents =
      AuthFragmentBuildersModule_ContributeGetStartedFragment.GetStartedFragmentSubcomponent.class
)
public abstract class AuthFragmentBuildersModule_ContributeGetStartedFragment {
  private AuthFragmentBuildersModule_ContributeGetStartedFragment() {}

  @Binds
  @IntoMap
  @ClassKey(GetStartedFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      GetStartedFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface GetStartedFragmentSubcomponent extends AndroidInjector<GetStartedFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<GetStartedFragment> {}
  }
}
