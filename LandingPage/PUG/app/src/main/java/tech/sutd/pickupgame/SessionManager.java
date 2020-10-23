package tech.sutd.pickupgame;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.ui.auth.AuthActivity;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Singleton
public class SessionManager {

    private FirebaseAuth fAuth;

    @Inject
    public SessionManager(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }

    public void register(RegisterFragment fragment, Context context, NavController navController, DatabaseReference reff, User user) {
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);

                        reff.child("users").child(user.getUsername()).setValue(user);
                        fAuth.signOut();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        fragment.registerFailed();
                    }
                }
        );
    }

    public void login(LoginFragment fragment, Context context, User user) {
        fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

                        ((AuthActivity) fragment.getActivity()).login();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        fragment.loginFailed();
                    }
                }
        );
    }

    public FirebaseUser getFirebaseUser() {
        return fAuth.getCurrentUser();
    }

    public void logout(MainActivity activity) {
        fAuth.signOut();
        activity.logout();
    }
}
