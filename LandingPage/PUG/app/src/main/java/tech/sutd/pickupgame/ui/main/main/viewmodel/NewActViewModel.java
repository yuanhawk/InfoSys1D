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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.di.helper.NewRoomHelper;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.util.StringComparator;

public class NewActViewModel extends ViewModel {

    @Inject NewRoomHelper repository;

    private final LiveData<PagedList<NewActivity>> allNewActivitiesByClock, allNewActivitiesBySport, newActivitiesByClock2;

    private final FirebaseFirestore fStore;
    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;

    @Inject
    public NewActViewModel(FirebaseFirestore fStore, NewRoomHelper helper,
                           DatabaseReference reff, FirebaseAuth fAuth) {
        repository = helper;
        allNewActivitiesByClock = repository.getAllNewActivitiesByClock();
        allNewActivitiesBySport = repository.getAllNewActivitiesBySport();
        newActivitiesByClock2 = repository.getNewActivitiesByClock2();

        this.fStore = fStore;
        this.reff = reff;
        this.fAuth = fAuth;
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

    public void push(MainFragment mainFragment, NewActFragment newActFragment, NewActivityAdapter adapter,
                     String id, BookingActivity bookingActivity) {
        reff.child("your_activity")
                .child(Objects.requireNonNull(fAuth.getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            if (Objects.equals(ds.getKey(), id)) {
                                if (mainFragment != null)
                                    mainFragment.getSuccessListenerTwo().onSignUpSuccess();
                                else if (newActFragment != null)
                                    newActFragment.getSuccessListenerTwo().onSignUpSuccess();

                                adapter.getDialog().dismiss();

                                return;
                            } else {

                                reff.child("your_activity")
                                        .child(Objects.requireNonNull(fAuth.getUid()))
                                        .child(id)
                                        .setValue(bookingActivity)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {

                                                if (mainFragment != null)
                                                    mainFragment.getSuccessListenerTwo().onSignUpSuccess();
                                                else if (newActFragment != null)
                                                    newActFragment.getSuccessListenerTwo().onSignUpSuccess();

                                            } else {

                                                if (mainFragment != null)
                                                    mainFragment.getSuccessListenerTwo().onSignUpFailure();
                                                else if (newActFragment != null)
                                                    newActFragment.getSuccessListenerTwo().onSignUpFailure();

                                            }
                                            adapter.getDialog().dismiss();
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
