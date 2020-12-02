package tech.sutd.pickupgame.ui.main.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.main.MainFragment;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.newact.NewActFragment;
import tech.sutd.pickupgame.util.StringComparator;

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

    public void delete(String clock) {
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
        reff.child("upcoming_activity")
                .child(Objects.requireNonNull(fAuth.getCurrentUser().getUid()))
                .child(id)
                .setValue(bookingActivity)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (mainFragment != null)
                            mainFragment.getBookingActListener().onSignUpSuccess();
                        else if (newActFragment != null)
                            newActFragment.getBookingActListener().onSignUpSuccess();

                    } else {

                        if (mainFragment != null)
                            mainFragment.getBookingActListener().onSignUpFailure();
                        else if (newActFragment != null)
                            newActFragment.getBookingActListener().onSignUpFailure();

                    }
                    adapter.getDialog().dismiss();
                });

    }

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }
}
