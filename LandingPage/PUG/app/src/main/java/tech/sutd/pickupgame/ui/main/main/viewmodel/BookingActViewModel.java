package tech.sutd.pickupgame.ui.main.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import io.reactivex.Single;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;

public class BookingActViewModel extends BaseViewModel {

    private final FirebaseFirestore fStore;

    private final MediatorLiveData<Resource<BookingActivity>> source = new MediatorLiveData<>();

    @Inject
    public BookingActViewModel(SchedulerProvider provider, DataManager dataManager, FirebaseFirestore fStore) {
        super(provider, dataManager);
        this.fStore = fStore;
    }

    @Override
    public void setError(Throwable e) {

    }

    public void push(BookingActivity activity) {
        source.setValue(Resource.loading(null));
        Single<Resource<BookingActivity>> bookingActSource = Single.create(emitter ->
                fStore.collection("activities")
                .add(activity)
                .addOnSuccessListener(documentReference -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(Resource.success(activity));
                    }
                })
                .addOnFailureListener(e -> emitter.onSuccess(Resource.error(e.getMessage())))
        );

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