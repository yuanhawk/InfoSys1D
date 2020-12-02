package tech.sutd.pickupgame;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
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
    private final SchedulerProvider provider;

    private final MediatorLiveData<AuthResource<FirebaseAuth>> source = new MediatorLiveData<>();

    @Inject
    public SessionManager(FirebaseAuth fAuth, DatabaseReference reff, SchedulerProvider provider) {
        this.fAuth = fAuth;
        this.reff = reff;
        this.provider = provider;
    }

    public Single<AuthResource<UserProfile>> register(UserProfile user) {
        return Single.create(emitter ->
                fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                        task -> {
                            if (!emitter.isDisposed()) {
                                if (task.isSuccessful()) {
                                    reff.child("users").child(Objects.requireNonNull(fAuth.getCurrentUser().getUid())).setValue(user);

                                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(user.getName())
                                            .build();

                                    fAuth.getCurrentUser().updateProfile(request);
                                    fAuth.getCurrentUser().sendEmailVerification();
                                    fAuth.signOut();
                                    emitter.onSuccess(AuthResource.registered(user));
                                } else {
                                    emitter.onSuccess(AuthResource.error(Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage())));
                                }
                            }
                        }
                )
        );
    }

    public void login(UserProfile user) {
        source.setValue(AuthResource.loading(null));
        Single<AuthResource<FirebaseAuth>> userSource = Single.create(emitter ->
                fAuth.signInWithEmailAndPassword(user.getEmail(), user.getPasswd()).addOnCompleteListener(
                        task -> {
                            if (!emitter.isDisposed()) {
                                if (task.isSuccessful()) {
                                    emitter.onSuccess(AuthResource.authenticated(fAuth));
                                } else {
                                    emitter.onSuccess(AuthResource.error(Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage())));
                                }
                            }
                        }
                )
        );
        final LiveData<AuthResource<FirebaseAuth>> userAuthSource = LiveDataReactiveStreams.fromPublisher(
                userSource.toFlowable()
                        .onErrorReturn(throwable -> AuthResource.error("Could not authenticate"))
                        .subscribeOn(provider.io())
                        .observeOn(provider.ui())
        );

        source.addSource(userAuthSource, firebaseAuthAuthResource -> {
            source.setValue(firebaseAuthAuthResource);
            source.removeSource(userAuthSource);
        });
    }

    public Single<AuthResource<UserProfile>> reset(UserProfile user) {
        return Single.create(emitter ->
                fAuth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        emitter.onSuccess(AuthResource.reset(user));
                    else
                        emitter.onSuccess(AuthResource.error(task.getException().getMessage()));
                })
        );
    }

    public Single<AuthResource<FirebaseAuth>> updateUserDetails(UserProfile user) {
        return Single.create(emitter -> fAuth.getCurrentUser()
                .updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName())
                .setPhotoUri(Uri.parse(user.getImg()))
                .build())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reff.child("users").child(Objects.requireNonNull(fAuth.getCurrentUser().getUid())).setValue(user);
                        emitter.onSuccess(AuthResource.update(fAuth));
                    } else
                        emitter.onSuccess(AuthResource.error(task.getException().getMessage()));
                }));
    }

    public LiveData<AuthResource<FirebaseAuth>> observeAuthState() {
        return source;
    }

    public void logout() {
        fAuth.signOut();
        source.setValue(AuthResource.logout());
    }

}
