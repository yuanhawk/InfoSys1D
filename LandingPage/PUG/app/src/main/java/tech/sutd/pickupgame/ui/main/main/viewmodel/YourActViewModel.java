package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.data.AuthResource;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;

public class YourActViewModel extends BaseViewModel {

    private final MutableLiveData<AuthResource<List<YourActivity>>> source = new MutableLiveData<>();

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
        source.setValue(AuthResource.error(e.getMessage()));
    }

    public void insert(YourActivity activity) {
        getCompositeDisposable().add(getDataManager().insertYourActivity(activity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    private void doOnLoading() {
        source.postValue(AuthResource.loading(null));
    }

    public LiveData<AuthResource<List<YourActivity>>> getYourActivities() {
        return LiveDataReactiveStreams.fromPublisher(getDataManager().getAllActivities()
                .onBackpressureBuffer()
                .onErrorReturn(throwable -> new ArrayList<>())
                .map((Function<List<YourActivity>, AuthResource<List<YourActivity>>>) yourActivities -> {
                    if (yourActivities.size() == 0)
                        return AuthResource.error("No Data");
                    return AuthResource.success(yourActivities);
                })
                .subscribeOn(getProvider().io())
        );
    }
}
