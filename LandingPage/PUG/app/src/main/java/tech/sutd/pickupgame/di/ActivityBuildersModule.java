package tech.sutd.pickupgame.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.auth.AuthFragmentBuildersModule;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.di.auth.AuthViewModelModule;
import tech.sutd.pickupgame.di.main.MainFragmentBuildersModule;
import tech.sutd.pickupgame.di.main.MainModule;
import tech.sutd.pickupgame.di.main.MainScope;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthFragmentBuildersModule.class, AuthViewModelModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class, MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity();
}
