package tech.sutd.pickupgame.di.main;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ui.main.user.UserFragment;

@Module(
  subcomponents = MainFragmentBuildersModule_ContributeUserFragment.UserFragmentSubcomponent.class
)
public abstract class MainFragmentBuildersModule_ContributeUserFragment {
  private MainFragmentBuildersModule_ContributeUserFragment() {}

  @Binds
  @IntoMap
  @ClassKey(UserFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      UserFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface UserFragmentSubcomponent extends AndroidInjector<UserFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<UserFragment> {}
  }
}
