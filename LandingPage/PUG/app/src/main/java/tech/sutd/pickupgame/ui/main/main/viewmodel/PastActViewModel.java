package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.past_activity.PastRepository;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class PastActViewModel extends ViewModel {

    private PastRepository repository;

    private LiveData<List<PastActivity>> pastActivities;

    @Inject
    public PastActViewModel(@NonNull Application application) {
        this.repository = new PastRepository(application);
        pastActivities = repository.getAllPastActivities();
    }

    public void insert(PastActivity pastActivity) {
        repository.insert(pastActivity);
    }

    public void update(PastActivity pastActivity) {
        repository.update(pastActivity);
    }

    public void delete(PastActivity pastActivity) {
        repository.delete(pastActivity);
    }

    public void deleteAllUpcomingActivities() {
        repository.deleteAllPastActivities();
    }

    public LiveData<List<PastActivity>> getPastActivities() {
        return pastActivities;
    }
}
