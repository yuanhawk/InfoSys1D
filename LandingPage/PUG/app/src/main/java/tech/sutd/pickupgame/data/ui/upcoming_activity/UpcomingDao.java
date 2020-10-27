package tech.sutd.pickupgame.data.ui.upcoming_activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.sutd.pickupgame.models.ui.UpcomingActivity;

@Dao
public interface UpcomingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UpcomingActivity upcomingActivity);

    @Update
    void update(UpcomingActivity upcomingActivity);

    @Delete
    void delete(UpcomingActivity upcomingActivity);

    @Query("DELETE FROM upcoming_activity")
    void deleteAllUpcomingActivities();

    @Query("SELECT * FROM upcoming_activity")
    LiveData<List<UpcomingActivity>> getUpcomingActivities();
}
