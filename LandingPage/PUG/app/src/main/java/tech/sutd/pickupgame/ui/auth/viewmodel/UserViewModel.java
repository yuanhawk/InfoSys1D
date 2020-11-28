package tech.sutd.pickupgame.ui.auth.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import tech.sutd.pickupgame.BuildConfig;
import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.data.ui.user.AuthResource;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.UserProfile;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;

public class UserViewModel extends BaseViewModel {

    private final SessionManager sessionManager;

    private final MediatorLiveData<AuthResource<List<User>>> source = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "setError: ", e);
            e.printStackTrace();
        }
        source.setValue(AuthResource.error(e.getMessage()));
    }

    @Inject
    public UserViewModel(SchedulerProvider provider, DataManager dataManager, SessionManager sessionManager) {
        super(provider, dataManager);
        this.sessionManager = sessionManager;
    }

    public void register(RegisterFragment fragment, Context context, NavController navController, UserProfile user) {
        sessionManager.register(fragment, context, navController, user);
    }

    public void login(LoginFragment fragment, Context context, UserProfile user) {
        sessionManager.login(fragment, this, context, user);
    }

    public void insert(User user) {
        getCompositeDisposable().add(getDataManager().insertUser(user)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public void deleteAllUsers() {
        getCompositeDisposable().add(getDataManager().deleteAll()
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }

    public LiveData<List<User>> getUsers() {
        return getDataManager().getUsers();
    }

    private void doOnLoading() {
        source.postValue(AuthResource.loading(null));
    }
}
