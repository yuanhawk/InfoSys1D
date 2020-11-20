package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingRepository;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.util.StringComparator;

public class UpcomingActViewModel extends ViewModel {

    private UpcomingRepository repository;

    private LiveData<PagedList<UpcomingActivity>> allUpcomingActivitiesByClock, upcomingActivitiesByClock2;

    private DatabaseReference reff;
    private FirebaseAuth fAuth;

    @Inject
    public UpcomingActViewModel(@NonNull Application application, DatabaseReference reff, FirebaseAuth fAuth) {
        this.repository = new UpcomingRepository(application);
        allUpcomingActivitiesByClock = repository.getAllUpcomingActivitiesByClock();
        upcomingActivitiesByClock2 = repository.getUpcomingActivitiesByClockTwo();
        this.reff = reff;
        this.fAuth = fAuth;
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

    public LiveData<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock() {
        return allUpcomingActivitiesByClock;
    }

    public LiveData<PagedList<UpcomingActivity>> getUpcomingActivitiesByClock2() {
        return upcomingActivitiesByClock2;
    }

    public void pull() {
        reff.child("your_activity")
                .child(Objects.requireNonNull(fAuth.getUid()))
                .orderByChild("epoch")
                .startAt(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            BookingActivity activity = ds.getValue(BookingActivity.class);
                            assert activity != null;
                            insert(new UpcomingActivity.Builder(ds.getKey())
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
