package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.main.adapter.YourActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.upcomingact.UpcomingActFragment;
import tech.sutd.pickupgame.util.StringChecker;

public class YourActViewModel extends BaseViewModel {

    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;
    private final FirebaseFirestore fStore;
    private final NewActViewModel viewModel;

    private final MediatorLiveData<Resource<List<YourActivity>>> source = new MediatorLiveData<>();

    @Inject
    public YourActViewModel(SchedulerProvider provider, DataManager dataManager, DatabaseReference reff,
                            FirebaseAuth fAuth, FirebaseFirestore fStore, NewActViewModel viewModel) {
        super(provider, dataManager);
        this.reff = reff;
        this.fAuth = fAuth;
        this.fStore = fStore;
        this.viewModel = viewModel;
    }

    @Override
    public void setError(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "setError: ", e);
            e.printStackTrace();
        }
        source.setValue(Resource.error(e.getMessage()));
    }

    public void insert(YourActivity activity) {
        getCompositeDisposable().add(getDataManager().insertYourActivity(activity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteById(String id) {
        getCompositeDisposable().add(getDataManager().deleteYourActivityById(id)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }

    public LiveData<Resource<List<YourActivity>>> getAllYourActivitiesByClockLimit10() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<List<YourActivity>>> activitySource = LiveDataReactiveStreams.fromPublisher(

                getDataManager().getAllYourActivitiesLimit10()

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

    public LiveData<Resource<List<YourActivity>>> getAllYourActivitiesByClock() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<List<YourActivity>>> activitySource = LiveDataReactiveStreams.fromPublisher(

                getDataManager().getAllYourActivities()

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


    public void deleteFromDb(UpcomingActFragment upcomingFragment, YourActivityAdapter adapter, String id) {
        reff.child("your_activity")
                .child(Objects.requireNonNull(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()))
                .child(id)
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fStore.collection("activities")
                                .document(id)
                                .delete()
                                .addOnCompleteListener(task1 -> {
                                    if (!task1.isSuccessful()) {
                                        if (upcomingFragment != null) {
                                            upcomingFragment.getUpcomingActDeleteListener().onDeleteFailure();
                                        }
                                    }
                                });

                        if (upcomingFragment != null)
                            upcomingFragment.getUpcomingActDeleteListener().onDeleteSuccess(null, upcomingFragment);
                        viewModel.deleteById(id);
                        deleteById(id);
                        adapter.getDialog().dismiss();
                    } else {
                        if (upcomingFragment != null)
                            upcomingFragment.getUpcomingActDeleteListener().onDeleteFailure();
                    }
                });
    }

    public void pull() {
        reff.child("your_activity")
                .child(Objects.requireNonNull(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            BookingActivity activity = ds.getValue(BookingActivity.class);
                            assert activity != null;
                            insert(new YourActivity.Builder(ds.getKey())
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
