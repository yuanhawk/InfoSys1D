package tech.sutd.pickupgame.data.ui.user;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.models.User;

public class UserRoom implements UserRoomHelper {

    private final UserDatabase userDatabase;

    @Inject
    public UserRoom(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public Completable insertUser(User user) {
        return userDatabase.userDao().insertUser(user);
    }

    @Override
    public Completable deleteAllUsers() {
        return userDatabase.userDao().deleteAllUsers();
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return userDatabase.userDao().getAllUsers();
    }

}
