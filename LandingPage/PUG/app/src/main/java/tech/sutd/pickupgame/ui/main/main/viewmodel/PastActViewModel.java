package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.util.StringComparator;

public class PastActViewModel extends BaseViewModel {

    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;

    private final MediatorLiveData<Resource<List<PastActivity>>> source = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {

    }

    @Inject
    public PastActViewModel(SchedulerProvider provider, DataManager dataManager, DatabaseReference reff,
                            FirebaseAuth fAuth) {
        super(provider, dataManager);
        this.reff = reff;
        this.fAuth = fAuth;
    }

    public void insert(PastActivity pastActivity) {
        getCompositeDisposable().add(getDataManager().insertPastActivity(pastActivity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }


    public LiveData<Resource<List<PastActivity>>> getPastActivities() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<List<PastActivity>>> activitySource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getAllPastActivities()
                .onBackpressureBuffer()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(activitySource, listResource -> {
            source.setValue(listResource);
            source.removeSource(activitySource);
        });
        return source;
    }

    public void pull() {
        pullFrom("upcoming_activity");
        pullFrom("your_activity");
    }

    private void pullFrom(String child) {
        reff.child(child)
                .child(Objects.requireNonNull(fAuth.getCurrentUser().getUid()))
                .orderByChild("epoch")
                .endAt(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            BookingActivity activity = ds.getValue(BookingActivity.class);
                            assert activity != null;
                            insert(new PastActivity.Builder(ds.getKey())
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

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }
}
