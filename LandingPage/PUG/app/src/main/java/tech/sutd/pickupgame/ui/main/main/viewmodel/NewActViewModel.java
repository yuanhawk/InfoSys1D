package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.util.StringComparator;

public class NewActViewModel extends ViewModel {

    private NewRepository repository;

    private LiveData<PagedList<NewActivity>> allNewActivitiesByClock, allNewActivitiesBySport, newActivitiesByClock2;

    private PeriodicWorkRequest workRequest;
    private FirebaseFirestore fStore;
    private OneTimeWorkRequest singleRequest;

    @Inject
    public NewActViewModel(@NonNull Application application, PeriodicWorkRequest workRequest,
                           FirebaseFirestore fStore, OneTimeWorkRequest singleRequest) {
        this.repository = new NewRepository(application);
        allNewActivitiesByClock = repository.getAllNewActivitiesByClock();
        allNewActivitiesBySport = repository.getAllNewActivitiesBySport();
        newActivitiesByClock2 = repository.getNewActivitiesByClock2();

        this.workRequest = workRequest;
        this.fStore = fStore;
        this.singleRequest = singleRequest;
    }

    public void insert(NewActivity newActivity) {
        repository.insert(newActivity);
    }

    public void update(NewActivity newActivity) {
        repository.update(newActivity);
    }

    public void delete(String clock) {
        repository.delete(clock);
    }

    public void deleteAllNewActivities() {
        repository.deleteAllNewActivities();
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesByClock() {
        return allNewActivitiesByClock;
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesBySport() {
        return allNewActivitiesBySport;
    }

    public LiveData<PagedList<NewActivity>> getNewActivitiesByClock2() {
        return newActivitiesByClock2;
    }

    public void pull() {
        fStore.collection("activities")
                .whereGreaterThanOrEqualTo("epoch", String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                            BookingActivity activity = ds.toObject(BookingActivity.class);


                            insert(new NewActivity.Builder(ds.getId())
                                    .setSport(activity.getSport())
                                    .setSportImg(StringComparator.caseImage(activity.getSport()))
                                    .setClock(activity.getEpoch())
                                    .setClockImg(R.drawable.ic_clock)
                                    .setEndClock(activity.getEpochEnd())
                                    .setLocationImg(R.drawable.ic_location)
                                    .setLocation(activity.getLoc())
                                    .setOrganizerImg(R.drawable.ic_profile)
                                    .setOrganizer(activity.getOrganizer())
                                    .setParticipantImg(R.drawable.ic_participants)
                                    .setParticipant(activity.getPart())
                                    .setNotesImg(R.drawable.ic_notes)
                                    .setNotes(activity.getDesc())
                                    .build()
                            );
                        }
                    }
                });
    }
}
