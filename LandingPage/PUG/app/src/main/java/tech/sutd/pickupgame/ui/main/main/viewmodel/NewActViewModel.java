package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.util.StringChecker;

public class NewActViewModel extends BaseViewModel {

    private final FirebaseFirestore fStore;
    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;

    private final MediatorLiveData<Resource<PagedList<NewActivity>>> source = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {

    }

    @Inject
    public NewActViewModel(SchedulerProvider provider, DataManager dataManager, FirebaseFirestore fStore,
                           DatabaseReference reff, FirebaseAuth fAuth) {
        super(provider, dataManager);

        this.fStore = fStore;
        this.reff = reff;
        this.fAuth = fAuth;
    }

    public void insert(NewActivity newActivity) {
        getCompositeDisposable().add(getDataManager().insertNewActivity(newActivity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteById(String id) {
        getCompositeDisposable().add(getDataManager().deleteNewActivityById(id)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteByChecked(int checked) {
        getCompositeDisposable().add(getDataManager().deleteNewActivityByChecked(checked)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteByClock(String clock) {
        getCompositeDisposable().add(getDataManager().deleteNewActivityByClock(clock)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public LiveData<Resource<PagedList<NewActivity>>> getAllNewActivitiesByClock() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<PagedList<NewActivity>>> newSource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getAllNewActivitiesByClock()
                .onBackpressureDrop()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(newSource, pagedListResource -> {
            source.setValue(pagedListResource);
            source.removeSource(newSource);
        });
        return source;
    }

    public LiveData<Resource<PagedList<NewActivity>>> getAllNewActivitiesBySport() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<PagedList<NewActivity>>> newSource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getAllNewActivitiesBySport()
                .onBackpressureDrop()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(newSource, pagedListResource -> {
            source.setValue(pagedListResource);
            source.removeSource(newSource);
        });
        return source;
    }

    public LiveData<Resource<PagedList<NewActivity>>> getNewActivitiesByClock2() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<PagedList<NewActivity>>> newSource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getNewActivitiesByClock2()
                .onBackpressureBuffer()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(newSource, pagedListResource -> {
            source.setValue(pagedListResource);
            source.removeSource(newSource);
        });

        return source;
    }

    public void pull() {
        fStore.collection("activities")
                .whereGreaterThanOrEqualTo("epoch", String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                            BookingActivity activity = ds.toObject(BookingActivity.class);

                            if (activity.getCount() != null && activity.getPart() != null) {
                                int count = 0, part = 0;

                                try {
                                    count = Integer.parseInt(activity.getCount());
                                    part = Integer.parseInt(activity.getPart());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                if (count < part && activity.getSport() != null) {
                                    insert(new NewActivity.Builder(ds.getId())
                                            .setSport(activity.getSport())
                                            .setSportImg(StringChecker.caseImage(activity.getSport()))
                                            .setClock(activity.getEpoch())
                                            .setClockImg(R.drawable.ic_clock)
                                            .setEndClock(activity.getEpochEnd())
                                            .setLocationImg(R.drawable.ic_location)
                                            .setLocation(activity.getLoc())
                                            .setOrganizerImg(R.drawable.ic_profile)
                                            .setOrganizer(activity.getOrganizer())
                                            .setIdentifier(activity.getOrganizerId())
                                            .setParticipantImg(R.drawable.ic_participants)
                                            .setParticipant(activity.getPart())
                                            .setCount(activity.getCount())
                                            .setNotesImg(R.drawable.ic_notes)
                                            .setNotes(activity.getDesc())
                                            .setChecked(0)
                                            .build());

                                    fStore.collection("activities")
                                            .document(ds.getId())
                                            .collection("participants")
                                            .document(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())
                                            .addSnapshotListener((value, error) -> {
                                                if (value != null) {
                                                    if (Objects.equals(value.get(fAuth.getCurrentUser().getUid()), "registered")) {

                                                        deleteById(ds.getId());
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });
    }

    public void push(Context context, MainFragment mainFragment, NewActFragment newActFragment, NewActivityAdapter adapter,
                     String id, NewActivity newActivity) {

        BookingActivity bookingActivity = new BookingActivity.Builder()
                .setSport(newActivity.getSport())
                .setEpoch(newActivity.getClock())
                .setEpochEnd(newActivity.getEndClock())
                .setLocation(newActivity.getLocation())
                .setParticipant(newActivity.getParticipant())
                .setCount(StringChecker.add(newActivity.getCount()))
                .setOrganizer(newActivity.getOrganizer())
                .setId(newActivity.getIdentifier())
                .build();

        newActivity.setChecked(1);

        fStore.collection("activities")
                .document(id)
                .addSnapshotListener((value, error) -> {
                    DocumentSnapshot val = null;
                    try {
                        val = value;
                    } catch (AssertionError e) {
                        e.printStackTrace();
                    }
                    assert val != null;
                    if (Objects.equals(val.get("count"), val.get("part"))) {
                        Toast.makeText(context, "Max Participant Reached", Toast.LENGTH_SHORT).show();
                    } else {

                        reff.child("upcoming_activity")
                                .child(Objects.requireNonNull(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()))
                                .child(id)
                                .setValue(bookingActivity)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {

                                        fStore.collection("activities")
                                                .document(id)
                                                .update("count", bookingActivity.getCount());

                                        Map<String, String> map = new HashMap<>();
                                        map.put(fAuth.getCurrentUser().getUid(), "registered");
                                        fStore.collection("activities")
                                                .document(id)
                                                .collection("participants")
                                                .document(fAuth.getCurrentUser().getUid())
                                                .set(map);

                                        reff.child("your_activity")
                                                .child(bookingActivity.getOrganizerId())
                                                .child(id)
                                                .child("count")
                                                .setValue(bookingActivity.getCount());


                                        if (mainFragment != null)
                                            mainFragment.getBookingActListener().onSignUpSuccess(mainFragment, null);
                                        else if (newActFragment != null)
                                            newActFragment.getBookingActListener().onSignUpSuccess(null, newActFragment);

                                    } else {

                                        if (mainFragment != null)
                                            mainFragment.getBookingActListener().onSignUpFailure();
                                        else if (newActFragment != null)
                                            newActFragment.getBookingActListener().onSignUpFailure();

                                    }
                                    adapter.getDialog().dismiss();
                                });
                    }
                });

    }

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }
}
