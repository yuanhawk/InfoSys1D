package tech.sutd.pickupgame.ui.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.ui.BaseViewModel;

public class UserViewModel extends BaseViewModel {

    private final SessionManager sessionManager;
    private final DatabaseReference reff;

    private final MediatorLiveData<AuthResource<UserProfile>> userProfileSource = new MediatorLiveData<>();
    private final MediatorLiveData<AuthResource<FirebaseAuth>> fAuthProfileSource = new MediatorLiveData<>();

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

    public LiveData<AuthResource<UserProfile>> reset(UserProfile user) {
        userProfileSource.setValue(AuthResource.loading(null));
        final LiveData<AuthResource<UserProfile>> userProfileSource = LiveDataReactiveStreams.fromPublisher(
                sessionManager.reset(user)
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

    public LiveData<AuthResource<FirebaseAuth>> updateUserDetails(UserProfile user) {
        fAuthProfileSource.setValue(AuthResource.loading(null));

        final LiveData<AuthResource<FirebaseAuth>> userProfileSource = LiveDataReactiveStreams.fromPublisher(
                sessionManager.updateUserDetails(user)
                        .toFlowable()
                        .onErrorReturn(throwable -> AuthResource.error("Could not update"))
                        .subscribeOn(getProvider().io())
                        .observeOn(getProvider().ui())
        );

        fAuthProfileSource.addSource(userProfileSource, firebaseAuthAuthResource -> {
            fAuthProfileSource.setValue(firebaseAuthAuthResource);
            fAuthProfileSource.removeSource(userProfileSource);
        });

        return fAuthProfileSource;
    }

    public void login(UserProfile user) {
        sessionManager.login(user);
    }

    public void insertUserDb(FirebaseAuth data) {
        deleteAllUsers();
        if (data == null || data.getCurrentUser() == null) {
            return;
        }

        String imgSrc = String.valueOf(data.getCurrentUser().getPhotoUrl());

        reff.child("users").child(Objects.requireNonNull(data.getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile profile = snapshot.getValue(UserProfile.class);
                if (profile != null) {
                    insert(new User.Builder(data.getCurrentUser().getUid())
                            .setName(profile.getName())
                            .setEmail(profile.getEmail())
                            .setPasswd(profile.getPasswd())
                            .setAge(profile.getAge())
                            .setImg(imgSrc)
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
        getCompositeDisposable().add(getDataManager().deleteAllUsers()
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public LiveData<List<User>> getUsers() {
        return getDataManager().getAllUsers();
    }

}
