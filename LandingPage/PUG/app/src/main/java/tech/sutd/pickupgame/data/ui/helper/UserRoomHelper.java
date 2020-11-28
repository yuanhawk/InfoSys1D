package tech.sutd.pickupgame.data.ui.helper;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tech.sutd.pickupgame.models.User;

public interface UserRoomHelper {

    Completable insertUser(User user);

    Completable deleteAll();

    LiveData<List<User>> getUsers();
}
