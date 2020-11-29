package tech.sutd.pickupgame.di.main;

import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.RequestManager;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.MainActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;

@Module
public class MainActivityModule {

    @MainScope
    @Provides
    static FragmentManager provideFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }

    @MainScope
    @Provides
    static UpcomingActivityAdapter<UpcomingActivity> provideUpcomingActivityAdapter(RequestManager requestManager) {
        return new UpcomingActivityAdapter<>(requestManager);
    }

    @MainScope
    @Provides
    static NewActivityAdapter<NewActivity> provideNewActivityAdapter(RequestManager requestManager, NewActViewModel viewModel) {
        return new NewActivityAdapter<>(requestManager, viewModel);
    }
}
