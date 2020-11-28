package tech.sutd.pickupgame;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Singleton
public class SessionManager {

    private final FirebaseAuth fAuth;
    private final DatabaseReference reff;

    @Inject
    public SessionManager(FirebaseAuth fAuth, DatabaseReference reff) {
        this.fAuth = fAuth;
        this.reff = reff;
    }

    public void register(RegisterFragment fragment, Context context, NavController navController, UserProfile user) {
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show();
                        navController.popBackStack(R.id.loginFragment, false);

                        reff.child("users").child(Objects.requireNonNull(fAuth.getUid())).setValue(user);
                        fAuth.signOut();
                    } else {
                        Toast.makeText(context, "Error! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        fragment.registerFailed();
                    }
                }
        );
    }

    public void login(LoginFragment fragment, UserViewModel viewModel, Context context, UserProfile user) {
        fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

                        reff.child("users").child(Objects.requireNonNull(fAuth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                viewModel.deleteAllUsers();
                                UserProfile profile = snapshot.getValue(UserProfile.class);
                                assert profile != null;
                                viewModel.insert(new User.Builder(fAuth.getUid())
                                        .setName(profile.getName())
                                        .setEmail(profile.getEmail())
                                        .setPasswd(profile.getPasswd())
                                        .setAge(profile.getAge())
                                        .build()
                                );
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        fragment.getListener().customAction();
                    } else {
                        Toast.makeText(context, "Error! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        fragment.loginFailed();
                    }
                }
        );
    }

    public void autoLogin(UserViewModel viewModel) {
        viewModel.deleteAllUsers();
        reff.child("users").child(Objects.requireNonNull(fAuth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile profile = snapshot.getValue(UserProfile.class);
                assert profile != null;
                viewModel.insert(new User.Builder(fAuth.getUid())
                        .setName(profile.getName())
                        .setEmail(profile.getEmail())
                        .setPasswd(profile.getPasswd())
                        .setAge(profile.getAge())
                        .build()
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FirebaseUser getFirebaseUser() {
        return fAuth.getCurrentUser();
    }

    public void logout(MainActivity activity) {
        fAuth.signOut();

        activity.logout();
    }
}
