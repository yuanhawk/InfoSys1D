package tech.sutd.pickupgame.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppContextModule {

    @Binds
    abstract Context provideContext(Application application);

}
