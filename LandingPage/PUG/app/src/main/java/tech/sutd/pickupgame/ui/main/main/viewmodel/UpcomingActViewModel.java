package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
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

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.util.StringComparator;

public class UpcomingActViewModel extends BaseViewModel {

    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;

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
    public UpcomingActViewModel(SchedulerProvider provider, DataManager dataManager, DatabaseReference reff, FirebaseAuth fAuth) {
        super(provider, dataManager);
        this.reff = reff;
        this.fAuth = fAuth;
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
                             String id) {
        reff.child("upcoming_activity")
                .child(Objects.requireNonNull(fAuth.getCurrentUser().getUid()))
                .child(id)
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (mainFragment != null)
                            mainFragment.getUpcomingActDeleteListener().onDeleteSuccess(mainFragment, null);
                        else if (upcomingActFragment != null)
                            upcomingActFragment.getUpcomingActDeleteListener().onDeleteSuccess(null, upcomingActFragment);
                        deleteById(id);
                        adapter.getDialog().dismiss();
                    } else {
                        if (mainFragment != null)
                            mainFragment.getUpcomingActDeleteListener().onDeleteFailure();
                        else if (upcomingActFragment != null)
                            upcomingActFragment.getUpcomingActDeleteListener().onDeleteFailure();
                    }
                });
    }

    public void pull() {
        reff.child("upcoming_activity")
                .child(Objects.requireNonNull(fAuth.getCurrentUser().getUid()))
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

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }
}
