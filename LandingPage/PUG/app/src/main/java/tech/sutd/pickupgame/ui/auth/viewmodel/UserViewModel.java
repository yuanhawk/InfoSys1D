package tech.sutd.pickupgame.ui.auth.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;

public class UserViewModel extends BaseViewModel {

    private final SessionManager sessionManager;
    private final DatabaseReference reff;

    private final MediatorLiveData<AuthResource<UserProfile>> userProfileSource = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {}

    @Inject
    public UserViewModel(SchedulerProvider provider, DataManager dataManager, SessionManager sessionManager, DatabaseReference reff) {
        super(provider, dataManager);
        this.sessionManager = sessionManager;
        this.reff = reff;
    }

    public LiveData<AuthResource<UserProfile>> register(UserProfile user) {
        userProfileSource.setValue(AuthResource.loading(null));
        final LiveData<AuthResource<UserProfile>> userProfileSource = LiveDataReactiveStreams.fromPublisher(
                sessionManager.register(user)
                .toFlowable()
                .onErrorReturn(throwable -> AuthResource.error("Could not authenticate"))
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui())
        );

        this.userProfileSource.addSource(userProfileSource, userProfileAuthResource -> {
            this.userProfileSource.setValue(userProfileAuthResource);
            this.userProfileSource.removeSource(userProfileSource);
        });

        return this.userProfileSource;
    }

    public void login(UserProfile user) {
        sessionManager.login(user);
    }

    public void insertUserDb(FirebaseAuth data) {
        deleteAllUsers();
        reff.child("users").child(Objects.requireNonNull(data.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile profile = snapshot.getValue(UserProfile.class);
                if (profile != null) {
                    insert(new User.Builder(data.getUid())
                            .setName(profile.getName())
                            .setEmail(profile.getEmail())
                            .setPasswd(profile.getPasswd())
                            .setAge(profile.getAge())
                            .build()
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insert(User user) {
        getCompositeDisposable().add(getDataManager().insertUser(user)
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteAllUsers() {
        getCompositeDisposable().add(getDataManager().deleteAll()
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public LiveData<List<User>> getUsers() {
        return getDataManager().getUsers();
    }

}
