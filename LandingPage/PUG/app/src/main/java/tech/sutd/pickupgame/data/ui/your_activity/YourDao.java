package tech.sutd.pickupgame.data.ui.your_activity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tech.sutd.pickupgame.models.ui.YourActivity;

@Dao
public interface YourDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertYourActivity(YourActivity yourActivities);

    @Query("DELETE FROM your_activity")
    Completable deleteAllYourActivities();

    @Query("SELECT * FROM your_activity ORDER BY clock DESC LIMIT 10")
    Flowable<List<YourActivity>> getAllYourActivitiesLimit10();

    @Query("SELECT * FROM your_activity ORDER BY clock")
    Flowable<List<YourActivity>> getAllYourActivities();
}
