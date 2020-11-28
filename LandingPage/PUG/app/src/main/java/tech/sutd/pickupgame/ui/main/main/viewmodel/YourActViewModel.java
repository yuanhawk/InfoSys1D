package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;

public class YourActViewModel extends BaseViewModel {

    private final MediatorLiveData<Resource<List<YourActivity>>> source = new MediatorLiveData<>();

    @Inject
    public YourActViewModel(SchedulerProvider provider, DataManager dataManager) {
        super(provider, dataManager);
    }

    @Override
    public void setError(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "setError: ", e);
            e.printStackTrace();
        }
        source.setValue(Resource.error(e.getMessage()));
    }

    public void insert(YourActivity activity) {
        getCompositeDisposable().add(getDataManager().insertYourActivity(activity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }

    public LiveData<Resource<List<YourActivity>>> getYourActivities() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<List<YourActivity>>> activitySource = LiveDataReactiveStreams.fromPublisher(

                getDataManager().getAllActivities()

                .onBackpressureBuffer()
                .onErrorReturn(throwable -> {
                    YourActivity activity = new YourActivity();
                    activity.setId(-1);
                    List<YourActivity> yourActivities = new ArrayList<>();
                    yourActivities.add(activity);
                    return yourActivities;
                })
                .map((Function<List<YourActivity>, Resource<List<YourActivity>>>) yourActivities -> {
                    if (yourActivities.size() > 0 && yourActivities.get(0).getId() == -1)
                        return Resource.error("No Data");
                    return Resource.success(yourActivities);
                })
                .subscribeOn(getProvider().io())
        );

        source.addSource(activitySource, listResource -> {
            source.setValue(listResource);
            source.removeSource(source);
        });
        return source;
    }
}
