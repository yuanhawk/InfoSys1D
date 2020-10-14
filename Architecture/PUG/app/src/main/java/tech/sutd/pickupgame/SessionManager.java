package tech.sutd.pickupgame;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sutd.pickupgame.data.UserRepository;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.ui.auth.AuthResource;

@Singleton
public class SessionManager {

    private FirebaseAuth fAuth;

    @Inject
    public SessionManager(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }

    public void login(Context context, User user) {
        fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void register(Context context, User user) {
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public FirebaseUser getFirebaseUser() {
        return fAuth.getCurrentUser();
    }
}
