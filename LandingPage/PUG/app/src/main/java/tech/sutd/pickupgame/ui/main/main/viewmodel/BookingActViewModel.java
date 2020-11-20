package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.ui.main.booking.BookingFragment;

public class BookingActViewModel extends ViewModel {

    private FirebaseFirestore fStore;

    @Inject
    public BookingActViewModel(FirebaseFirestore fStore) {
        this.fStore = fStore;
    }

    public void push(BookingFragment fragment, BookingActivity activity) {
        fStore.collection("activities")
                .add(activity)
                .addOnSuccessListener(documentReference -> fragment.getSuccessListener().onSuccess())
                .addOnFailureListener(e -> fragment.getSuccessListener().onFailure());
    }
}
