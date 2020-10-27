package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingRepository;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingActViewModel extends ViewModel {

    private UpcomingRepository repository;

    private LiveData<List<UpcomingActivity>> upcomingActivities;

    @Inject
    public UpcomingActViewModel(@NonNull Application application) {
        this.repository = new UpcomingRepository(application);
        upcomingActivities = repository.getAllUpcomingActivities();
    }

    public void insert(UpcomingActivity upcomingActivity) {
        repository.insert(upcomingActivity);
    }

    public void update(UpcomingActivity upcomingActivity) {
        repository.update(upcomingActivity);
    }

    public void delete(UpcomingActivity upcomingActivity) {
        repository.delete(upcomingActivity);
    }

    public void deleteAllUpcomingActivities() {
        repository.deleteAllUpcomingActivities();
    }

    public LiveData<List<UpcomingActivity>> getUpcomingActivities() {
        return upcomingActivities;
    }
}
