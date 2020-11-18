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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.security.Hashing;
import tech.sutd.pickupgame.ui.auth.UserViewModel;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;
import tech.sutd.pickupgame.ui.main.MainActivity;

@Singleton
public class SessionManager {

    private FirebaseAuth fAuth;
    private MessageDigest md;
    private DatabaseReference reff;

    @Inject
    public SessionManager(FirebaseAuth fAuth, MessageDigest md, DatabaseReference reff) {
        this.fAuth = fAuth;
        this.md = md;
        this.reff = reff;
    }

    public void register(RegisterFragment fragment, Context context, NavController navController, User user) {
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show();
                        navController.popBackStack(R.id.loginFragment, false);

                        String hash = null;
                        try {
                            hash = Hashing.makeSHA1Hash(md, user.getPasswd());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        reff.child("users").child(fAuth.getUid()).setValue(user);
                        fAuth.signOut();
                    } else {
                        Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        fragment.registerFailed();
                    }
                }
        );
    }

    public void login(LoginFragment fragment, UserViewModel viewModel, Context context, User user) {
        fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

                        reff.child("users").child(fAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                viewModel.update(snapshot.getValue(User.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        fragment.getListener().customAction();
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
