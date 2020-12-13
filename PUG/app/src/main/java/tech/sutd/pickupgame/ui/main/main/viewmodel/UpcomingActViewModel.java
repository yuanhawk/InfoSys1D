package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.util.StringChecker;

public class UpcomingActViewModel extends BaseViewModel {

    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;
    private final FirebaseFirestore fStore;

    private final MediatorLiveData<Resource<PagedList<UpcomingActivity>>> source = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "setError: ", e);
            e.printStackTrace();
        }
        source.setValue(Resource.error(e.getMessage()));
    }

    @Inject
    public UpcomingActViewModel(SchedulerProvider provider, DataManager dataManager, DatabaseReference reff,
                                FirebaseAuth fAuth, FirebaseFirestore fStore) {
        super(provider, dataManager);
        this.reff = reff;
        this.fAuth = fAuth;
        this.fStore = fStore;
    }

    public void insert(UpcomingActivity upcomingActivity) {
        getCompositeDisposable().add(getDataManager().insertUpcomingActivity(upcomingActivity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteByClock(String clock) {
        getCompositeDisposable().add(getDataManager().deleteUpcomingActivities(clock)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteById(String id) {
        getCompositeDisposable().add(getDataManager().deleteUpcomingActivities(id)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public LiveData<Resource<PagedList<UpcomingActivity>>> getAllUpcomingActivitiesByClock() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<PagedList<UpcomingActivity>>> upcomingSource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getAllUpcomingActivitiesByClock()
                .onBackpressureDrop()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(upcomingSource, pagedListResource -> {
            source.setValue(pagedListResource);
            source.removeSource(upcomingSource);
        });

        return source;
    }

    public LiveData<Resource<PagedList<UpcomingActivity>>> getUpcomingActivitiesByClock2() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<PagedList<UpcomingActivity>>> upcomingSource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getUpcomingActivitiesByClockLimit2()
                .onBackpressureBuffer()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(upcomingSource, pagedListResource -> {
            source.setValue(pagedListResource);
            source.removeSource(upcomingSource);
        });
        return source;
    }

    public void deleteFromDb(MainFragment mainFragment, UpcomingActFragment upcomingActFragment, UpcomingActivityAdapter adapter,
                             UpcomingActivity upcomingActivity) {
        deleteById(upcomingActivity.getId());

        fStore.collection("activities")
                .document(upcomingActivity.getId())
                .collection("participants")
                .document(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())
                .delete();

        Map<String, Object> map = new HashMap<>();
        map.put("count", StringChecker.sub(upcomingActivity.getCount()));
        fStore.collection("activities")
                .document(upcomingActivity.getId())
                .update(map);

        reff.child("your_activity")
                .child(fAuth.getCurrentUser().getUid())
                .child(upcomingActivity.getId())
                .child("count")
                .setValue(StringChecker.sub(upcomingActivity.getCount()));

        reff.child("upcoming_activity")
                .child(Objects.requireNonNull(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()))
                .child(upcomingActivity.getId())
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (mainFragment != null) {
                            mainFragment.getUpcomingActDeleteListener().onDeleteSuccess(mainFragment, null);
                        } else if (upcomingActFragment != null) {
                            upcomingActFragment.getUpcomingActDeleteListener().onDeleteSuccess(null, upcomingActFragment);
                        }
                        adapter.getDialog().dismiss();
                    }
                });

    }

    public void pull() {
        reff.child("upcoming_activity")
                .child(Objects.requireNonNull(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()))
                .orderByChild("epoch")
                .startAt(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            BookingActivity activity = ds.getValue(BookingActivity.class);
                            assert activity != null;

                            if (activity.getSport() != null) {
                                insert(new UpcomingActivity.Builder(ds.getKey())
                                        .setSport(activity.getSport())
                                        .setSportImg(StringChecker.caseImage(activity.getSport()))
                                        .setClock(activity.getEpoch())
                                        .setClockImg(R.drawable.ic_clock)
                                        .setEndClock(activity.getEpochEnd())
                                        .setLocationImg(R.drawable.ic_location)
                                        .setLocation(activity.getLoc())
                                        .setOrganizerImg(R.drawable.ic_profile)
                                        .setOrganizer(activity.getOrganizer())
                                        .setParticipantImg(R.drawable.ic_participants)
                                        .setParticipant(activity.getPart())
                                        .setCount(activity.getCount())
                                        .setNotesImg(R.drawable.ic_notes)
                                        .setNotes(activity.getDesc())
                                        .build()
                                );
                            }
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
