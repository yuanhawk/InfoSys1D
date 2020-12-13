package tech.sutd.pickupgame.ui.main.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;

public class BookingActViewModel extends BaseViewModel {

    private final FirebaseFirestore fStore;
    private final DatabaseReference reff;
    private final FirebaseAuth fAuth;

    private final MediatorLiveData<Resource<BookingActivity>> source = new MediatorLiveData<>();

    @Inject
    public BookingActViewModel(SchedulerProvider provider, DataManager dataManager,
                               FirebaseFirestore fStore, DatabaseReference reff, FirebaseAuth fAuth) {
        super(provider, dataManager);
        this.fStore = fStore;
        this.reff = reff;
        this.fAuth = fAuth;
    }

    @Override
    public void setError(Throwable e) {

    }

    public void push(BookingActivity activity) {
        if (fAuth.getCurrentUser() == null)
            return;

        activity.setOrganizerId(fAuth.getCurrentUser().getUid());
        source.setValue(Resource.loading(null));
        Single<Resource<BookingActivity>> bookingActSource = Single.create(emitter -> fStore.collection("activities")
                .add(activity)
                .addOnSuccessListener(documentReference -> {
                    if (!emitter.isDisposed()) {
                        reff.child("your_activity")
                                .child(Objects.requireNonNull(fAuth.getCurrentUser().getUid()))
                                .child(documentReference.getId())
                                .setValue(activity);

                        emitter.onSuccess(Resource.success(activity));
                    }
                })
                .addOnFailureListener(e -> emitter.onSuccess(Resource.error(e.getMessage()))));

        final LiveData<Resource<BookingActivity>> bookingSource = LiveDataReactiveStreams.fromPublisher(
                bookingActSource
                        .toFlowable()
                        .subscribeOn(getProvider().io())
                        .observeOn(getProvider().ui())
        );

        source.addSource(bookingSource, bookingActivityResource -> {
            source.setValue(bookingActivityResource);
            source.removeSource(bookingSource);
        });
    }

    public LiveData<Resource<BookingActivity>> observeBooking() {
        return source;
    }
}
