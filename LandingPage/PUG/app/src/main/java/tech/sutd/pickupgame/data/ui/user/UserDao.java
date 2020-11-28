package tech.sutd.pickupgame.data.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tech.sutd.pickupgame.models.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(User user);

    @Query("DELETE FROM users")
    Completable deleteAll();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getUsers();
}
