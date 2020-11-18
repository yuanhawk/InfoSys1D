package tech.sutd.pickupgame.di;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import tech.sutd.pickupgame.BaseApplication;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.service.NewActivitiesWorker;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Override
    void inject(BaseApplication instance);

    SessionManager sessionManager();

    void inject(NewActivitiesWorker worker);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
