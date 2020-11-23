package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.your_activity.YourRepository;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class YourActViewModel extends ViewModel {

    private final YourRepository repository;
    private final LiveData<List<YourActivity>> yourActivities;

    @Inject
    public YourActViewModel(@NonNull Application application) {
        this.repository = new YourRepository(application);
        yourActivities = repository.getAllYourActivities();
    }

    public void insert(YourActivity yourActivity) {
        repository.insert(yourActivity);
    }

    public void update(YourActivity yourActivity) {
        repository.update(yourActivity);
    }

    public void delete(YourActivity yourActivity) {
        repository.delete(yourActivity);
    }

    public void deleteAllUpcomingActivities() {
        repository.deleteAllYourActivitiesExecutor();
    }

    public LiveData<List<YourActivity>> getYourActivities() {
        return yourActivities;
    }
}
