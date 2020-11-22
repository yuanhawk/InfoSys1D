package tech.sutd.pickupgame.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.auth.AuthFragmentBuildersModule;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.di.data.NewRoomModule;
import tech.sutd.pickupgame.di.main.MainActivityModule;
import tech.sutd.pickupgame.di.main.MainActivityViewModelModule;
import tech.sutd.pickupgame.di.main.MainFragmentBuildersModule;
import tech.sutd.pickupgame.di.main.MainScope;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Module(includes = NewRoomModule.class)
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthFragmentBuildersModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class, MainActivityModule.class, MainActivityViewModelModule.class
            }
    )
    abstract MainActivity contributeMainActivity();
}
