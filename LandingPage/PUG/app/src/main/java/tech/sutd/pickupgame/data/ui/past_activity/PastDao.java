package tech.sutd.pickupgame.data.ui.past_activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.models.ui.PastActivity;

@Dao
public interface PastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PastActivity pastActivity);

    @Query("DELETE FROM past_activity")
    Completable deleteAllPastActivities();

    @Query("SELECT * FROM past_activity ORDER BY clock DESC LIMIT 10")
    Flowable<List<PastActivity>> getAllPastActivities();
}
